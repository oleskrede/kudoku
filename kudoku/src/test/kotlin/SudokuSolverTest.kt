import SudokuBoard.Companion.fromString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SudokuSolverTest {

    @Test
    fun `Solve EASY case 1`() {
        val case =
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
        val solution =
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
        solveAndVerifyCase(case, solution)
    }

    @Test
    fun `Solve EASY case 2`() {
        val case =
            """
            x8xx4xx63
            xxx8xx7x9
            7x3xxxx14
            xx6xx7xxx
            x1x6x4x8x
            xxx1xx4xx
            16xxxx2x5
            5x8xx1xxx
            37xx6xx4x
            """.trimIndent()
        val solution =
            """
            281749563
            654813729
            793526814
            426387951
            917654382
            835192476
            169478235
            548231697
            372965148
            """.trimIndent()
        solveAndVerifyCase(case, solution)
    }

    @Test
    fun `Solve EASY case 3`() {
        val case =
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
        val solution =
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
        solveAndVerifyCase(case, solution)
    }

    private fun solveAndVerifyCase(inputCase: String, inputSolution: String) {
        val board = fromString(inputCase)
        val solution = fromString(inputSolution)

        SudokuSolver(board).solve()

        for (i in 0..80) {
            assertEquals(solution.cells[i].value, board.cells[i].value)
        }
    }
}
