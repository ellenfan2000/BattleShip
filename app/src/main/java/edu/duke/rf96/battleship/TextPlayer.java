package edu.duke.rf96.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
  private final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  private final String name;

  public TextPlayer(String name, Board<Character> theBoard, Reader inputSource, PrintStream out,
      AbstractShipFactory<Character> factory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    // this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = factory;
    this.name = name;
  }

  /*
   * This method build a placement from input steam
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  /*
   * This function read a placement
   * put a ship on board
   * and print the board
   */
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?");
    Ship<Character> s = shipFactory.makeDestroyer(p);
    theBoard.tryAddShip(s);
    out.print(view.displayMyOwnBoard());
  }

  /**
   * this method display the starting (empty) board
   * ,print the instructions message (from the README,
   * but also shown again near the top of this file)
   * , call doOnePlacement to place one ship
   * 
   */

  public void doPlacementPhase() throws IOException {
    out.println("\n--------------------------------------------------------------------------------");
    out.print(view.displayMyOwnBoard());
    out.println("--------------------------------------------------------------------------------\n" +
        "Player " + name + ": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" +
        "2 \"Submarines\" ships that are 1x2\n" +
        "3 \"Destroyers\" that are 1x3\n" +
        "3 \"Battleships\" that are 1x4\n" +
        "2 \"Carriers\" that are 1x6\n" +
        "--------------------------------------------------------------------------------");

    doOnePlacement();
  }

}
