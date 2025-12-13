record Pos(int x, int y) {
  Pos add(Pos other) {
    return new Pos(x + other.x, y + other.y);
  }
}

record Shape(List<Pos> posList) {
  static Shape create(List<String> lines) {
    var grid = lines.stream().map(String::toCharArray).toArray(char[][]::new);
    return new Shape(IntStream.rangeClosed(-1, 1).boxed()
        .flatMap(j -> IntStream.rangeClosed(-1, 1).boxed()
            .flatMap(i -> grid[j + 1][i + 1] == '#' ? Stream.of(new Pos(i, j)) : null))
        .toList());
  }

  Shape rotateRight() {
    return new Shape(posList.stream().map(p -> new Pos(-p.y, p.x)).toList());
  }

  Shape relativize() {
    if (posList.stream().anyMatch(p -> p.x == 0 && p.y == 0)) {
      return this;
    }
    return new Shape(posList.stream().map(p -> new Pos(p.x + 1, p.y + 1)).toList());
  }

  List<Shape> allRotations() {
    var rotateRight = rotateRight().relativize();
    var flip = rotateRight().rotateRight().relativize();
    var rotateLeft = rotateRight().rotateRight().rotateRight().relativize();
    return Stream.of(relativize(), rotateRight, flip, rotateLeft).distinct().toList();
  }

  @Override
  public String toString() {
    var minX = posList.stream().mapToInt(Pos::x).min().orElse(0);
    var maxX = posList.stream().mapToInt(Pos::x).max().orElse(0);
    var minY = posList.stream().mapToInt(Pos::y).min().orElse(0);
    var maxY = posList.stream().mapToInt(Pos::y).max().orElse(0);
    var set = new HashSet<>(posList);
    return IntStream.rangeClosed(minY, maxY).boxed()
        .map(j -> IntStream.rangeClosed(minX, maxX)
            .mapToObj(i -> set.contains(new Pos(i, j)) ? "#" : ".")
            .collect(Collectors.joining()))
        .collect(Collectors.joining("\n"));
  }
}

record Grid(int width, int height, Set<Pos> posSet) {
  static Grid create(int width, int height) {
    return new Grid(width, height, IntStream.range(0, height).boxed()
        .flatMap(j -> IntStream.range(0, width).mapToObj(i -> new Pos(i, j)))
        .collect(Collectors.toSet()));
  }

  boolean containedAt(Pos pos, Shape shape) {
    return shape.posList.stream().allMatch(p -> posSet.contains(pos.add(p)));
  }

  Grid remove(Pos pos, Shape shape) {
    var newSet = new HashSet<>(posSet);
    shape.posList.forEach(p -> newSet.remove(pos.add(p)));
    return new Grid(width, height,newSet);
  }

  @Override
  public String toString() {
    return IntStream.range(0, height).boxed()
        .map(j -> IntStream.range(0, width)
            .mapToObj(i -> posSet.contains(new Pos(i, j)) ? "." : "#")
            .collect(Collectors.joining("")))
        .collect(Collectors.joining("\n"));
  }
}

record Region(Grid grid, List<List<Shape>> shapes) {}

boolean fit(Grid grid, List<List<Shape>> shapesList, int n) {
  if (shapesList.size() == n) {
    return true;
  }
  for(var pos : grid.posSet) {
    for(var shape : shapesList.get(n)) {
      if (grid.containedAt(pos, shape)) {
        if (fit(grid.remove(pos, shape), shapesList, n + 1)) {
          return true;
        }
      }
    }
  }
  return false;
}

void main() {
  var input = """
    0:
    ###
    ##.
    ##.
    
    1:
    ###
    ##.
    .##
    
    2:
    .##
    ###
    ##.
    
    3:
    ##.
    ###
    ##.
    
    4:
    ###
    #..
    ###
    
    5:
    ###
    .#.
    ###
    
    4x4: 0 0 0 0 2 0
    12x5: 1 0 1 0 2 2
    12x5: 1 0 1 0 3 2
    """;

  var separation = input.lastIndexOf("\n\n");
  var shapes = input.substring(0, separation)
      .lines()
      .filter(l -> !l.isEmpty() && !l.endsWith(":"))
      .gather(Gatherers.windowFixed(3))
      .map(Shape::create)
      .toList();
  var regions = input.substring(separation + 2)
      .lines()
      .map(l -> Arrays.stream(l.split("[: x]+")).mapToInt(Integer::parseInt).toArray())
      .map(array -> new Region(
          Grid.create(array[0], array[1]),
          IntStream.range(0, shapes.size()).boxed()
              .flatMap(i -> IntStream.range(0, array[2 + i]).mapToObj(_ -> shapes.get(i)))
              .map(Shape::allRotations)
              .toList()))
      .toList();
  IO.println(regions.stream().filter(r -> fit(r.grid, r.shapes, 0)).count());
}
