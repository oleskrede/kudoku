class SudokuSolver(val sudokuBoard: SudokuBoard) {
    fun solve() {
        while (sudokuBoard.hasUnsolvedCells()) {
            if (!prunePossibilites()) break
        }
    }

    private fun prunePossibilites(): Boolean {
        var pruned = false
        pruned = pruned || pruneSubsets(sudokuBoard.subGrids)
        pruned = pruned || pruneSubsets(sudokuBoard.rows)
        pruned = pruned || pruneSubsets(sudokuBoard.columns)
        return pruned
    }

    private fun pruneSubsets(subsets: List<List<Cell>>): Boolean {
        var pruned = false

        for (subset in subsets) {
            var solvedValues = sudokuBoard.getSolvedValuesForCells(subset)

            // Prune away conflicts in the subset. I.e. a cell cant have a value already used in the subset
            for (cell in subset) {
                if (cell.value == null && cell.pruneCandidates(solvedValues)) {
                    if (cell.attemptToSolve()) {
                        solvedValues = sudokuBoard.getSolvedValuesForCells(subset)
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
                        solvedValues = sudokuBoard.getSolvedValuesForCells(subset)
                        pruned = true
                    }
                }
            }
        }
        return pruned
    }
}
