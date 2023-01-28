package edu.duke.rf96.battleship;

public abstract class PlacementRuleChecker<T> {
  /**
   * Chain of Responsibility pattern
   * If the current cannot handle the request (or
   * cannot determine the outcome),
   * go to the next item in the chain.
   * 
   * @param next rule
   */
  private final PlacementRuleChecker<T> next;

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

  
  /**
     recursively cheack all reules
     @return false if one rule is not satisfied
     @return true if pass all rules 
   */
  public boolean checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stop the placement is not legal
    if (!checkMyRule(theShip, theBoard)) {
      return false;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return true;
  }
}
