class Transaction {
  static var idCounter = 0;

  final int _id;

  final String title;
  final int amount;
  final DateTime date;

  Transaction({
    required this.title,
    required this.amount,
    required this.date,
  }) : _id = idCounter++;

  int get id => _id;
}
