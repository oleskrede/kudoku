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
            xx46xxxx5
            3xxx2xxxx
            58x9xxxx1
            1278xxx46
            xxxxxxxxx
            65xxx2918
            9xxxx8x64
            xxxx9xxx3
            2xxxx71xx
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
