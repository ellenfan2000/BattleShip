package edu.duke.rf96.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {
  /**
   * The class makes all kinds of ships
   */

  /**
   * Actually create a rectangle ship
   * 
   * @param where specifies the location, orientation and type of the ship to make
   * @return the specific kins of ship
   */
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
    int temp = 0;
    if (where.getOrientation() == 'H') {
      temp = w;
      w = h;
      h = temp;
    }
    Ship<Character> s = new RectangleShip<Character>(name, where.getCoordinate(), w, h, letter, '*', where);
    return s;
  }

  /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }
}
