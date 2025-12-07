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

  var ranges = input.lines()
      .takeWhile(Predicate.not(String::isEmpty))
      .map(l -> Arrays.stream(l.split("-")).mapToLong(Long::parseLong).toArray())
      .toList();
  IO.println(input.lines()
      .dropWhile(Predicate.not(String::isEmpty))
      .skip(1)
      .map(Long::parseLong)
      .filter(id -> ranges.stream().anyMatch(r -> r[0] <= id && id <= r[1]))
      .count());
}
