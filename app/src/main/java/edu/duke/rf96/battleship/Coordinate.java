package edu.duke.rf96.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public Coordinate(int r, int c) {
    row = r;
    column = c;
  }

  /**
   * this construct Coordinate class through a string e.g. A2 = (0, 2)
   */
  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate should have two characters but have" + descr.length());
    }

    // Case does not matter
    descr = descr.toUpperCase();
    char r = descr.charAt(0);
    char c = descr.charAt(1);

    // invalid strings
    if (r < 'A' || r > 'Z') {
      throw new IllegalArgumentException("Coordinate row should be between A and Z, but is " + r);
    }
    if (c < '0' || c > '9') {
      throw new IllegalArgumentException("Coordinate column should be between 1 and 9, but is " + c);
    }

    row = r - 'A';
    column = c - '0';

  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
