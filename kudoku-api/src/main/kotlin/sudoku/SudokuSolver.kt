package sudoku

import sudoku.SudokuBoard.Companion.clone
import sudoku.SudokuBoard.Companion.fromString

class SudokuSolver(sudokuBoardString: String) {

    private val solutionCandidates = mutableListOf(fromString(sudokuBoardString))

    private fun popCandidate() = if (solutionCandidates.isNotEmpty()) solutionCandidates.removeLast() else null

    fun solve(): SudokuBoard? {
        var current = popCandidate()
        while (current != null && !current.isSolved()) {
            pruneCandidates(current)
            if (current.isSolved()) break
            if (current.isUnsolvable()) {
                current = popCandidate()
            } else {
                makeGuesses(current)
            }
        }
        return current
    }

    private fun pruneCandidates(current: SudokuBoard) {
        var pruningProgress = true
        while (pruningProgress) {
            pruningProgress = pruneSubsets(current, current.subGrids)
            pruningProgress = pruningProgress || pruneSubsets(current, current.rows)
            pruningProgress = pruningProgress || pruneSubsets(current, current.columns)
        }
    }

    private fun makeGuesses(current: SudokuBoard) {
        val cellWithFewestCandidates = current.cells
            .filter { cell -> cell.value == null }
            .minByOrNull { cell -> cell.valueCandidates.size }!!
        val index = current.cells.indexOf(cellWithFewestCandidates)

        for (candidate in cellWithFewestCandidates.valueCandidates) {
            current.cells[index].value = candidate
            solutionCandidates.add(clone(current))
        }
    }

    private fun pruneSubsets(current: SudokuBoard, subsets: List<List<Cell>>): Boolean {
        var pruningProgress = false
        for (subset in subsets) {
            var solvedValues = current.getSolvedValuesForCells(subset)

            // Prune away conflicts in the subset. I.e. a cell cant have a value already used in the subset
            for (cell in subset) {
                if (cell.value == null && cell.pruneCandidates(solvedValues)) {
                    if (cell.attemptToSolve()) {
                        solvedValues = current.getSolvedValuesForCells(subset)
                        pruningProgress = true
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
                        solvedValues = current.getSolvedValuesForCells(subset)
                        pruningProgress = true
                    }
                }
            }
        }
        return pruningProgress
    }
}
