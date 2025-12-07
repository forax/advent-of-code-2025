void main() {
  var input = """
    123 328 51  64
    45  64  387 23
    6   98  215 314
    *   +   *   +
    """;

  var grid = input.lines().map(l -> l.split(" +")).toArray(String[][]::new);
  var width = grid[0].length;
  var height = grid.length;
  IO.println(IntStream.range(0, width)
      .map(col -> IntStream.range(0, height - 1)
          .map(row -> Integer.parseInt(grid[row][col]))
          .reduce(grid[height - 1][col].equals("+") ? Math::addExact : Math::multiplyExact)
          .orElseThrow())
      .sum());
}
