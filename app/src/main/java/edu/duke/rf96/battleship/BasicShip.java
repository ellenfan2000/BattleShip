
package edu.duke.rf96.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  //private final Coordinate myLocation;

  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  // public BasicShip(Coordinate c){
  //   // Coordinate c2 = new Coordinate(c.getRow(), c.getColumn());
  //   // myLocation = c2;
  //   //myPieces = RectangleShip.makeCoords(c, 1, 2);
  //  myPieces = new HashMap<Coordinate, Boolean>();
  //  myPieces.put(c, false); 
  // }

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo){
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c: where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    
  }

  protected void checkCoordinateInThisShip(Coordinate c){
    if(myPieces.get(c) == null){
      throw new IllegalArgumentException("The coornidate is not in the ship");
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    for(Coordinate c: myPieces.keySet()){
      if(!myPieces.get(c)){
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
