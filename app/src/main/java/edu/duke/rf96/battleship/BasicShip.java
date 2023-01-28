
package edu.duke.rf96.battleship;

import java.util.HashMap;

/**
 * Basic ship is an abstract class of text version ships
 * 
 * @param myPieces:     stores coordinates the ship takes
 *                      if myPieces.get(c) is null, c is not part of this Ship
 *                      if myPieces.get(c) is false, c is part of this ship and
 *                      has not been hit
 *                      if myPieces.get(c) is true, c is part of this ship and
 *                      has been hitmyPiece
 * @param myDisplayInfo
 */
public abstract class BasicShip<T> implements Ship<T> {

  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  // public BasicShip(Coordinate c){
  // // Coordinate c2 = new Coordinate(c.getRow(), c.getColumn());
  // // myLocation = c2;
  // //myPieces = RectangleShip.makeCoords(c, 1, 2);
  // myPieces = new HashMap<Coordinate, Boolean>();
  // myPieces.put(c, false);
  // }

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;

  }

  /**
   * check whether the input coordinate is a part of the ship
   * 
   * @param coordinate to be checked
   *                   if not, throw exception
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (myPieces.get(c) == null) {
      throw new IllegalArgumentException("The coornidate is not in the ship");
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {

    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    for (Coordinate c : myPieces.keySet()) {
      if (!myPieces.get(c)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);

  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myDisplayInfo.getInfo(where, myPieces.get(where));
  }

}
