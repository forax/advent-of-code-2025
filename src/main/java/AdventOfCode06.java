void parse(String input, List<IntBinaryOperator> ops, List<List<Integer>> data) {
  var lines = input.lines().toList();
  for (var token : lines.getFirst().split(" +")) {
    var list = new ArrayList<Integer>();
    list.add(Integer.parseInt(token));
    data.add(list);
  }
  for (var i = 1; i < lines.size() - 1; i++) {
    var tokens = lines.get(i).split(" +");
    for (int j = 0; j < tokens.length; j++) {
      data.get(j).add(Integer.parseInt(tokens[j]));
    }
  }
  for (var token : lines.getLast().split(" +")) {
    ops.add(token.equals("+") ? Math::addExact : Math::multiplyExact);
  }
}

int eval(List<IntBinaryOperator> ops, List<List<Integer>> data) {
  var sum = 0;
  for(var i = 0; i < ops.size(); i++) {
    var values = data.get(i);
    var op = ops.get(i);
    sum += values.stream().mapToInt(x -> x).reduce( op).orElseThrow();
  }
  return sum;
}

void main() {
  var input = """
    123 328 51  64
    45  64  387 23
    6   98  215 314
    *   +   *   +
    """;

  var data = new ArrayList<List<Integer>>();
  var ops = new ArrayList<IntBinaryOperator>();
  parse(input,ops,data);
  IO.println(eval(ops, data));
}
