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

  IO.println(input.lines().filter(l->!l.isEmpty()).map(l->new Scanner(l).findAll("(\\d+)").mapToLong(r->Long.parseLong(r.group())).toArray()).collect(Collectors.collectingAndThen(Collectors.partitioningBy(a->a.length==1),values->values.get(true).stream().filter(id->values.get(false).stream().anyMatch(r->r[0]<=id[0]&&id[0]<=r[1])).count())));
}
