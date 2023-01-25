package edu.duke.rf96.battleship;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to displya
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append('\n');
    return ans.toString();
  }

  /**
   * this prints a blank board, e.g.
   * 0|1|2|3|4|5|6|7|8|9
   * A | | | | | | | | | A
   * B | | | | | | | | | B
   * C | | | | | | | | | C
   * D | | | | | | | | | D
   * 0|1|2|3|4|5|6|7|8|9
   * 
   * @return the string that is the board
   */
  public String displayMyOwnBoard() {
    StringBuilder ans = new StringBuilder("");

    ans.append(this.makeHeader());
    String sep = "|";
    char lineC = 'A';
    // i for line
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      ans.append(lineC);
      ans.append(" ");
      sep = "";
      // j for column
      for (int j = 0; j < toDisplay.getWidth(); j++) {
        ans.append(sep);
        ans.append(" ");
        sep = "|";
      }
      ans.append(" ");
      ans.append(lineC);
      ans.append('\n');
      lineC += 1;
    }
    ans.append(this.makeHeader());
    return ans.toString(); // this is a placeholder for the moment
  }

}
