import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ApplicationKtTest {

    private val testCase = "xx46xxxx53xxx2xxxx58x9xxxx11278xxx46xxxxxxxxx65xxx29189xxxx8x64xxxx9xxx32xxxx71xx"
    private val solution = "714683295396125487582974631127859346849361572653742918935218764471596823268437159"
    private val unsolvable = "xx46xxxx53xxx2xxxx58x9xxxx11278xxx46xxxxxxxxx65xxx29189xxxx8x64xxxx9xxx32xxxx711x"

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `valid input should return solution OK`() = testApplication {
        val response = client.get("/$testCase")
        assertEquals(HttpStatusCode.OK, response.status)
        val kudokuSolution = objectMapper.readValue(response.bodyAsText(), KudokuSolution::class.java)
        assertEquals(testCase, kudokuSolution.input)
        assertEquals(solution, kudokuSolution.solution)
    }

    @Test
    fun `unsolvable input should return solution OK`() = testApplication {
        val response = client.get("/$unsolvable")
        assertEquals(HttpStatusCode.BadRequest, response.status)
        val kudokuError = objectMapper.readValue(response.bodyAsText(), KudokuError::class.java)
        assertEquals(KudokuErrorCode.NOT_SOLVABLE, kudokuError.errorCodes)
    }
}
