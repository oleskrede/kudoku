class SudokuSolver(val board: Board) {
    fun solve() {
        while (board.hasUnsolvedCells()) {
            if (!prunePossibilites()) break
        }
    }

    private fun prunePossibilites(): Boolean {
        var pruned = false
        pruned = pruned || pruneSubsets(board.subGrids)
        pruned = pruned || pruneSubsets(board.rows)
        pruned = pruned || pruneSubsets(board.columns)
        return pruned
    }

    private fun pruneSubsets(subsets: List<List<Cell>>): Boolean {
        var pruned = false

        for (subset in subsets) {
            var solvedValues = board.getSolvedValuesForCells(subset)

            // Prune away conflicts in the subset. I.e. a cell cant have a value already used in the subset
            for (cell in subset) {
                if (cell.value == null && cell.pruneCandidates(solvedValues)) {
                    if (cell.attemptToSolve()) {
                        solvedValues = board.getSolvedValuesForCells(subset)
                        pruned = true
                    }
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
                        pruned = true
                    }
                }
            }
        }
        return pruned
    }
}
