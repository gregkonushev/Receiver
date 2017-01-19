package sqe

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
@RequestMapping("/")
open class ReceiverController {

  @ResponseStatus(OK)
  @RequestMapping("")
  fun rootDirectory() = "Welcome to Receiver App"

  @ResponseStatus(OK)
  @RequestMapping("health_check", method = arrayOf(RequestMethod.GET))
  fun healthCheck() = "OK"

  @ResponseStatus(OK)
  @RequestMapping("tracking_numbers", method = arrayOf(RequestMethod.PUT))
  fun receiveNumbers(@RequestBody body: NumbersHolder) {
    File("src/main/resources/numbers.json").writeText(
        text = jacksonObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(body)
    )
  }

  @ResponseStatus(OK)
  @RequestMapping("tracking_numbers", method = arrayOf(RequestMethod.GET))
  fun sendNumbers(): String =
      File("src/main/resources/numbers.json").readText()
}

