package sudoku

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
