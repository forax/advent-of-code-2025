record Machine(BitSet state, List<BitSet> buttons) {}

int start(Machine machine) {
  record Work(BitSet state, int depth) {}
  var queue = new ArrayDeque<Work>();
  queue.add(new Work(new BitSet(), 0));
  var seen = new HashSet<BitSet>();
  for(;;) {
    var work = queue.remove();
    if (!seen.add(work.state)) {
      continue;
    }
    if (work.state.equals(machine.state)) {
      return work.depth;
    }
    for (var button : machine.buttons) {
      var newState = (BitSet) work.state.clone();
      newState.xor(button);
      queue.offer(new Work(newState, work.depth + 1));
    }
  }
}

void main() {
  var input = """
      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
      """;

  var buttonPattern = Pattern.compile("\\(([^)]+)\\)");
  var machines = input.lines()
      .map(l -> {
        var state = new BitSet();
        for (var i = 1; i < l.indexOf(']'); i++) {
          state.set(i - 1, l.charAt(i) == '#');
        }
        var buttons = buttonPattern.matcher(l).results()
            .map(r -> {
              var set = new BitSet();
              for(var token : r.group(1).split(",")) {
                set.set(Integer.parseInt(token));
              }
              return set;
            })
            .toList();
        return new Machine(state, buttons);
      })
      .toList();

  var sum = machines.stream()
      .mapToInt(this::start)
      .sum();
  IO.println(sum);
}
