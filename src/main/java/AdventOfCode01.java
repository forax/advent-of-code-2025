void main() {
  var input = """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
      """;

  enum Dir { LEFT, RIGHT }
  record Step(Dir dir, int distance) {}

  var steps = input.lines()
      .map(line -> {
        var dir = line.charAt(0) == 'L' ? Dir.LEFT : Dir.RIGHT;
        var distance = Integer.parseInt(line.substring(1));
        return new Step(dir, distance);
      })
      .toList();

  var dial = 50;
  var count = 0;
  for (var step : steps) {
    var rotation = step.dir == Dir.LEFT ? -step.distance : step.distance;
    dial = (dial + rotation + 100) % 100;
    if (dial == 0) {
      count++;
    }
  }
  System.out.println(count);
}
