import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import mu.KotlinLogging
import sudoku.SudokuSolver

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation) { jackson() }

    routing {

        get("/{sudoku}") {
            val uSudokuBoardString = call.parameters["sudoku"]
            if (uSudokuBoardString == null || uSudokuBoardString.length != 81) {
                return@get call.respond(
                    HttpStatusCode.BadRequest,
                    KudokuError(
                        KudokuErrorCode.INVALID_INPUT,
                        "Invalid input. Expected string representation of board (left to right, top to bottom). Use 'x' for unknown cells. Expected length: 81"
                    )
                )
            }

            logger.info { "Solving <$uSudokuBoardString>" }
            val result = SudokuSolver(uSudokuBoardString).solve()

            if (!result.isSolved()) {
                logger.info { "Solution not found <${result.toOneLineString()}>" }
                return@get call.respond(
                    HttpStatusCode.BadRequest,
                    KudokuError(KudokuErrorCode.NOT_SOLVABLE, "The input sudoku is not solvable"),
                )
            }

            logger.info { "Found solution <${result.toOneLineString()}>" }
            call.respond(
                KudokuSolution(uSudokuBoardString, result.toOneLineString())
            )
        }
    }
}

data class KudokuSolution(val input: String, val solution: String)

private data class KudokuError(val errorCodes: KudokuErrorCode, val description: String)

private enum class KudokuErrorCode() {
    INVALID_INPUT,
    NOT_SOLVABLE
}
