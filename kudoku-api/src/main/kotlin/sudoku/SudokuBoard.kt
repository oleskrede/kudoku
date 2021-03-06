package sudoku

import java.lang.IllegalArgumentException

/**
 * A Sudoku board representation with additional
 * @property cells the cells in a list, corresponding to reading the board left-to-right, top-to-bottom (like text a book)
 */
class SudokuBoard(val cells: List<Cell>) {

    // All the 3x3 subgrids of the board, for easy access
    val subGrids = cells.asSequence().chunked(3)
        .withIndex()
        .groupBy { it.index % 3 }
        .map { indexed -> indexed.value.map { it.value } }
        .map { it.chunked(3) }
        .flatten()
        .map { it.flatten() }.toList()

    // All rows of the board, for easy access
    val rows = cells.chunked(9)

    // All columns of the board, for easy access
    val columns = cells.withIndex()
        .groupBy { it.index % 9 }
        .map { indexed -> indexed.value.map { it.value } }

    fun getSolvedValuesForCells(cells: List<Cell>): Set<Int> {
        return cells
            .filter { cell -> cell.value != null }
            .map { it.value!! }
            .toSet()
    }

    fun isSolved(): Boolean {
        if (hasUnsolvedCells()) return false

        val allValues = (1..9).toSet()
        for (row in rows) {
            if (!row.map { it.value!! }.toSet().containsAll(allValues)) return false
        }
        for (col in columns) {
            if (!col.map { it.value!! }.toSet().containsAll(allValues)) return false
        }
        for (subGrid in this.subGrids) {
            if (!subGrid.map { it.value!! }.toSet().containsAll(allValues)) return false
        }

        return true
    }

    private fun hasUnsolvedCells(): Boolean {
        for (cell in cells) {
            if (cell.value == null)
                return true
        }
        return false
    }

    // Indicates if we have reached a dead-end in our solution attempt (i.e. the guess is wrong)
    fun isUnsolvable(): Boolean {
        if (subGrids.any { hasDuplicateValues(it) }) return true
        if (rows.any { hasDuplicateValues(it) }) return true
        if (columns.any { hasDuplicateValues(it) }) return true
        return false
    }

    private fun hasDuplicateValues(cells: List<Cell>): Boolean {
        return cells.filter { it.value != null }
            .groupBy { it.value }
            .any { group -> group.value.size > 1 }
    }

    override fun toString(): String {
        return cells.chunked(9)
            .joinToString(separator = "\n") { row -> row.joinToString(separator = "") }
    }

    fun toOneLineString(): String = cells.joinToString(separator = "")

    companion object {
        /**
         * @param sudokuStringRepresentation the values of the sudokuboard from left-to-right top-to-bottom.
         * - empty cells should be represented by non-digit character, e.g. 'x' or space
         * - newlines are ignored
         */
        fun fromString(sudokuStringRepresentation: String): SudokuBoard {
            val board = sudokuStringRepresentation.replace("\n", "")
                .map { if (it.isDigit()) Cell(Character.getNumericValue(it)) else Cell() }

            if (board.size != 81) throw IllegalArgumentException("Expected 81 input cells, but got ${board.size}")

            return SudokuBoard(board)
        }

        fun toPrettyString(sudokuBoard: SudokuBoard): String {
            val hLine = "\n ------- ------- ------- \n"
            return sudokuBoard.cells.chunked(9)
                .map { row ->
                    row.chunked(3)
                        .joinToString(separator = " | ") { subRow -> subRow.joinToString(separator = " ") }
                }
                .chunked(3).joinToString(
                    separator = hLine,
                    prefix = hLine,
                    postfix = hLine
                ) { rows -> rows.joinToString(separator = " |\n| ", prefix = "| ", postfix = " |") }
        }

        fun clone(sudokuBoard: SudokuBoard): SudokuBoard {
            val clone = fromString(sudokuBoard.cells.joinToString(separator = ""))
            for (i in sudokuBoard.cells.indices) {
                clone.cells[i].valueCandidates.clear()
                clone.cells[i].valueCandidates.addAll(sudokuBoard.cells[i].valueCandidates)
            }
            return clone
        }
    }
}

