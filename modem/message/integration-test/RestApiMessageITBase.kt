package global.hiber.api.rest.customer.modem.message

import global.hiber.api.initializeToken
import global.hiber.api.rest.RestApiRoute
import global.hiber.auth.Scope
import global.hiber.common.coroutines.delayUntilSuccessful
import global.hiber.common.require
import global.hiber.common.time.days
import global.hiber.common.time.minutes
import global.hiber.database.system.Tables
import global.hiber.database.system.db
import global.hiber.database.system.initializeModems
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import global.hiber.database.system.type.locationOf
import global.hiber.database.toMap
import global.hiber.events.events
import global.hiber.modem.message.envelope.processAPIModemMessages
import global.hiber.modem.message.processing.MessageProcessing
import global.hiber.modem.message.simulation.GenerateModemMessageV1
import global.hiber.modem.message.simulation.generateV1Messages
import global.hiber.serialization.time.decodeToInstant
import io.ktor.server.resources.Resources
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlin.test.assertEquals

abstract class RestApiMessageITBase : DatabaseIntegrationTestContainer() {
  open val modems = setOf(1, 2)
  open val location = locationOf(1, 1)
  open val messagePerModemAmount: Int = 10
  open val baseTime = "2021-03-01 13:00".decodeToInstant()

  fun withMessages(
    vararg route: RestApiRoute,
    f: suspend ApplicationTestBuilder.(String) -> Unit,
  ): Unit = testApplication {
    install(Resources)
    routing { route.forEach { it.invoke(this) } }

    val token = db.action {
      // init org
      val identity = initializePrimaryOrganization()

      // init modems
      initializeModems(modems.size, identity, includeNumbers = modems, location = location)

      // init messages
      events.status().require { ok }
      MessageProcessing.status().require { ok }
      db.action(Scope.Organization.Modems.Messages.Push.Real(identity)) {
        processAPIModemMessages(
          generateV1Messages(
            modems.flatMap {
              (1..messagePerModemAmount).map { index ->
                GenerateModemMessageV1(it, baseTime - index.days)
              }.reversed()
            },
          ),
          overrideReceivedAt = baseTime,
        )
      }

      val expectedMessages = modems.size * messagePerModemAmount
      delayUntilSuccessful(timeout = 2.minutes) {
        assertEquals(expectedMessages, selectCount().from(Tables.MODEM_MESSAGE).fetchOne()?.component1())
        assertEquals(
          expectedMessages,
          selectCount().from(Tables.EVENT_MODEM_MESSAGE_RECEIVED).fetchOne()?.component1(),
        )
      }

      // need event time to be predictable for the test
      db.action {
        val times = with(Tables.MODEM_MESSAGE) {
          select(ID, TIME).from(this).toMap()
        }

        with(Tables.EVENT_MODEM_MESSAGE_RECEIVED) {
          times.forEach { (id, receivedAt) ->
            update(this).set(TIME, receivedAt).where(MODEM_MESSAGE_ID.eq(id)).execute()
          }
        }
      }

      db.transaction { initializeToken(identity).token }
    }

    f(token)
  }
}
