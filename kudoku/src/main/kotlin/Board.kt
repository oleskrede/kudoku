class Board(val cells: List<List<Cell>>) {
    fun setCell(x: Int, y: Int, value: Int) {
        cells[y][x].value = value
    }

    override fun toString(): String {
        return cells.joinToString(separator = "\n") { row -> row.joinToString(separator = "") }
    }
}

class Cell(var value: Int? = null) {
    val possibilities = mutableSetOf(1..9)

    override fun toString(): String {
        return if (value == null) "_" else value.toString()
    }
}
