void main() {
  var input = """
    7,1
    11,1
    11,7
    9,7
    9,5
    2,5
    2,3
    7,3
    """;

  record Pair(int x, int y) {}
  var pos = input.lines()
      .map(l -> {
        var tokens = l.split(",");
        return new Pair(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
      })
      .toList();
  record Box(int width, int height) {}
  var max = pos.stream()
      .flatMap(left -> pos.stream().flatMap(right -> {
        var width = right.x - left.x;
        var height = right.y - left.y;
        return width >= 0 && height >= 0 ? Stream.of(new Box(width, height)) : null;
      }))
      .mapToInt(box -> (box.width + 1) * (box.height + 1))
      .max()
      .orElseThrow();
  IO.println(max);
}
