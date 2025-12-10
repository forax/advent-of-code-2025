final class Circuit<E> {
  final E value;
  Circuit<E> parent;

  Circuit(E value) {
    this.value = value;
    super();
  }

  Circuit<E> find() {
    return parent == null ? this : parent.find();
  }

  Circuit<E> union(Circuit<E> circuit) {
    var parent1 = find();
    var parent2 = circuit.find();
    if (parent1 == parent2) {
      return parent1;
    }
    parent1.parent = parent2;
    return parent2;
  }
}

record Box(long x, long y, long z) {
  long distance(Box box) {
    var dx = x - box.x;
    var dy = y - box.y;
    var dz = z - box.z;
    return dx * dx + dy * dy + dz * dz;
  }
}

void main() {
  var input = """
    162,817,812
    57,618,57
    906,360,560
    592,479,940
    352,342,300
    466,668,158
    542,29,236
    431,825,988
    739,650,466
    52,470,668
    216,146,977
    819,987,18
    117,168,530
    805,96,715
    346,949,466
    970,615,88
    941,993,340
    862,61,35
    984,92,344
    425,690,689
    """;
  var shortestPairs = 10;
  var largestCircuits = 3;

  record Pair(Circuit<Box> left, Circuit<Box> right, long distance) { }

  var pattern = Pattern.compile("\\d+");
  var circuits = pattern.matcher(input).results()
      .map(r -> Integer.parseInt(r.group()))
      .gather(Gatherers.windowFixed(3))
      .map(list -> new Circuit<>(new Box(list.get(0), list.get(1), list.get(2))))
      .toList();
  IntStream.range(0, circuits.size()).boxed()
      .flatMap(i -> IntStream.range(0, i).mapToObj(j -> {
        var left = circuits.get(i);
        var right = circuits.get(j);
        return new Pair(left, right, left.value.distance(right.value));
      }))
      .sorted(Comparator.comparingLong(Pair::distance))
      .limit(shortestPairs)  // only ten
      .forEach(pair -> pair.left.union(pair.right));
  var product = circuits.stream()
      .collect(Collectors.groupingBy(Circuit::find, Collectors.counting()))
      .values().stream()
      .sorted(Comparator.reverseOrder())
      .limit(largestCircuits)  // only three
      .reduce(1L, (a, b) -> a * b);
  IO.println(product);
}
