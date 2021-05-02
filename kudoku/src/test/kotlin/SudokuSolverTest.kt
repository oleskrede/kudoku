import InputHandler.Companion.stringToBoard
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SudokuSolverTest {

    @Test
    fun solve_case1() {
        val inputCase =
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
        val inputSolution =
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
        val board = stringToBoard(inputCase)
        val solution = stringToBoard(inputSolution)

        SudokuSolver(board).solve()

        for (i in 0..80) {
            assertEquals(solution.cells[i].value, board.cells[i].value)
        }
    }
}
