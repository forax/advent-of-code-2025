int path(Map<String, List<String>> dag, String node, Map<String, Integer> countMap) {
  var count = countMap.get(node);
  if (count != null) {
    return count;
  }
  if (node.equals("out")) {
    return 1;
  }
  var sum = 0;
  for(var neighbor : dag.get(node)) {
    sum += path(dag, neighbor, countMap);
  }
  countMap.put(node, sum);
  return sum;
}

void main() {
  var input = """
      aaa: you hhh
      you: bbb ccc
      bbb: ddd eee
      ccc: ddd eee fff
      ddd: ggg
      eee: out
      fff: out
      ggg: out
      hhh: ccc fff iii
      iii: out
      """;

  var dag = input.lines()
      .map(l -> l.split("[:\\s]+"))
      .collect(Collectors.toMap(a -> a[0], a -> Arrays.stream(a).skip(1).toList()));

  IO.println(path(dag, "you", new HashMap<>()));
}
