void main(String[] args) {
  var input = """
    7,1
    11,1
    11,7
    9,7
    9,5
    2,5
    2,3
    7,3
    """;

  IO.println(input.lines()
      .map(l -> Arrays.stream(l.split(",")).mapToInt(Integer::parseInt).toArray())
      .<int[]>gather(Gatherer.ofSequential(
          ArrayList<int[]>::new,
          (state, element, _) -> state.add(element),
          (state, next) -> {
            for(var left : state) for(var right : state) next.push(new int[]{right[0] - left[0], right[1] - left[1]});
          }))
      .flatMapToInt(p -> p[0] >= 0 && p[1] >= 0 ? IntStream.of((p[0] + 1) * (p[1] + 1)) : null)
      .max().orElseThrow());
}
