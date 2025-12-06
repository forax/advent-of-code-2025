void main() {
  var input = """
    3-5
    10-14
    16-20
    12-18

    1
    5
    8
    11
    17
    32
    """;

  var rangePattern = Pattern.compile("(\\d+)-(\\d+)");
  var fresh = rangePattern.matcher(input).results()
      .collect(Collectors.toMap(
          r -> Long.parseLong(r.group(1)), r -> Long.parseLong(r.group(2)),
          Math::max, TreeMap::new));
  fresh.merge(Long.MIN_VALUE, -1L, Math::max);  // there is always a floor entry
  IO.println(input.substring(input.indexOf("\n\n") + 2).lines()
      .mapToLong(Long::parseLong)
      .filter(id -> id <= fresh.floorEntry(id).getValue())
      .count());
}
