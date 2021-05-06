# kudoku
A [Sudoku](https://en.wikipedia.org/wiki/Sudoku) solver written in Kotlin

It can solve any solvable Sudoku (even an empty one), at a decent speed (<30 ms on my computer).

## Running
Run from the `kudoku` directory:
```
./gradlew run
```

The you can open the (very basic) web-interface on `localhost:8080`.



### How it works
It is basically a depth-first search sprinkled with some basic candidate pruning logic.  
The solver first solves as much as it can with the candidate pruning. If it isn't enough to find a solution, then it goes one level deeper in the search-tree.

```
solutionCandidates = List[initialBoard]

while (solutionCandidates.last is not solved) {
  solve as much as possible by pruning
  if (solutionCandidates.last is solved):
    break
  else:
    choose an unsolved cell with few candidates remaining
    for each candidate:
      guess = copy of the board with choosen cell set to the candidate
      add guess to solutionCandidates
}
    
return solutionCandidates.last
```

### How it could be improved
Implementing more advanced pruning logic might make the solver faster. (Or at least make it more impressive).
