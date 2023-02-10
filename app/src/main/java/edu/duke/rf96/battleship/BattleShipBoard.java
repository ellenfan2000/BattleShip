package edu.duke.rf96.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  private HashMap<Coordinate,T> enemyHits;
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
    enemyHits = new HashMap<Coordinate,T>();
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
   * displayInfo it has at those coordinates. If
   * none is found, we return null.
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    if(isSelf){
      for (Ship<T> s : myShips) {
        if (s.occupiesCoordinates(where)) {
          return s.getDisplayInfoAt(where, isSelf);
        }
      }
    }
    if (!isSelf) {
      if (enemyHits.containsKey(where)){
        return enemyHits.get(where);
      }
      if (enemyMisses.contains(where)) {
        return missInfo;
      }
    }
    return null;
  }

  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
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
        enemyHits.put(c,s.getDisplayInfoAt(c, false));
        return s;
      }
    }
    enemyMisses.add(c);
    return null;

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

  /**
   * @param where: the coordinate take by the ship
   * @return the ship that takes the coordinate
   *         null is there is no ship
   */

  public Ship<T> whichShipisAt(Coordinate where) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s;
      }
    }
    return null;

  }

  /**
   * @param newship is the same type of ship newly created as
   *                the result of move
   * @param toMove  is the old ship
   * @return return null is success, else return add failure
   */
  public String tryMoveShip(Ship<T> newShip, Ship<T> toMove) {
    myShips.remove(toMove);
    String mess = tryAddShip(newShip);
    if (mess == null) {
      for (Coordinate c : toMove.getCoordinates()) {
        if (toMove.wasHitAt(c)) {
          Coordinate relative = toMove.getRelativeCoordinate(c);
          newShip.recordHitAt(newShip.getDefinCoordinate(relative));
        }
      }
      return null;
    }
    myShips.add(toMove);
    return mess;
  }

  /**
   * @param C the center of sonar
   * @return a hashmap of how many sqares each type of ship takes
   */
  public HashMap<String, Integer> sonarScan(Coordinate C) {
    HashMap<String, Integer> ans = new HashMap<String, Integer>();
    if (C.getRow() < 0 || C.getRow() >= height || C.getColumn() < 0 || C.getColumn() >= width) {
      throw new IllegalArgumentException("Invalid Coordinate");
    }
    int top = C.getRow() - 3;
    for (int line = 0; line < 7; line++) {
      int row = top + line;
      if (row < 0) {
        continue;
      } else if (row >= height) {
        break;
      } else {
        for (int col = C.getColumn() + Math.abs(3 - line) - 3; col <= C.getColumn() - Math.abs(3 - line) + 3; col++) {
          if (col < 0) {
            continue;
          }
          if (col >= width) {
            break;
          }
          Ship<T> s = whichShipisAt(new Coordinate(row, col));
          if (s != null) {
            if (ans.get(s.getName()) == null) {
              ans.put(s.getName(), 1);
            } else {
              ans.replace(s.getName(), ans.get(s.getName()) + 1);
            }
          }
        }
      }
    }
    return ans;
  }
}
