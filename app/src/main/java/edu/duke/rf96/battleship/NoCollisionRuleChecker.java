package edu.duke.rf96.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  /**
   * The newly toAdd ship cannnot collide with ships on board
   * 
   * @param next: next chained rule
   */

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * check whether this rule is satisfied
   * 
   * @return true if satisfied
   * @return false if not
   */

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      if (theBoard.whatIsAt(c) != null) {
        return "the ship overlaps another ship";
      }
    }
    return null;
  }

}
