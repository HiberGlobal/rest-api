package global.hiber.api.rest.customer.value

import global.hiber.api.initializeToken
import global.hiber.api.rest.RestApiRoute
import global.hiber.asset.AssetSelection
import global.hiber.asset.assets
import global.hiber.assignment.type.assignAssetToDevice
import global.hiber.auth.Assets
import global.hiber.auth.Scope
import global.hiber.database.system.AssetIdentifier
import global.hiber.database.system.ModemNumber
import global.hiber.database.system.db
import global.hiber.database.system.enums.AssetType
import global.hiber.database.system.initializeAssets
import global.hiber.database.system.initializeModems
import global.hiber.database.system.initializePayloadParsers
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.singleByteParserKsy
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import global.hiber.database.system.type.filter.Filter
import global.hiber.modem.ModemSelection
import global.hiber.test.data.TestValues
import global.hiber.test.data.initializeTestFields
import global.hiber.test.data.initializeTestValues
import io.ktor.server.resources.Resources
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication

abstract class RestApiValueITBase : DatabaseIntegrationTestContainer() {
  fun withValues(
    vararg route: RestApiRoute,
    f: suspend ApplicationTestBuilder.(String, AssetIdentifier, ModemNumber) -> Unit,
  ): Unit = testApplication {
    install(Resources)
    routing { route.forEach { it.invoke(this) } }

    val identity = db.action { initializePrimaryOrganization() }
    val modem = db.action { initializeModems(1, identity) }.single()
    val parser = db.action { initializePayloadParsers(1, identity, singleByteParserKsy) }.single()
    val fields = db.action { initializeTestFields(parser) }
    db.action { initializeTestValues(identity.organizationId!!, modem, parser, fields) }

    // init assets
    db.action { initializeAssets(identity) }
    val scope = Scope.Organization.Read(identity)
    val assetSelection = AssetSelection(types = Filter.AssetTypes(setOf(AssetType.Well_Annulus_A)))
    val asset = db.read(scope.Assets()) { assets(assetSelection) }.results.single().identifier
    db.transaction(scope.Assignments()) {
      assignAssetToDevice(assetSelection, ModemSelection(modem), TestValues.timeRange.start)
    }

    f(db.transaction { initializeToken(identity).token }, asset, modem)
  }
}
