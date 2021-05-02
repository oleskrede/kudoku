class SudokuSolver(val board: Board) {
    fun solve() {
        while (board.hasUnsolvedCells()) {
            prunePossibilites()
        }
    }

    private fun prunePossibilites() {
        pruneSubsets(board.subGrids)
        pruneSubsets(board.rows)
        pruneSubsets(board.columns)
    }

    private fun pruneSubsets(subsets: List<List<Cell>>) {

        for (subset in subsets) {
            var solvedValues = board.getSolvedValuesForCells(subset)

            // Prune away conflicts in the subset. I.e. a cell cant have a value already used in the subset
            for (cell in subset) {
                if (cell.value == null && cell.pruneCandidates(solvedValues)) {
                    if (cell.attemptToSolve())
                        solvedValues = board.getSolvedValuesForCells(subset)
                }
            }

            // Prune for cases where a value only has one possible placement
            for (value in 1..9) {
                if (value !in solvedValues) {
                    val potentialCellsForValue = subset
                        .filter { it.value == null && it.valueCandidates.contains(value) }
                    if (potentialCellsForValue.size == 1) {
                        potentialCellsForValue.first().value = value
                        solvedValues = board.getSolvedValuesForCells(subset)
                    }
                }
            }
        }
    }
}
