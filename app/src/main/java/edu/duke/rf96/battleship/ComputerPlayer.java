package edu.duke.rf96.battleship;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.BufferedReader;
import java.util.function.Function;

public class ComputerPlayer extends TextPlayer {
  private Coordinate lastfire;

  public ComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
      AbstractShipFactory<Character> factory) {
    super(name, theBoard, inputSource, out, factory);
    lastfire = new Coordinate(0, -1);
  }

  @Override
  public void doPlacementPhaseV2() {
    theBoard.tryAddShip(shipFactory.makeSubmarine(new Placement("D0V")));
    theBoard.tryAddShip(shipFactory.makeSubmarine(new Placement("B6H")));
    theBoard.tryAddShip(shipFactory.makeDestroyer(new Placement("A8V")));
    theBoard.tryAddShip(shipFactory.makeDestroyer(new Placement("A0V")));
    theBoard.tryAddShip(shipFactory.makeDestroyer(new Placement("E1H")));
    theBoard.tryAddShip(shipFactory.makeBattleship(new Placement("A1D")));
    theBoard.tryAddShip(shipFactory.makeBattleship(new Placement("B1R")));
    theBoard.tryAddShip(shipFactory.makeBattleship(new Placement("C6U")));
    theBoard.tryAddShip(shipFactory.makeCarrier(new Placement("A3R")));
    theBoard.tryAddShip(shipFactory.makeCarrier(new Placement("C2L")));

  }

  public void fire(Board<Character> enemyBoard) {
    int row = lastfire.getRow();
    int col = lastfire.getColumn();
    if (col == enemyBoard.getWidth() - 1) {
      col = 0;
      row += 1;
    } else {
      col++;
    }
    if (row == enemyBoard.getHeight() - 1 && col == enemyBoard.getWidth() - 1) {
      row = 0;
      col = 0;
    }

    Coordinate c = new Coordinate(row, col);
    Ship<Character> s = enemyBoard.fireAt(c);
    out.println("---------------------------------------------------------------------------");
    if (s != null) {
      out.println("Player " + name + " hit your " + s.getName() + " at " + c.toString());
    } else {
      out.println("Player " + name + " missed "+ " at " + c.toString());
    }
    lastfire = c;
  }

  @Override
  public void playOneTurnV2(Board<Character> enemyBoard, BoardTextView enemyView, String myHeader, String enemyHeader) {
    fire(enemyBoard);

  }

}
