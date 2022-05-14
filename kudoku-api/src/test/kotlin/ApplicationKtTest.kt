import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ApplicationKtTest {

    private val testCase = "xx46xxxx53xxx2xxxx58x9xxxx11278xxx46xxxxxxxxx65xxx29189xxxx8x64xxxx9xxx32xxxx71xx"
    private val solution = "714683295396125487582974631127859346849361572653742918935218764471596823268437159"

    @Test
    fun testApi() = testApplication {
        val response = client.get("/$testCase")
        assertEquals(HttpStatusCode.OK, response.status)
        val kudokuSolution: KudokuSolution = response.body()
        assertEquals(testCase, kudokuSolution.input)
        assertEquals(kudokuSolution, kudokuSolution.solution)
    }
}
