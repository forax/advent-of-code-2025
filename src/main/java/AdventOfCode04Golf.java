void main() {
  var input = """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.
      """;

  var grid = input.lines().map(l -> l.chars().toArray()).toArray(int[][]::new);
  var width = grid[0].length;
  var height = grid.length;

  IO.println(IntStream.range(0, height)
      .flatMap(y -> IntStream.range(0, width)
          .filter(x -> grid[y][x] == '@')
          .map(x -> IntStream.rangeClosed(-1, 1).filter(j -> y + j >= 0 && y + j < height)
              .flatMap(j -> IntStream.rangeClosed(-1, 1)
                  .filter(i -> x + i >= 0 && x + i < width)
                  .map(i -> grid[y + j][x + i] == '@' ? 1 : 0))
              .sum())
          .filter(n -> n < 5))
      .count());
}
