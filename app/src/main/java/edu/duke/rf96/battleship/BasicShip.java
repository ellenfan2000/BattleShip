
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
  protected Placement center;
  

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enDisplayInfo, Placement center) {
    myPieces = new HashMap<Coordinate, Boolean>();
    for (Coordinate c : where) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enDisplayInfo;
    this.center = center;
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
   @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  @Override
  public Coordinate getCenter(){
    return center.getCoordinate();
  }

  /**
     Calculate the relative position to the center point in the
     up or horizontal view. 
   */
  
   @Override
  public Coordinate getRelativeCoordinate(Coordinate defini){
    checkCoordinateInThisShip(defini);
    int row = defini.getRow() - center.getCoordinate().getRow();
    int col = defini.getColumn() - center.getCoordinate().getColumn();
    Character orien = center.getOrientation();
    if(orien == 'U'){
      return new Coordinate(row, col);
    }
    else if(orien == 'R'){
      return new Coordinate(0-col,row);
    }
    else if(orien == 'D'){
      return new Coordinate(0-row,0-col);
    }
    else if(orien == 'L'){
      return new Coordinate(col,0-row);
    }
    else if(orien == 'H'){
      return new Coordinate(row, col);
    }
    else{
      return new Coordinate(col, row);
    }
  }

   @Override
  public Coordinate getDefinCoordinate(Coordinate relat){
     int row = center.getCoordinate().getRow();
     int col = center.getCoordinate().getColumn();
    Character orien = center.getOrientation();
    if(orien == 'U'){
      row +=relat.getRow();
      col +=relat.getColumn();
      return new Coordinate(row, col);
    }
    else if(orien == 'D'){
      row -=relat.getRow();
      col -=relat.getColumn();
      return new Coordinate(row, col);
    }
    else if(orien == 'R'){
      row +=relat.getColumn();
      col -=relat.getRow();
      return new Coordinate(row,col);
    }
    else if(orien == 'L'){
      row -=relat.getColumn();
      col +=relat.getRow();
      return new Coordinate(row,col);
    }
    else if(orien == 'H'){
      row +=relat.getRow();
      col +=relat.getColumn();
      return new Coordinate(row, col);
    }
    else{
      row +=relat.getColumn();
      col +=relat.getRow();
     return new Coordinate(row, col);
    }
  }
}
