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
            714683295
            396125487
            582974631
            127859346
            849361572
            653742918
            935218764
            471596823
            268437159
        """.trimIndent()

    val testCase = fromString(inputTestCase)
    println("Test case:")
    println(toPrettyString(testCase))

    val testSolution = fromString(inputTestSolution)
    println("Test solution:")
    println(toPrettyString(testSolution))

    val sudokuSolver = SudokuSolver(testCase)
    sudokuSolver.solve()
    println("SudokuSolver solution:")
    println(toPrettyString(sudokuSolver.sudokuBoard))

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
