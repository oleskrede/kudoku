import InputHandler.Companion.stringToBoard
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class InputHandlerTest {

    @Test
    fun shouldParseInputStringToBoard() {
        val input =
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
        val cells = stringToBoard(input).cells

        assertEquals(1, cells[0].value)
        assertNull(cells[1].value)
        assertEquals(8, cells[2].value)
        assertNull(cells[8].value)
        assertEquals(2, cells[9].value)
        assertEquals(6, cells[80].value)
    }
}
