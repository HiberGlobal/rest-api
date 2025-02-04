package global.hiber.api.rest.customer.modem

import global.hiber.api.customer.modem.asProto
import global.hiber.api.rest.HAL
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.RestApiRoute
import global.hiber.api.rest.customer.modem.RestApiModem.Links
import global.hiber.api.rest.customer.modem.RestApiModem.Modem.Companion.asModem
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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestApiDevice(
  val device: RestApiModem.Modem,

  @SerialName("_links")
  val links: Links,
) : RestApiResponse {

  companion object {
    val route: RestApiRoute.WithPathParameter.Typed<ModemNumber> = restApiRoute(
      "device/{number}",
      serialize = { it.asModemNumberHex.replace(" ", "") },
      deserialize = { it.parseModemNumberHex() },
      head = { _, _, modemNumber -> db.read(Modems()) { if (!hasAccessToModem(modemNumber)) throw NotFound } },
      get = { parent, _, modemNumber ->
        db.read(Modems()) {
          asProto(listOfNotNull(modem(modemNumber))).firstOrNull()
            ?.asModem?.let {
              RestApiDevice(it, Links(self = HAL.Link.withDomain(path = RestApiDevice.route.path(parent, modemNumber))))
            }
            ?.toJson()
            ?: throw NotFound
        }
      },
    )
  }
}
