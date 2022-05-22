import 'package:flutter/material.dart';
import 'package:fudoku/widgets/sudoku_subgrid.dart';

class SudokuBoard extends StatelessWidget {
  final List<List<int>> _sudokuCells = List.generate(9, (i) {
    return List.filled(9, i);
  });

  SudokuBoard({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: ConstrainedBox(
        constraints: const BoxConstraints(
          maxWidth: 400,
        ),
        child: Container(
          decoration: BoxDecoration(
            border: Border.all(width: 2),
          ),
          child: GridView.count(
            shrinkWrap: true,
            crossAxisCount: 3,
            children: _sudokuCells
                .map(
                  (e) => Center(
                    child: SudokuSubgrid(cells: e),
                  ),
                )
                .toList(),
          ),
        ),
      ),
    );
    // Container(
    //   height: 300,
    //   child: ListView.builder(
    //     itemBuilder: (ctx, i) {
    //       final tx = _sudokuCells[i];
    //       return Card(
    //         margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 20),
    //         elevation: 5,
    //         child: Text('$tx'),
    //       );
    //     },
    //     itemCount: _sudokuCells.length,
    //   ),
    // );
  }
}
