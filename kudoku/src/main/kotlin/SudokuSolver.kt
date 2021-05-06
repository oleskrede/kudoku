import SudokuBoard.Companion.clone
import SudokuBoard.Companion.fromString

class SudokuSolver(sudokuBoardString: String) {

    private val solutionCandidates = mutableListOf(fromString(sudokuBoardString))

    private fun currentCandidate() = solutionCandidates.last()
    private fun dropCurrentCandidate() = solutionCandidates.removeLast()

    private var pruningProgress = false

    fun solve(): SudokuBoard {
        while (!currentCandidate().isSolved()) {
            pruneCandidates()
            if (currentCandidate().isSolved()) break
            if (currentCandidate().isUnsolvable()) {
                dropCurrentCandidate()
            } else {
                makeGuesses()
            }
        }
        return currentCandidate()
    }

    private fun pruneCandidates() {
        pruningProgress = true
        while (pruningProgress) {
            pruningProgress = false
            pruneSubsets(currentCandidate().subGrids)
            pruneSubsets(currentCandidate().rows)
            pruneSubsets(currentCandidate().columns)
        }
    }

    private fun makeGuesses() {
        val guessParent = dropCurrentCandidate() // will be discarded, so can safely be modified
        val cellWithFewestCandidates = guessParent.cells
            .filter { cell -> cell.value == null }
            .minByOrNull { cell -> cell.valueCandidates.size }!!
        val index = guessParent.cells.indexOf(cellWithFewestCandidates)

        for (candidate in cellWithFewestCandidates.valueCandidates) {
            guessParent.cells[index].value = candidate
            solutionCandidates.add(clone(guessParent))
        }
    }

    /**
     * TODO Add more advanced pruning logic for faster solutions
     */
    private fun pruneSubsets(subsets: List<List<Cell>>) {
        for (subset in subsets) {
            var solvedValues = currentCandidate().getSolvedValuesForCells(subset)

            // Prune away conflicts in the subset. I.e. a cell cant have a value already used in the subset
            for (cell in subset) {
                if (cell.value == null && cell.pruneCandidates(solvedValues)) {
                    if (cell.attemptToSolve()) {
                        solvedValues = currentCandidate().getSolvedValuesForCells(subset)
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
                        solvedValues = currentCandidate().getSolvedValuesForCells(subset)
                        pruningProgress = true
                    }
                }
            }
        }
    }
}
