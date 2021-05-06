import SudokuBoard.Companion.toPrettyString
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.application.*
import io.ktor.mustache.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }
    routing {
        get("/") {
            val sampleUser = User(1, "John")
            call.respond(MustacheContent("index.hbs", mapOf("user" to sampleUser)))
        }
        post("/solve") {
            val formInput = call.receiveParameters()
            val sudokuString = formInput["sudoku"]
            if (sudokuString == null || sudokuString.length != 81) {
                call.respondText("Input missing or invalid.")
            } else {
                val solution = SudokuSolver(sudokuString).solve()
                call.respondText(toPrettyString(solution))
            }
        }
    }
}

data class User(val id: Int, val name: String)
