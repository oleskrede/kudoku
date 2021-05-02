class Board(val cells: List<List<Cell>>) {
    fun setCell(x: Int, y: Int, value: Int) {
        cells[y][x].value = value
    }

    override fun toString(): String {
        return cells.joinToString(separator = "\n") { row -> row.joinToString(separator = "") }
    }

    fun toPrettyString(): String {
        val hLine = "\n ------- ------- ------- \n"
        return cells
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
    val possibilities = mutableSetOf(1..9)

    override fun toString(): String {
        return if (value == null) "_" else value.toString()
    }
}
