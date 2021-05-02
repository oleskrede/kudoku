class Board(val cells: List<Cell>) {

    val subGrids = cells.chunked(3)
        .withIndex()
        .groupBy { it.index % 3 }
        .map { indexed -> indexed.value.map { it.value } }
        .map { it.chunked(3) }
        .flatten()
        .map { it.flatten() }

    val rows = cells.chunked(9)

    val columns = cells.withIndex()
        .groupBy { it.index % 9 }
        .map { indexed -> indexed.value.map { it.value } }

    fun getSolvedValuesForCells(cells: List<Cell>): Set<Int> {
        return cells
            .filter { cell -> cell.value != null }
            .map { it.value!! }
            .toSet()
    }

    fun hasUnsolvedCells(): Boolean {
        for (cell in cells) {
            if (cell.value == null)
                return true
        }
        return false
    }

    fun solutionIsValid(): Boolean {
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
    val valueCandidates = (1..9).toMutableSet()

    // Returns boolean indicating if any candidates were pruned
    fun pruneCandidates(solvedValues: Set<Int>): Boolean {
        return valueCandidates.removeAll(solvedValues)
    }

    // Returns boolean indicating if the cell was solved
    fun attemptToSolve(): Boolean {
        if (valueCandidates.size == 1) {
            value = valueCandidates.first()
            return true
        }
        return false
    }

    override fun toString(): String {
        return if (value == null) "_" else value.toString()
    }
}
