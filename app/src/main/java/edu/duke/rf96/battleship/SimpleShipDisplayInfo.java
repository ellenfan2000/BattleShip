package edu.duke.rf96.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {

  /**
     This class presents the view of a shape, flexible to all kinds
   */
  private final T myData;
  private final T onHit;

  public SimpleShipDisplayInfo (T data, T hit){
    myData = data;
    onHit = hit;
  }

  /**
     getInfo check if (hit) and returns onHit if so, and myData otherwise.
   */
  @Override
  public T getInfo(Coordinate where, boolean hit){
    if(hit){
      return onHit;
    }
    else{
      return myData;
    }
  }
}
