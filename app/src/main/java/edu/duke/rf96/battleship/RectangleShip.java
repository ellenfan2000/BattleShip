package edu.duke.rf96.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{

  private final String name;

  public String getName(){
    return name;
  }
  
  static HashSet<Coordinate> makeCoords( Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        Coordinate c = new Coordinate(upperLeft.getRow() + h, upperLeft.getColumn() + w);
        ans.add(c);
      }
    }
    return ans;

  }

  public RectangleShip(String n, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> info) {
    super(makeCoords(upperLeft, width, height), info);
    this.name = n;
   }

  public RectangleShip(String n, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(n,upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship",upperLeft, 1, 1, data, onHit);
  }
}
