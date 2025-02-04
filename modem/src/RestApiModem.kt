package global.hiber.api.rest.customer.modem

import global.hiber.api.customer.conversion.json.toJsonObject
import global.hiber.api.customer.modem.asProto
import global.hiber.api.grpc.modem.ModemApi
import global.hiber.api.ifNotDefault
import global.hiber.api.rest.HAL
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.RestApiRoute
import global.hiber.api.rest.customer.modem.RestApiModem.Modem.Companion.asJson
import global.hiber.api.rest.restApiRoute
import global.hiber.api.rest.toJson
import global.hiber.auth.Modems
import global.hiber.database.system.ModemNumber
import global.hiber.database.system.asModemNumberHex
import global.hiber.database.system.db
import global.hiber.database.system.parseModemNumberHex
import global.hiber.grpc.exceptions.NotFound
import global.hiber.modem.hasAccessToModem
import global.hiber.modem.modem
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RestApiModem(
  val modem: Modem,

  @SerialName("_links")
  val links: Links,
) : RestApiResponse {
  @Serializable
  data class Modem(
    val organization: String,
    val number: String,
    val name: String,
    val identifier: String?,
    val type: String?,
    val location: JsonObject,
    val lifecycle: ModemApi.Modem.Lifecycle,
    val healthLevel: JsonObject,
    val notes: String,
    val tags: List<JsonObject>,
  ) {
    companion object {
      val ModemApi.Modem.asModem
        get() = Modem(
          organization = organization,
          number = number,
          name = name,
          identifier = connectedDeviceInfo.externalDeviceIdsList.firstOrNull(),
          location = location.toJsonObject(full = false),
          healthLevel = healthLevel.toJsonObject(full = false),
          notes = notes,
          tags = tagsList.map { it.toJsonObject(full = false) },
          lifecycle = lifecycle,
          type = connectedDeviceInfo.deviceType.ifNotDefault(),
        )

      fun ModemApi.Modem.asJson(modemNumber: ModemNumber, parent: Route) =
        asModem.let {
          RestApiModem(
            it,
            Links(self = HAL.Link.withDomain(path = route.path(parent, modemNumber))),
          ).toJson()
        }
    }
  }

  @Serializable
  data class Links(override val self: HAL.Link) : HAL.Links {
    override val previous: HAL.Link? = null
    override val next: HAL.Link? = null
  }

  companion object {
    val route: RestApiRoute.WithPathParameter.Typed<ModemNumber> = restApiRoute(
      "modem/{number}",
      serialize = { it.asModemNumberHex.replace(" ", "") },
      deserialize = { it.parseModemNumberHex() },
      head = { _, _, modemNumber -> db.read(Modems()) { if (!hasAccessToModem(modemNumber)) throw NotFound } },
      get = { parent, _, modemNumber ->
        db.read(Modems()) {
          asProto(listOfNotNull(modem(modemNumber))).firstOrNull()?.asJson(modemNumber, parent)
            ?: throw NotFound
        }
      },
    )
  }
}
