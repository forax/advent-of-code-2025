void main() {
  var input = """
      987654321111111
      811111111111119
      234234234234278
      818181911112111
      """;

  record Value(int val, boolean last) {}
  record Pair(int left, int right) {
    Pair max(Value value) {
      return value.val > left && !value.last ? new Pair(value.val, 0) : new Pair(left, Math.max(right, value.val));
    }

    int result() {
      return left * 10 + right;
    }
  }

  IO.println(input.lines()
      .mapToInt(line -> IntStream.range(0, line.length())
          .mapToObj(i -> new Value(line.charAt(i) - '0', i == line.length() - 1))
          .gather(Gatherers.fold(() -> new Pair(0, 0), Pair::max))
          .findFirst().orElseThrow().result())
      .sum());
}
