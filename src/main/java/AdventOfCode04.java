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

  var count = 0;
  for(var y = 0; y < height; y++) {
    for(var x = 0; x < width; x++) {
      if (grid[y][x] != '@') {
        continue;
      }
      var neighbors = 0;
      for(var j = -1; j <= 1; j++) {
        if (y + j < 0 || y + j >= height) {
          continue;
        }
        for(var i = -1; i <= 1; i++) {
          if (x + i < 0 || x + i >= width) {
            continue;
          }
          neighbors += grid[y + j][x + i] == '@' ? 1 : 0;
        }
      }
      if (neighbors < 5) {
        count++;
      }
    }
  }
  IO.println(count);
}
