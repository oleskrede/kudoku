import SudokuBoard.Companion.fromString
import SudokuBoard.Companion.toPrettyString
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
        title("Kudoku")
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
            xx53xxxxx
            8xxxxxx2x
            x7xx1x5xx
            4xxxx53xx
            x1xx7xxx6
            xx32xxx8x
            x6x5xxxx9
            xx4xxxx3x
            xxxxx97xx
        """.trimIndent()

    val inputTestSolution =
        """
            125394678
            849657123
            376218594
            487965312
            912873456
            653421987
            761532849
            594786231
            238149765
        """.trimIndent()

    val testSolution = fromString(inputTestSolution)
    println("Test solution:")
    println(toPrettyString(testSolution))

    val sudokuSolver = SudokuSolver(inputTestCase)
    val solverSolution = sudokuSolver.solve()
    println("SudokuSolver solution:")
    println(toPrettyString(solverSolution))

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
        }
    }.start(wait = true)
}
//
// fun solveSudoku(input: String): String {
//    val sudokuSolver = SudokuSolver(fromString(input))
//    sudokuSolver.solve()
//    return toPrettyString(sudokuSolver.sudokuBoard)
// }
