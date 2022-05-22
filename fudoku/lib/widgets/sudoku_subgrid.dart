import 'dart:developer';

import 'package:flutter/material.dart';

class SudokuSubgrid extends StatelessWidget {
  final List<int> cells;

  const SudokuSubgrid({Key? key, required this.cells}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(border: Border.all(color: Colors.grey)),
      child: GridView.count(
        crossAxisCount: 3,
        shrinkWrap: true,
        children: cells
            .asMap()
            .keys
            .map(
              (i) => Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.shade200),
                ),
                child: Center(
                  child: TextField(
                    decoration: const InputDecoration(border: InputBorder.none),
                    textAlign: TextAlign.center,
                    controller:
                        TextEditingController(text: cells[i].toString()),
                  ),
                ),
                // child: cells[i] == 0
                //     ? const Text("")
                //     : Text(
                //         '${cells[i]}',
                //       ),
              ),
            )
            .toList(),
      ),
    );
  }
}
