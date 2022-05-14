package sudoku

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import sudoku.SudokuBoard.Companion.fromString

internal class SudokuBoardTest {

    private val solvableInput = """
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

    private val unsolvableInput = """
                15824xx34
                2xx1x5xxx
                xx7x89154
                x8x59xx4x
                5xx4x3xx8
                x4xx18x2x
                81396x4xx
                xxx3x7xx2
                x2xxxx3x6
    """.trimIndent()

    private val solution = """
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

    @Test
    fun shouldParseInputStringToBoard() {

        val cells = fromString(solvableInput).cells

        kotlin.test.assertEquals(1, cells[0].value)
        kotlin.test.assertNull(cells[1].value)
        kotlin.test.assertEquals(8, cells[2].value)
        kotlin.test.assertNull(cells[8].value)
        kotlin.test.assertEquals(2, cells[9].value)
        kotlin.test.assertEquals(6, cells[80].value)
    }

    @Test
    fun `Illegal board should not be solvable`() {
        val sudokuBoard = fromString(unsolvableInput)
        assertTrue(sudokuBoard.isUnsolvable())
    }

    @Test
    fun `Illegal board should not be solved`() {
        val sudokuBoard = fromString(unsolvableInput)
        assertFalse(sudokuBoard.isSolved())
    }

    @Test
    fun `Solved board should be solved`() {
        val sudokuBoard = fromString(solution)
        assertTrue(sudokuBoard.isSolved())
    }

    @Test
    fun `Solved board should not be unsolvable`() {
        val sudokuBoard = fromString(solution)
        assertFalse(sudokuBoard.isUnsolvable())
    }
}
