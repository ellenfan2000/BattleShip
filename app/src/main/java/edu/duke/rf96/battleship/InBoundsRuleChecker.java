package edu.duke.rf96.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  /**
   * The Ship can't go out of bounds (row or column less than 0,
   * equal ot greater than width/height)
   */

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * check whether this rule is satisfied
   * 
   * @return true if satisfied
   * @return false if not
   */
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int R = theBoard.getHeight();//boards row
    int W = theBoard.getWidth();//boards column
    for (Coordinate c : theShip.getCoordinates()) {
      if (c.getRow() >= R || c.getRow() < 0 || c.getColumn() >= W || c.getColumn() < 0) {
        return false;
      }
    }
    return true;
  }

}
