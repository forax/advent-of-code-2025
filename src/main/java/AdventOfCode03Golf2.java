void main() {
  var input = """
      987654321111111
      811111111111119
      234234234234278
      818181911112111
      """;

  record Pair(int left, int right) {
    int result() {
      return left * 10 + right;
    }
  }

  IO.println(input.lines()
      .mapToInt(line ->  line.chars()
          .mapToObj(i -> i - '0')
          .reduce(List.of(new Pair(0, 0)),
              (list, val) -> {
                var pair = list.getLast();
                return val > pair.left?
                    List.of(new Pair(pair.left, val), new Pair(val, 0)) :
                    List.of(new Pair(pair.left, Math.max(pair.right, val)));
              },
              (_, _) -> null)
          .getFirst().result())
      .sum());
}
