void main() {
  var input = """
      11-22,95-115,998-1012,1188511880-1188511890,222220-222224,\
      1698522-1698528,446443-446449,38593856-38593862,565653-565659,\
      824824821-824824827,2121212118-2121212124\
      """;

  var map = new TreeMap<Integer, Long>();
  var sum = 0L;
  var bases = List.of(1, 10, 100, 1000, 10_000, 100_000);
  for (var j = 1; j < bases.size(); j++) {
    var start = (int) bases.get(j - 1);
    var end = (int) bases.get(j);
    for (var i = start; i < end; i++) {
      var value = i * end + i;
      sum += value;
      map.put(value, sum);
    }
  }

  IO.println(Pattern.compile("(\\d+)").matcher(input).results()
      .map(result -> Integer.parseInt(result.group()))
      .gather(Gatherers.windowFixed(2))
      .mapToLong(list -> {
        var subMap = map.subMap(list.getFirst(), true, list.getLast(), true);
        if (subMap.isEmpty()) {
          return 0;
        }
        var firstEntry = subMap.firstEntry();
        return firstEntry.getKey() + subMap.lastEntry().getValue() - firstEntry.getValue();
      })
      .sum());
}
