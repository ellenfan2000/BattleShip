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

  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  
  /**
     recursively cheack all reules
     @return false if one rule is not satisfied
     @return true if pass all rules 
   */
  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stop the placement is not legal
    String error_message = checkMyRule(theShip, theBoard);
    if (error_message!= null) {
      return error_message ;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return null;
  }
}
