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

  IO.println(input.lines()
      .map(s -> (s.charAt(0) == 'L' ? -1 : 1) * Integer.parseInt(s.substring(1)))
      .gather(Gatherers.scan(() -> 50, (dial, dist) -> Math.floorMod(dial + dist, 100)))
      .filter(dial -> dial == 0)
      .count());
}
