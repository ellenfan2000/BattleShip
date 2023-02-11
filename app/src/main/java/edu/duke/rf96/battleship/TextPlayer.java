package edu.duke.rf96.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {

  /**
   * This class represents a human text player that can place ships and make
   * actions
   * 
   * @param theBoard        current player's board
   * @param view            curent board's view
   * @param shipCreationFns a map of each ships name to their corresponding
   *                        creation function
   * @param shipsToPlace    a list of all ships' name that need to place on the
   *                        board
   * @param numSonar        the number of opportunities to place a sonar
   * @param numMove         the number of opportunities to move a ship
   */
  protected final Board<Character> theBoard;
  protected final BoardTextView view;
  protected final BufferedReader inputReader;
  protected final PrintStream out;
  protected final AbstractShipFactory<Character> shipFactory;
  protected final String name;
  protected final ArrayList<String> shipsToPlace;
  protected final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  protected int numSonar;
  protected int numMove;

  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> factory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    // this.inputReader = new BufferedReader(inputSource);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    setupShipCreationList();
    numMove = 3;
    numSonar = 3;

  }

  /**
   * this method fill the shipCreationMaps with each type of ships's
   * corresponding make ship method.
   */

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
   * this method add all needed ships to te shipsToPlace
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));

  }

  /*
   * This method build a placement from input steam
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("Input stream is null");
    }
    Placement p = new Placement(s);
    return p;
  }

  /*
   * This function read a placement
   * put a ship on board
   * and print the board
   */

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    while (true) {
      try {
        Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
        Ship<Character> s = createFn.apply(p);
        String error_message = theBoard.tryAddShip(s);
        if (error_message != null) {
          out.println(error_message);
          // throw new IllegalArgumentException(error_message);
        } else {
          break;
        }
      } catch (IllegalArgumentException ex) {
        out.println("That placement is invalid: it does not have the correct format.");
      }
    }
    out.print(view.displayMyOwnBoard());
  }

  /**
   * this method display the starting (empty) board
   * ,print the instructions message (from the README,
   * but also shown again near the top of this file)
   * , call doOnePlacement to place every ship
   * 
   */
  // public void doPlacementPhase() throws IOException {
  //   out.println("\n--------------------------------------------------------------------------------");
  //   out.print(view.displayMyOwnBoard());
  //   out.println("--------------------------------------------------------------------------------\n"
  //       +
  //       "Player " + name + ": you are going to place the following ships (which areall\n" +
  //       "rectangular). For each ship, type the coordinate of the upper left\n" +
  //       "side of the ship, followed by either H (for horizontal) or V (for\n" +
  //       "vertical). For example M4H would place a ship horizontally starting\n" +
  //       "at M4 and going to the right. You have\n\n" +
  //       "2 \"Submarines\" ships that are 1x2\n" +
  //       "3 \"Destroyers\" that are 1x3\n" +
  //       "3 \"Battleships\" that are 1x4\n" +
  //       "2 \"Carriers\" that are 1x6\n" +
  //       "--------------------------------------------------------------------------------");
  //   for (String s : shipsToPlace) {
  //     doOnePlacement(s, shipCreationFns.get(s));
  //   }
  // }

  /**
   * this method display the starting (empty) board
   * ,print the instructions message (from the README,
   * but also shown again near the top of this file)
   * , call doOnePlacement to place every ship
   * 
   */
  public void doPlacementPhaseV2() throws IOException {
    out.println("\n--------------------------------------------------------------------------------");
    out.print(view.displayMyOwnBoard());
    out.println("--------------------------------------------------------------------------------\n" +
        "Player " + name + ": you are going to place the following ships. For each ship,\n" +
        "type the coordinate of the upper left side of the ship, followed by orientation:\n" +
        "H (for horizontal), V (fo rvertical), U (for up), R (for right), D (for down), L (for left)\n" +
        "For example M4H would place a ship horizontally starting at M4 and going to the right.\n" +
        "You have\n\n" +
        "2 \"Submarines\" ships that are 1x2 (H or V)\n" +
        "3 \"Destroyers\" that are 1x3 (H or V)\n" +
        "3 \"Battleships\" that are: \n\n" +
        " *b              B         Bbb        *b\n" +
        " bbb    OR       bb   OR    b     OR  bb \n" +
        "                 b                     b \n\n " +
        " Up             Right      Down      Left\n\n" +
        "2 \"Carriers\" that are:\n\n" +
        " C                       C             \n" +
        " c          *cccc        cc       * ccc\n" +
        " cc   OR    ccc      OR  cc   OR  cccc \n" +
        " cc                       c         \n" +
        "  c                       c\n\n" +
        " Up         Right       Down      Left\n" +

        "--------------------------------------------------------------------------------");
    for (String s : shipsToPlace) {
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

  /**
   * This method read a coordinate from the player
   * 
   * @return coordinate read
   * @throws if the coordinate is not valid, throw IllegalArgumentException
   */

  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("Input stream is null");
    }
    Coordinate c = new Coordinate(s);
    if (c.getRow() >= theBoard.getHeight() || c.getColumn() >= theBoard.getWidth()) {
      throw new IllegalArgumentException("The coordinate is out of bound");
    }
    return c;
  }

  /**
   * This method read a choice (F, S, M) from the player
   * 
   * @return action coordinate read
   */
  public Character readChoice(String prompt) throws IOException {
    // out.println("---------------------------------------------------------------------------\n");
    out.println("Possible actions for Player " + name + ":\n");
    out.println("F Fire at a square");
    out.println("M Move a ship to another square (" + numMove + " remaining)");
    out.println("S Sonar scan (" + numSonar + " remaining)\n");
    out.println(prompt);
    out.println("---------------------------------------------------------------------------");

    while (true) {
      try {
        String s = inputReader.readLine();

        if (s == null) {
          throw new EOFException("Input stream is null");
        }
        s = s.toUpperCase();
        if (s.length() != 1) {
          throw new IllegalArgumentException("Invalid input");
        }
        char choice = s.charAt(0);
        if (choice != 'F' && choice != 'M' && choice != 'S') {
          throw new IllegalArgumentException("Invalid input");
        }
        if ((choice == 'M' && numMove == 0) || (choice == 'S' && numSonar == 0)) {
          throw new IllegalArgumentException("Option use up");
        }
        return choice;
      } catch (IllegalArgumentException ex) {
        out.println("That choice is invalid, please input another choice");
      }
    }

  }

  // public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView,
  //     String myHeader, String enemyHeader)
  //     throws IOException {
  //   out.println("---------------------------------------------------------------------------");
  //   out.println("Player " + name + "'s turn:");
  //   out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader,
  //       enemyHeader));
  //   out.println("---------------------------------------------------------------------------");
  //   String prompt = "Player " + name + " where do you want to fire at?";

  //   while (true) {
  //     try {
  //       Coordinate c = readCoordinate(prompt);
  //       Ship<Character> s = enemyBoard.fireAt(c);
  //       out.println("---------------------------------------------------------------------------");
  //       if (s != null) {
  //         out.println("You hit a " + s.getName() + "!");
  //       } else {
  //         out.println("You missed!");
  //       }
  //       break;

  //     } catch (IllegalArgumentException ex) {
  //       out.println("That Coordinate is invalid: it does not have the correct format.");
  //     }
  //   }
  // }

  /**
   * @return name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * one possible action: fire at a square
   */
  public void fireaSquare(Board<Character> enemyBoard) throws IOException {
    String prompt = "Player " + name + " where do you want to fire at?";
    Coordinate c = readCoordinate(prompt);
    Ship<Character> s = enemyBoard.fireAt(c);
    out.println("---------------------------------------------------------------------------");
    if (s != null) {
      out.println("You hit a " + s.getName() + "!");
    } else {
      out.println("You missed!");
    }

  }

  /**
   * one possible action: place a sonar
   */
  public void scanSquares(Board<Character> enemyBoard) throws IOException {
    String prompt = "Player " + name + " where do you want to Scan?";
    Coordinate c = readCoordinate(prompt);
    HashMap<String, Integer> scans = enemyBoard.sonarScan(c);
    out.println("---------------------------------------------------------------------------");
    int subma = (scans.get("Submarine") == null) ? 0 : scans.get("Submarine");
    int destroy = (scans.get("Destroyer") == null) ? 0 : scans.get("Destroyer");
    int battle = (scans.get("Battleship") == null) ? 0 : scans.get("Battleship");
    int carr = (scans.get("Carrier") == null) ? 0 : scans.get("Carrier");
    out.println("Submarines occupy " + subma + " squares\n" +
        "Destroyers occupy " + destroy + " squares\n" +
        "Battleships occupy " + battle + " squares\n" +
        "Carriers occupy " + carr + " square");

    // out.println("---------------------------------------------------------------------------");
    numSonar -= 1;

  }

  /**
   * one possible action: move a ship
   * 
   * @throws if movement does not success, thwow IllegalArgumentException
   */
  public void moveAShip() throws IOException {
    String prompt1 = "Player " + name
        + " which ship do you want to move? (Please enter a coordinate in the ship you want to move)";
    String prompt2 = "Player " + name + " Where do do want to move this ship to?";
    Coordinate c = readCoordinate(prompt1);
    Ship<Character> tomove = theBoard.whichShipisAt(c);
    if (tomove == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    Placement p = readPlacement(prompt2);
    String type = tomove.getType();
    Ship<Character> newship = shipCreationFns.get(type).apply(p);
    String mess = theBoard.tryMoveShip(newship, tomove);
    if (mess != null) {
      throw new IllegalArgumentException(mess);
    }

    out.println("Success!");
    numMove -= 1;

  }

  public void playOneTurnV2(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader)
      throws IOException {
    out.println("---------------------------------------------------------------------------");
    out.println("Player " + name + "'s turn:");
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
    out.println("---------------------------------------------------------------------------");
    while (true) {
      try {
        Character c = readChoice("Player " + name + ", what would you like to do?");

        if (c == 'F') {
          fireaSquare(enemyBoard);
        } else if (c == 'S') {
          scanSquares(enemyBoard);
        } else {
          moveAShip();
        }
        break;
      } catch (IllegalArgumentException ex) {
        out.println("The Choice failed, please try again");
      }
    }

  }

}
