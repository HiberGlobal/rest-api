package global.hiber.api.rest.customer.modem.message

import global.hiber.api.rest.test.get
import global.hiber.common.requireEmpty
import global.hiber.common.tests.TestTags
import global.hiber.common.time.hours
import global.hiber.common.time.now
import global.hiber.serialization.json.assertJsonEquals
import global.hiber.serialization.json.toJsonPathParser
import io.ktor.http.Url
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Tag(TestTags.Messages)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiTimeRangeShortcutsIT : RestApiMessageITBase() {
  val parent = ""

  @Test
  fun `WEB-6427 - timerange shortcuts should work`() = withMessages(RestApiMessageList.route) { token ->
    val time = now

    get(RestApiMessageList.path(parent), token, RestApiMessageList.Request(from = "-1h", to = "now")) {
      // VERIFY: time is set, but we cannot check it exactly, so json compare doesn't work
      it.toJsonPathParser().also { json ->
        json["$.messages"].jsonArray.requireEmpty("No messages expected in range")
        val fullUrl = json["$['_links'].self.href"].jsonPrimitive.content
        val (url, _) = json["$['_links'].self.href"].jsonPrimitive.content.split("?")
        assertEquals("https://rest.api.test.env.hiber.cloud/messages", url)
        // VERIFY: from is ~1h before request time; to is request time
        val timeCheck = time.truncatedTo(ChronoUnit.MINUTES)
        val prefixFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(UTC)
        assertTrue { Url(fullUrl).parameters["from"]?.startsWith(prefixFormat.format(timeCheck - 1.hours)) == true }
        assertTrue { Url(fullUrl).parameters["to"]?.startsWith(prefixFormat.format(timeCheck)) == true }
      }
    }

    get(RestApiMessageList.path(parent), token, RestApiMessageList.Request(from = "2021-02-26", to = "2021-02-27")) {
      assertJsonEquals(
        """
        {
          "messages": [
            {
              "messageId": "8",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-26T13:00:00Z",
                "textual": "2021-02-26T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
                "messageId": "18",
                "modemNumber": "0000 0002",
                "modemName": "0000 0002",
                "sentAt": {
                    "timestamp": "2021-02-26T13:00:00Z",
                    "timeZone": "UTC",
                    "textual": "2021-02-26T13:00:00Z"
                },
                "receivedAt": {
                    "timestamp": "2021-03-01T13:00:00Z",
                    "timeZone": "UTC",
                    "textual": "2021-03-01T13:00:00Z"
                },
                "location": {
                    "latitude": 1.0,
                    "longitude": 0.99999,
                    "textual": "[1.0,0.99999]"
                }
            }
          ],
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/messages?from=2021-02-26T00%3A00%3A00Z&to=2021-02-27T00%3A00%3A00Z&size=10&page=0"
            }
          },
          "pagination": {"size":10,"page":0,"total":2,"approximation":false,"totalPages":1}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }
  }
}
