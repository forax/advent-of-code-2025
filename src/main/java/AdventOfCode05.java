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

  var data = input.split("\n\n");
  var fresh = data[0].lines().map(l -> l.split("-"))
      .map(l -> List.of(Integer.parseInt(l[0]), Integer.parseInt(l[1])))
      .toList();
  var ids = data[1].lines().map(Integer::parseInt).toList();
  var set = new BitSet();
  for(var range : fresh) {
    set.set(range.getFirst(), range.getLast() + 1);
  }
  var count = 0;
  for(var id : ids) {
   if (set.get(id)) {
     count++;
   }
  }
  IO.println(count);
}
