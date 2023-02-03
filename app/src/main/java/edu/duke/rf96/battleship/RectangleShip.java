package edu.duke.rf96.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  /**
   * This class represents a rectangle ship
   * 
   * @param name: the ship's type name
   */
  private final String name;

  public String getName() {
    return name;
  }

  /**
   * @return a hash set of all the coordinates the ship takes
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> ans = new HashSet<Coordinate>();
    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        Coordinate c = new Coordinate(upperLeft.getRow() + h, upperLeft.getColumn() + w);
        ans.add(c);
      }
    }
    return ans;

  }

  /**
   * three types of constructors
   */
  public RectangleShip(String n, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myinfo,  ShipDisplayInfo<T> enminfo) {
    super(makeCoords(upperLeft, width, height), myinfo, enminfo);
    this.name = n;
  }

  public RectangleShip(String n, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(n, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  // a test version constructor
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }
}
