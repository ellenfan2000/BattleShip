package edu.duke.rf96.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;

  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param w              is the width of the newly constructed board.
   * @param h              is the height of the newly constructed board.
   * @param myShips        is a arraylists of all ships on the board
   * @param placementCheck is used to check whether the placement of a ship is
   *                       valid
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> rc) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    myShips = new ArrayList<Ship<T>>();
    placementChecker = rc;
  }

  public BattleShipBoard(int w, int h) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)));
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return height;
  }

  /**
   * add the ship to the list myShips
   * 
   * @return true if success, false if not
   */

  public boolean tryAddShip(Ship<T> toAdd) {
    if(placementChecker.checkPlacement(toAdd, this)){
      myShips.add(toAdd);
      return true;
    }
    return false;
  }

  /*
   * This method takes a Coordinate, and sees which (if any) Ship
   * occupies that coordinate. If one is found, we return whatever
   * displayInfo it has at those coordinates (for now, just 's'). If
   * none is found, we return null.
   */
  public T whatIsAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }

}
