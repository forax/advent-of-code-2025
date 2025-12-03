void main() {
  var input = """
      11-22,95-115,998-1012,1188511880-1188511890,222220-222224,\
      1698522-1698528,446443-446449,38593856-38593862,565653-565659,\
      824824821-824824827,2121212118-2121212124\
      """;

  var sum = 0;
  for(var text : input.split(",")) {
    var tokens = text.split("-");
    var start = Integer.parseInt(tokens[0]);
    var end = Integer.parseInt(tokens[1]);

    for(var i = start; i <= end; i++) {
      var number = "" + i;
      var length = number.length();
      var part1 = number.substring(0, length / 2);
      var part2 = number.substring(length / 2);
      if (part1.equals(part2)) {
        sum += i;
      }
    }
  }
  IO.println(sum);
}
