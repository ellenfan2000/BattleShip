package edu.duke.rf96.battleship;

public class BasicShip implements Ship<Character> {
  private final Coordinate myLocation;

  public BasicShip(Coordinate c){
    Coordinate c2 = new Coordinate(c.getRow(), c.getColumn());
    myLocation = c2;
  }
  

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    // TODO Auto-generated method stub
    return where.equals(myLocation);
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
  public Character getDisplayInfoAt(Coordinate where) {
    // TODO Auto-generated method stub
    return 's';
  }

}
