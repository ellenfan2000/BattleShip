
package edu.duke.rf96.battleship;

import java.util.HashMap;

public class BasicShip<T> implements Ship<T> {
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

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // TODO Auto-generated method stub
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
   return myDisplayInfo.getInfo(where, false);
  }

}
