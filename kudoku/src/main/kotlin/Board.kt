class Board(val cells: List<Cell>) {

    val subGrids = cells.chunked(3)
        .withIndex()
        .groupBy { it.index % 3 }
        .map { indexed -> indexed.value.map { it.value } }
        .map { it.chunked(3) }
    val subGridsList = subGrids.flatten()
    val subGridsListFlattened = subGridsList.map { it.flatten() }

    val rows = cells.chunked(9)

    val columns = cells.withIndex()
        .groupBy { it.index % 9 }
        .map { indexed -> indexed.value.map { it.value } }

    fun getSolvedValuesForCells(cells: List<Cell>): List<Int> {
        return cells
            .filter { cell -> cell.value != null }
            .map { it.value!! }
    }

    fun isSolved(): Boolean {
        for (cell in cells) {
            if (cell.value == null)
                return false
        }
        return true
    }

    override fun toString(): String {
        return cells.chunked(9)
            .joinToString(separator = "\n") { row -> row.joinToString(separator = "") }
    }

    fun toPrettyString(): String {
        val hLine = "\n ------- ------- ------- \n"
        return cells.chunked(9)
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
}

class Cell(var value: Int? = null) {
    val possibilities = (1..9).toMutableSet()

    override fun toString(): String {
        return if (value == null) "_" else value.toString()
    }

    fun prunePossibilities(solvedValues: List<Int>): Boolean {
        return possibilities.removeAll(solvedValues)
    }
}
