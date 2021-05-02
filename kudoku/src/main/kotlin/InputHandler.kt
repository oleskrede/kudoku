import java.lang.Character.getNumericValue
import java.lang.IllegalArgumentException

class InputHandler {
    companion object {

        /*
        Accepted input example:
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
         """
         */
        fun stringToBoard(input: String): Board {
            val board = input.replace("\n", "")
                .map { if (it.isDigit()) Cell(getNumericValue(it)) else Cell() }

            if (board.size != 81) throw IllegalArgumentException("Expected 81 input cells, but got ${board.size}")

            return Board(board)
        }
    }
}
