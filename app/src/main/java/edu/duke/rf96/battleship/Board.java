package edu.duke.rf96.battleship;

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
  public boolean tryAddShip(Ship<T> toAdd);

  /**
   * This method takes a Coordinate, and sees which (if any) Ship
   * occupies that coordinate. If one is found, we return whatever
   * displayInfo it has at those coordinates (for now, just 's'). If
   * none is found, we return null.
   */
  public T whatIsAt(Coordinate where);
}
