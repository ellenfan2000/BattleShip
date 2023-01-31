package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer(name, board, input, output, shipFactory);
  }

  @Test
  public void test_read_placement() throws IOException {
    // StringReader sr = new StringReader("B2V\nC8H\na4v\n");
    // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // PrintStream ps = new PrintStream(bytes, true);
    // Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    // // Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
    // V1ShipFactory factory = new V1ShipFactory();
    // TextPlayer player = new TextPlayer("A", b1, sr, ps, factory);

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer("A", 10, 20, "B2V\nC8H\na4v\nb4a\nh2H\n", bytes);
    TextPlayer player2 = createTextPlayer("B", 10, 20, "", bytes);

    // App app = new App(p1,p2);

    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length - 1; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
    // Placement p = player.readPlacement(prompt);
    // assertEquals(prompt + "\n" + "That placement is invalid: it does not have the correct format\n", bytes.toString());

    assertThrows(EOFException.class, () -> player2.readPlacement(prompt));
  }

  @Test
  public void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer("A", 3, 5, "B2V\nC8H\na4v\n", bytes);
    // TextPlayer player2= createTextPlayer("A", 3, 5, "B4h\nA9V\na4v\n", bytes);

    // App app = new App(player1, player2);

    String prompt = "Player A where do you want to place a Destroyer?";

    String expected = prompt + "\n" +
        "  0|1|2\n" +
        "A  | |  A\n" +
        "B  | |d B\n" +
        "C  | |d C\n" +
        "D  | |d D\n" +
        "E  | |  E\n" +
        "  0|1|2\n";

    V1ShipFactory shipFactory = new V1ShipFactory();

    // app.doPlacementPhase();
    player1.doOnePlacement("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    assertEquals(expected, bytes.toString());
  }

  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  public void test_do_placement_phase() throws IOException {
    // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    // TextPlayer player1 = createTextPlayer("A", 10, 20,
    // "A2H\nT7H\nC7V\nP4H\nI3V\nM5H\nD1H\nN0V\nG2H\nM2V", bytes);
    // player1.doPlacementPhase();
    // String message =
    // "\n--------------------------------------------------------------------------------\n"
    // +
    // " 0|1|2|3|4|5|6|7|8|9\n" +
    // "A | | | | | | | | | A\n" +
    // "B | | | | | | | | | B\n" +
    // "C | | | | | | | | | C\n" +
    // "D | | | | | | | | | D\n" +
    // "E | | | | | | | | | E\n" +
    // "F | | | | | | | | | F\n" +
    // "G | | | | | | | | | G\n" +
    // "H | | | | | | | | | H\n" +
    // "I | | | | | | | | | I\n" +
    // "J | | | | | | | | | J\n" +
    // "K | | | | | | | | | K\n" +
    // "L | | | | | | | | | L\n" +
    // "M | | | | | | | | | M\n" +
    // "N | | | | | | | | | N\n" +
    // "O | | | | | | | | | O\n" +
    // "P | | | | | | | | | P\n" +
    // "Q | | | | | | | | | Q\n" +
    // "R | | | | | | | | | R\n" +
    // "S | | | | | | | | | S\n" +
    // "T | | | | | | | | | T\n" +
    // " 0|1|2|3|4|5|6|7|8|9\n" +
    // "--------------------------------------------------------------------------------\n"
    // +
    // "Player A: you are going to place the following ships (which are all\n" +
    // "rectangular). For each ship, type the coordinate of the upper left\n" +
    // "side of the ship, followed by either H (for horizontal) or V (for\n" +
    // "vertical). For example M4H would place a ship horizontally starting\n" +
    // "at M4 and going to the right. You have\n\n" +
    // "2 \"Submarines\" ships that are 1x2\n" +
    // "3 \"Destroyers\" that are 1x3\n" +
    // "3 \"Battleships\" that are 1x4\n" +
    // "2 \"Carriers\" that are 1x6\n" +
    // "--------------------------------------------------------------------------------\n";

    // String prompt = "Player A where do you want to place a Destroyer?";

    // String expected = message + prompt + "\n" +
    // " 0|1|2\n" +
    // "A | | A\n" +
    // "B | |d B\n" +
    // "C | |d C\n" +
    // "D | |d D\n" +
    // "E | | E\n" +
    // " 0|1|2\n";
    // assertEquals(expected, bytes.toString());

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);

    /*
     * asks the current class to give us its ClassLoader
     * (the part of the Java runtime that reads classes from their files and sets
     * them up in the JVM).
     * Then we ask the ClassLaoder to find us a resource named "input.txt" and give
     * us back
     * an InputStream for it.
     */
    InputStream input = getClass().getClassLoader().getResourceAsStream("input2.txt");
    assertNotNull(input);

    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output2.txt");
    assertNotNull(expectedStream);

    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    BufferedReader in = new BufferedReader(new InputStreamReader(input));
    V1ShipFactory factory = new V1ShipFactory();
    TextPlayer p1 = new TextPlayer("A", b1, in, out, factory);

    p1.doPlacementPhase();

    String expected = new String(expectedStream.readAllBytes()); // read all the data from our expectedStream
    String actual = bytes.toString();// get the String out of bytes
    assertEquals(expected, actual);
  }

}
