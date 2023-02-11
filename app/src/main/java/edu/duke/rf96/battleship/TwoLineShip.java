package edu.duke.rf96.battleship;

import java.util.HashSet;

public class TwoLineShip<T> extends BasicShip<T> {
  /**
   * this class represents the new Battleship and carrier that takes two line
   * view the ship as two pieces of rectangle ship
   */
  private final String name;

  public String getName() {
    return name;
  }

  /**
   * @return a hash set of all the coordinates the ship takes
   * @param line1 the left corner of the first line
   * @param line2 the left corner of the second line
   */
  static HashSet<Coordinate> makeCoords(Coordinate line1, int width1, int height1, Coordinate line2, int width2,
      int height2) {
    HashSet<Coordinate> ans = RectangleShip.makeCoords(line1, width1, height1);
    ans.addAll(RectangleShip.makeCoords(line2, width2, height2));
    return ans;
  }

  /**
   * @param center: the line with only one square in battleship
   *                in carrier: the coner of the line with 4 squares
   */
  public TwoLineShip(String n, Coordinate line1, int width1, int height1, Coordinate line2, int width2, int height2,
      ShipDisplayInfo<T> myinfo, ShipDisplayInfo<T> enminfo, Placement center) {
    super(makeCoords(line1, width1, height1, line2, width2, height2), myinfo, enminfo, center);
    this.name = n;
  }

  public TwoLineShip(String n, Coordinate line1, int width1, int height1, Coordinate line2, int width2, int height2,
      T data, T onHit, Placement center) {
    this(n, line1, width1, height1, line2, width2, height2, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data), center);
  }

}
