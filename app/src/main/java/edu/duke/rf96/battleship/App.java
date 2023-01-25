/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.rf96.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class App {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;

  public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = new BufferedReader(inputSource);
    this.out = out;
  }

  /*
    This method build a placement from input steam
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }
  /*
    This function read a placement
    put a ship on board
    and print the board
   */
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Where would you like to put your ship?");
    BasicShip s = new BasicShip(p.getCoordinate());
    theBoard.tryAddShip(s);
    out.println(view.displayMyOwnBoard());
  }

  public static void main(String[] args) throws IOException {
    Board<Character> b = new BattleShipBoard<Character>(10, 20);
    App app = new App(b, new InputStreamReader(System.in), System.out);
    app.doOnePlacement();
  }

}
