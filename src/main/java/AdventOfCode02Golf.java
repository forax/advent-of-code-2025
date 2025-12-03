void main() {
  var input = """
      11-22,95-115,998-1012,1188511880-1188511890,222220-222224,\
      1698522-1698528,446443-446449,38593856-38593862,565653-565659,\
      824824821-824824827,2121212118-2121212124\
      """;

  var rangePattern = Pattern.compile("(\\d+)");
  var twicePattern = Pattern.compile("(\\d+)(\\1)");
  IO.println(rangePattern.matcher(input).results()
      .map(result -> Integer.parseInt(result.group()))
      .gather(Gatherers.windowFixed(2))
      .flatMapToInt(list -> IntStream.rangeClosed(list.getFirst(), list.getLast()))
      .filter(n -> twicePattern.matcher("" + n).matches())
      .sum());
}
