int start(BitSet state, List<BitSet> buttons) {
  record Work(BitSet state, int depth) {}
  var queue = new ArrayDeque<Work>();
  queue.add(new Work(new BitSet(), 0));
  var seen = new HashSet<BitSet>();
  for(;;) {
    var work = queue.remove();
    if (!seen.add(work.state)) {
      continue;
    }
    if (work.state.equals(state)) {
      return work.depth;
    }
    for (var button : buttons) {
      var newState = (BitSet) work.state.clone();
      newState.xor(button);
      queue.offer(new Work(newState, work.depth + 1));
    }
  }
}

Collector<Integer, ?, BitSet> toBitSet() {
  return Collector.of(
      BitSet::new,
      BitSet::set,
      (set1, set2) -> { set1.or(set2); return set1; });
}

void main() {
  var input = """
      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
      """;

  var buttonPattern = Pattern.compile("\\(([^)]+)\\)");
  IO.println(input.lines()
      .mapToInt(l -> start(
          IntStream.range(0, l.indexOf(']') - 1)
            .filter(i -> l.charAt(i + 1) == '#')
            .boxed().collect(toBitSet()),
          buttonPattern.matcher(l).results()
              .map(r -> Arrays.stream(r.group(1).split(","))
                  .map(Integer::parseInt)
                  .collect(toBitSet()))
              .toList()))
      .sum());
}
