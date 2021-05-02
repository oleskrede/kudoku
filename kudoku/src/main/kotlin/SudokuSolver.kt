class SudokuSolver(val board: Board) {
    fun solve() {
        print("Solving...")
        while (prunePossibilites()) {
            setValueInCellsWithSinglePossibility()
            if (board.isSolved()) break
        }
        println("\nSolving complete!")
    }

    private fun prunePossibilites(): Boolean {
        var pruned = false

        pruned = pruned || pruneSubsets(board.subGridsListFlattened)
        pruned = pruned || pruneSubsets(board.rows)
        pruned = pruned || pruneSubsets(board.columns)

        return pruned
    }

    private fun setValueInCellsWithSinglePossibility() {
        for (cell in board.cells) {
            if (cell.value == null && cell.possibilities.size == 1)
                cell.value = cell.possibilities.first()
        }
    }

    private fun pruneSubsets(subsets: List<List<Cell>>): Boolean {
        var pruned = false

        for (subset in subsets) {
            val solvedValues = board.getSolvedValuesForCells(subset)
            for (cell in subset) {
                if (cell.value == null) {
                    pruned = pruned || cell.prunePossibilities(solvedValues)
                }
            }
        }

        return pruned
    }
}
