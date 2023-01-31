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
  private final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  private final String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

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
      
                      
  }
  /**
     this method fill the shipCreationMaps with each type of ships's
     corresponding make ship method.
   */

  protected void setupShipCreationMap(){
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
     this method add all needed ships to te shipsToPlace
   */
  protected void setupShipCreationList(){
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
    if(s==null){
      throw new EOFException("Input stream is null");
    }

    while (true){
      try{
        Placement p = new Placement(s);
        return p;
      }catch(IllegalArgumentException ex){
        out.println("That placement is invalid: it does not have the correct format.");
        out.println(prompt);
        s = inputReader.readLine();
      }
    }
    // System.out.println(s);
  }

  /*
   * This function read a placement
   * put a ship on board
   * and print the board
   */

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    while(true){
      Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
      Ship<Character> s = createFn.apply(p);
      String error_message = theBoard.tryAddShip(s);
      if(error_message!=null){
        out.println(error_message);
      }
      else{
        break;
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
    for (String s:shipsToPlace){
      doOnePlacement(s, shipCreationFns.get(s));
    }
  }

}
