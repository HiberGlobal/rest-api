package global.hiber.api.rest.customer.asset

import global.hiber.api.initializeToken
import global.hiber.asset.AssetSelection
import global.hiber.assignment.type.assignAssetToDevice
import global.hiber.auth.Scope
import global.hiber.common.time.days
import global.hiber.common.time.todayUTC
import global.hiber.database.system.ModemNumber
import global.hiber.database.system.Tables
import global.hiber.database.system.db
import global.hiber.database.system.enums.DeviceTypeIdentifier
import global.hiber.database.system.enums.NumericValueType
import global.hiber.database.system.enums.UnitOfMeasurement
import global.hiber.database.system.enums.ValueType
import global.hiber.database.system.fieldInlineOf
import global.hiber.database.system.initializeAssets
import global.hiber.database.system.initializeDeviceTypes
import global.hiber.database.system.initializeModems
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.singleByteParserKsy
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import global.hiber.database.system.type.locationOf
import global.hiber.modem.ModemSelection
import global.hiber.serialization.json.path.JsonPath
import global.hiber.test.data.deviceTypePressure
import global.hiber.test.data.deviceTypeTemperature
import io.ktor.server.routing.Route
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import java.time.Instant

abstract class RestApiAssetTestBase : DatabaseIntegrationTestContainer() {
  val parent: Route? = null
  val location = locationOf(1, 1)
  val previousTimeRange = (todayUTC - 10.days)..(todayUTC - 5.days)
  val currentTimeRange = (todayUTC - 5.days)..Instant.MAX

  val pressureField = fieldInlineOf(
    jsonPath = JsonPath.fromUserInput("pressure"),
    type = ValueType.numeric,
    numericValueType = NumericValueType.pressure,
    unit = UnitOfMeasurement.pressure_psi,
  )
  val temperatureField = fieldInlineOf(
    jsonPath = JsonPath.fromUserInput("temperature"),
    type = ValueType.numeric,
    numericValueType = NumericValueType.temperature,
    unit = UnitOfMeasurement.temperature_degrees_celsius,
  )

  fun withApi(f: suspend ApplicationTestBuilder.(String, ModemNumber, ModemNumber) -> Unit): Unit = testApplication {
    val identity = db.action { initializePrimaryOrganization() }

    // SETUP: asset with assignments
    val assets = db.action { initializeAssets(identity) }
    val (previous, current) = db.action {
      initializeDeviceTypes(
        identity,
        mapOf(
          DeviceTypeIdentifier.Pressure_Sensor_1_EU to mapOf(singleByteParserKsy to setOf(pressureField)),
          DeviceTypeIdentifier.Temperature_Sensor_1_EU to mapOf(singleByteParserKsy to setOf(temperatureField)),
        ),
      )
      initializeModems(2, identity, location = location).toList()
    }
    db.transaction(Scope.Organization.Read(identity).Assignments()) {
      // SETUP: a previously assigned device
      with(Tables.MODEM) {
        update(this).set(DEVICE_TYPE, deviceTypeTemperature.identifier).where(NUMBER.eq(previous)).execute()
      }
      assignAssetToDevice(
        AssetSelection(assets),
        ModemSelection(previous),
        overrideStartTime = previousTimeRange.start,
        overrideEndTime = previousTimeRange.endInclusive,
      )

      // SETUP: currently assigned device
      with(Tables.MODEM) {
        update(this).set(DEVICE_TYPE, deviceTypePressure.identifier).where(NUMBER.eq(current)).execute()
      }
      assignAssetToDevice(
        AssetSelection(assets),
        ModemSelection(current),
        overrideStartTime = currentTimeRange.start,
      )
    }

    f(db.transaction { initializeToken(identity).token }, previous, current)
  }
}
