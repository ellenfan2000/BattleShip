package edu.duke.rf96.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;
  private final T onHit;

  public SimpleShipDisplayInfo (T data, T hit){
    myData = data;
    onHit = hit;
  }

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
