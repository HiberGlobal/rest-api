package global.hiber.api.rest.customer

import global.hiber.api.rest.customer.asset.RestApiAsset
import global.hiber.api.rest.customer.asset.RestApiAssetList
import global.hiber.api.rest.customer.docs.restApiDocs
import global.hiber.api.rest.customer.file.RestApiFile
import global.hiber.api.rest.customer.modem.RestApiDevice
import global.hiber.api.rest.customer.modem.RestApiDeviceList
import global.hiber.api.rest.customer.modem.RestApiModem
import global.hiber.api.rest.customer.modem.RestApiModemList
import global.hiber.api.rest.customer.modem.message.RestApiMessageList
import global.hiber.api.rest.customer.modem.message.event.RestApiMessageEventList
import global.hiber.api.rest.customer.value.RestApiAssetValueList
import global.hiber.api.rest.customer.value.RestApiDeviceValueList
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.customerRestApi() {
  route("/api") {
    restApiDocs(this)
    RestApiAsset.route(this)
    RestApiAssetList.route(this)
    RestApiAssetValueList.route(this)
    RestApiDevice.route(this)
    RestApiDeviceList.route(this)
    RestApiDeviceValueList.route(this)
    RestApiFile.route(this)
    RestApiModem.route(this)
    RestApiModemList.route(this)
    RestApiMessageList.route(this)
    RestApiMessageEventList.route(this)
  }
}
