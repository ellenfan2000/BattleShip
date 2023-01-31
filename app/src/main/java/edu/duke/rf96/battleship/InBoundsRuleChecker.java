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
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int R = theBoard.getHeight();//boards row
    int W = theBoard.getWidth();//boards column
    for (Coordinate c : theShip.getCoordinates()) {
      if (c.getRow() >= R ) {
        return "the ship goes off the bottom of the board";
      }
      else if( c.getRow() < 0){
        return "the ship goes off the top of the board";
      }
      else if( c.getColumn() >= W){
        return "the ship goes off the right of the board";
      }
      else if(c.getColumn() < 0){
        return "the ship goes off the left of the board";
      }
    }
    return null;
  }

}
