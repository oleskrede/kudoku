import InputHandler.Companion.stringToBoard
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.HTML
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.title

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
    }
}

fun main() {

    val inputTestCase =
        """
            1x8xxxx3x
            2xx1x5xxx
            xx7x89154
            x8x59xx4x
            5xx4x3xx8
            x4xx18x2x
            81396x4xx
            xxx3x7xx2
            x2xxxx3x6
        """.trimIndent()

    val inputTestSolution =
        """
            158674239
            294135687
            637289154
            382596741
            571423968
            946718523
            813962475
            469357812
            725841396
        """.trimIndent()

    val testCase = stringToBoard(inputTestCase)
    println("Test case:")
    println(testCase.toPrettyString())

    val testSolution = stringToBoard(inputTestSolution)
    println("Test solution:")
    println(testSolution.toPrettyString())

    val sudokuSolver = SudokuSolver(testCase)
    sudokuSolver.solve()
    println("SudokuSolver solution:")
    println(sudokuSolver.board.toPrettyString())

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
        }
    }.start(wait = true)
}
