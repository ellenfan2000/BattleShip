package edu.duke.rf96.battleship;

import java.util.HashMap;

public interface Board<T> {

  /**
   * @return the board's width: columns
   */
  public int getWidth();

  /**
   * @return the board's height: rows
   */
  public int getHeight();

  /**
   * @param toAdd is the ship we want to add to the board
   * @return true if success, false if not
   */
  public String tryAddShip(Ship<T> toAdd);

  /**
   * This method takes a Coordinate, and sees which (if any) Ship
   * occupies that coordinate. If one is found, we return whatever
   * displayInfo it has at those coordinates (for now, just 's'). If
   * none is found, we return null.
   */
  public T whatIsAtForSelf(Coordinate where);


   /**
     This method should search for any ship that occupies coordinate c
If one is found, that Ship is "hit" by the attack and should
record it.  Then we return this ship.

   */
  public Ship<T> fireAt(Coordinate c);


  /**
   * This method takes a Coordinate, and sees which (if any) Ship
   * occupies that coordinate. If one is found, we return whatever
   * displayInfo it has at those coordinates (for now, just 's'). If
   * none is found, we return null.
   */
  public T whatIsAtForEnemy(Coordinate where);

  /**
   * @param newship is the same type of ship newly created as
   *                the result of move
   * @param toMove  is the old ship
   * @return return null is success, else return add failure
   */
  public String tryMoveShip(Ship<T> newShip, Ship<T> toMove);

  public Boolean isLose();

  public HashMap<String, Integer> sonarScan(Coordinate C);

  public Ship<T> whichShipisAt(Coordinate where);
}
