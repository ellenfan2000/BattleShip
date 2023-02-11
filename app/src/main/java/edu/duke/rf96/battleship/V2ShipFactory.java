package edu.duke.rf96.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {
  /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    if (where.getOrientation() != 'V' && where.getOrientation() != 'H') {
      throw new IllegalArgumentException("Placement Orientation should be H or V");
    }
    V1ShipFactory fac = new V1ShipFactory();
    return fac.makeSubmarine(where);
  }

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    Coordinate upperleft = where.getCoordinate();
    int row = upperleft.getRow();
    int col = upperleft.getColumn();
    Character orien = where.getOrientation();
    if (orien == 'V' || orien == 'H') {
      throw new IllegalArgumentException("Placement Orientation should be U, R, D, L ");
    }

    if (orien == 'U') {
      return new TwoLineShip<Character>("Battleship", new Coordinate(row, col + 1), 1, 1, new Coordinate(row + 1, col),
          3, 1, 'b', '*', new Placement(new Coordinate(row, col + 1), 'U'));
    } else if (orien == 'R') {
      return new TwoLineShip<Character>("Battleship", new Coordinate(row + 1, col + 1), 1, 1, new Coordinate(row, col),
          1, 3, 'b', '*', new Placement(new Coordinate(row + 1, col + 1), 'R'));
    } else if (orien == 'D') {
      return new TwoLineShip<Character>("Battleship", new Coordinate(row + 1, col + 1), 1, 1, new Coordinate(row, col),
          3, 1, 'b', '*', new Placement(new Coordinate(row + 1, col + 1), 'D'));
    } else {
      return new TwoLineShip<Character>("Battleship", new Coordinate(row + 1, col), 1, 1, new Coordinate(row, col + 1),
          1, 3, 'b', '*', new Placement(new Coordinate(row + 1, col), 'L'));
    }

  }

  /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    Coordinate upperleft = where.getCoordinate();
    int row = upperleft.getRow();
    int col = upperleft.getColumn();
    Character orien = where.getOrientation();
    if (orien == 'V' || orien == 'H') {
      throw new IllegalArgumentException("Placement Orientation should be U, R, D, L ");
    }

    if (orien == 'U') {
      return new TwoLineShip<Character>("Carrier", new Coordinate(row + 2, col + 1), 1, 3, new Coordinate(row, col), 1,
          4, 'c', '*', new Placement(new Coordinate(row, col), 'U'));
    } else if (orien == 'R') {
      return new TwoLineShip<Character>("Carrier", new Coordinate(row + 1, col), 3, 1, new Coordinate(row, col + 1), 4,
          1, 'c', '*', new Placement(new Coordinate(row, col + 4), 'R'));
    } else if (orien == 'D') {
      return new TwoLineShip<Character>("Carrier", new Coordinate(row, col), 1, 3, new Coordinate(row + 1, col + 1), 1,
          4, 'c', '*', new Placement(new Coordinate(row + 4, col + 1), 'D'));
    } else {
      return new TwoLineShip<Character>("Carrier", new Coordinate(row, col + 2), 3, 1, new Coordinate(row + 1, col), 4,
          1, 'c', '*', new Placement(new Coordinate(row + 1, col), 'L'));
    }

  }

  /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    if (where.getOrientation() != 'V' && where.getOrientation() != 'H') {
      throw new IllegalArgumentException("Placement Orientation should be H or V");
    }
    V1ShipFactory fac = new V1ShipFactory();
    return fac.makeDestroyer(where);
  }

}
