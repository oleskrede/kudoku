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
            val rows = input.split("\n")

            validateRows(rows)

            val board = mutableListOf<List<Cell>>()
            for (row in rows) {
                val cellRow = mutableListOf<Cell>()
                for (c in row) {
                    if (c.isDigit()) cellRow.add(Cell(getNumericValue(c)))
                    else cellRow.add(Cell())
                }
                board.add(cellRow)
            }
            return Board(board)
        }

        private fun validateRows(rows: List<String>) {
            if (rows.size != 9) {
                throw IllegalArgumentException("Input should contain 9 rows, but contains ${rows.size} rows.")
            }
            for (row in rows) {
                if (row.length != 9) {
                    throw IllegalArgumentException("Each row should contain 9 chars, but the row: '$row' contains ${row.length} chars.")
                }
            }
        }
    }
}
