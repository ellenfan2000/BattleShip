
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
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enDisplayInfo ) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enDisplayInfo;

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
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    checkCoordinateInThisShip(where);
    if (myShip){
    return myDisplayInfo.getInfo(where, myPieces.get(where));
  }
    else{
      return enemyDisplayInfo.getInfo(where,myPieces.get(where));
    }
  }

  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

}
