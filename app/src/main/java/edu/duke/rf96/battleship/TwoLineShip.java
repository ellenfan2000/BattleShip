package edu.duke.rf96.battleship;

import java.util.HashSet;

public class TwoLineShip<T> extends BasicShip<T>{
  private final String name;

  public String getName(){
    return name;
  }

  static HashSet<Coordinate> makeCoords(Coordinate line1, int width1, int height1, Coordinate line2, int width2, int height2){
    HashSet<Coordinate> ans = RectangleShip.makeCoords(line1, width1, height1);
    ans.addAll(RectangleShip.makeCoords(line2, width2, height2));
    return ans;
  }

  public TwoLineShip(String n, Coordinate line1, int width1, int height1,Coordinate line2, int width2, int height2, ShipDisplayInfo<T> myinfo,  ShipDisplayInfo<T> enminfo) {
    super(makeCoords(line1, width1, height1, line2, width2, height2), myinfo, enminfo);
    this.name = n;
  }

  public TwoLineShip(String n, Coordinate line1, int width1, int height1,Coordinate line2, int width2, int height2, T data, T onHit) {
    this(n, line1, width1, height1, line2, width2, height2, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

}
