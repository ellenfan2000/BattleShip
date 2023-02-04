package edu.duke.rf96.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  private final T missInfo;

  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param w              is the width of the newly constructed board.
   * @param h              is the height of the newly constructed board.
   * @param myShips        is a arraylists of all ships on the board
   * @param placementCheck is used to check whether the placement of a ship is
   *                       valid
   * @param enemyMisses    records where the enemy has missed
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> rc, T mi) {
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
    enemyMisses = new HashSet<Coordinate>();
    missInfo = mi;
  }

  public BattleShipBoard(int w, int h, T mi) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)), mi);
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

  public String tryAddShip(Ship<T> toAdd) {
    String mess = placementChecker.checkPlacement(toAdd, this);
    if (mess == null) {
      myShips.add(toAdd);
      return null;
    }
    return "That placement is invalid: " + mess + ".";
  }

  /*
   * This method takes a Coordinate, and sees which (if any) Ship
   * occupies that coordinate. If one is found, we return whatever
   * displayInfo it has at those coordinates (for now, just 's'). If
   * none is found, we return null.
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (!isSelf) {
      if (enemyMisses.contains(where)) {
        return missInfo;
      }
    }
    return null;
  }

  /**
   * @param c the coordinate been fired at
   * @return if hit, return the ship been hit
   *         if not , return null
   *         This method should search for any ship that occupies coordinate c
   *         If one is found, that Ship is "hit" by the attack and should
   *         record it. Then we return this ship.
   */

  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;

  }

  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }

  /**
   * the player lose if all of his ships sink
   * 
   * @return boolean indicate whether the player loses
   */
  public Boolean isLose() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }
}
