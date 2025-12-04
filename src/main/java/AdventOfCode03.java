int indexOfMax(String s, int start, int end) {
  return IntStream.range(start, end)
      .boxed()
      .max(Comparator.comparing(s::charAt))
      .orElseThrow();
}

int val(String s, int index) {
  return s.charAt(index) - '0';
}

void main() {
  var input = """
      987654321111111
      811111111111119
      234234234234278
      818181911112111
      """;

  var sum = 0;
  for(var line : input.lines().toList()) {
    var index = indexOfMax(line, 0, line.length() - 1);
    sum += val(line, index) * 10 + val(line, indexOfMax(line, index + 1, line.length()));
  }
  IO.println(sum);
}
