package global.hiber.api.rest.customer.asset

import global.hiber.api.customer.asset.asProto
import global.hiber.api.customer.conversion.json.toJsonObject
import global.hiber.api.grpc.asset.AssetApi
import global.hiber.api.grpc.value.Value
import global.hiber.api.ifNotDefault
import global.hiber.api.rest.HAL
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.RestApiRoute
import global.hiber.api.rest.customer.asset.RestApiAsset.Asset.Companion.asJson
import global.hiber.api.rest.restApiRoute
import global.hiber.api.rest.toJson
import global.hiber.asset.asset
import global.hiber.auth.Assets
import global.hiber.database.system.ModemNumberHex
import global.hiber.database.system.db
import global.hiber.database.system.file.getFilesNoContent
import global.hiber.grpc.exceptions.NotFound
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RestApiAsset(
  val asset: Asset,

  @SerialName("_links")
  val links: Links,
) : RestApiResponse {
  @Serializable
  data class Asset(
    val organization: String,
    val identifier: String,
    val name: String,
    val type: String,
    val description: String,
    val notes: String,
    val devices: List<ConnectedDevice>,
    val inactiveDevices: List<ConnectedDevice>,
    val tags: List<JsonObject>,
  ) {
    @Serializable
    data class ConnectedDevice(
      val number: ModemNumberHex,
      val name: String? = null,
      val type: String? = null,
      val health: String? = null,
      val numericValueTypes: List<Value.Numeric.Type>,
      val assignmentStart: String? = null,
      val assignmentEnd: String? = null,
    ) {
      companion object {
        operator fun invoke(d: AssetApi.Asset.AssignedDevice) = ConnectedDevice(
          number = d.number,
          name = d.name.ifNotDefault(),
          type = d.type?.ifNotDefault(),
          health = d.health.takeIf { d.hasHealth() }?.level?.ifNotDefault(),
          numericValueTypes = d.numericValueTypesList,
          assignmentStart = d.assignmentTimeRange.start.textual.ifNotDefault(),
          assignmentEnd = d.assignmentTimeRange.end.textual.ifNotDefault(),
        )
      }
    }

    companion object {
      val AssetApi.Asset.asRestApiAsset
        get() = Asset(
          organization = organization,
          identifier = identifier,
          name = name,
          type = type.name,
          description = description,
          notes = notes,
          tags = tagsList.map { it.toJsonObject(full = false) },
          devices = devicesList.map { ConnectedDevice(it) },
          inactiveDevices = inactiveDevicesList.map { ConnectedDevice(it) },
        )

      fun AssetApi.Asset.asJson(parent: Route) = asRestApiAsset.let {
        RestApiAsset(it, Links(self = HAL.Link.withDomain(path = route.path(parent, identifier)))).toJson()
      }
    }
  }

  @Serializable
  data class Links(override val self: HAL.Link) : HAL.Links {
    override val previous: HAL.Link? = null
    override val next: HAL.Link? = null
  }

  companion object {
    val route: RestApiRoute.WithPathParameter = restApiRoute(
      "asset/{identifier}",
      head = { _, _, asset -> db.read(Assets()) { asset(asset) ?: throw NotFound } },
      get = { parent, _, asset ->
        db.read(Assets()) {
          asset(asset)?.asProto { getFilesNoContent(it) }?.asJson(parent) ?: throw NotFound
        }
      },
    )
  }
}
