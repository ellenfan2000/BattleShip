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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
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
    // assertEquals(prompt + "\n" + "That placement is invalid: it does not have the
    // correct format\n", bytes.toString());

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

  // @Disabled
  // @Test
  // @ResourceLock(value = Resources.SYSTEM_OUT, mode =
  // ResourceAccessMode.READ_WRITE)
  // public void test_do_placement_phase() throws IOException {

  // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
  // PrintStream out = new PrintStream(bytes, true);

  // /*
  // * asks the current class to give us its ClassLoader
  // * (the part of the Java runtime that reads classes from their files and sets
  // * them up in the JVM).
  // * Then we ask the ClassLaoder to find us a resource named "input.txt" and
  // give
  // * us back
  // * an InputStream for it.
  // */
  // InputStream input =
  // getClass().getClassLoader().getResourceAsStream("input2.txt");
  // assertNotNull(input);

  // InputStream expectedStream =
  // getClass().getClassLoader().getResourceAsStream("output2.txt");
  // assertNotNull(expectedStream);

  // Board<Character> b1 = new BattleShipBoard<Character>(10, 20,'X');
  // BufferedReader in = new BufferedReader(new InputStreamReader(input));
  // V1ShipFactory factory = new V1ShipFactory();
  // TextPlayer p1 = new TextPlayer("A", b1, in, out, factory);

  // p1.doPlacementPhase();

  // String expected = new String(expectedStream.readAllBytes()); // read all the
  // data from our expectedStream
  // String actual = bytes.toString();// get the String out of bytes
  // assertEquals(expected, actual);
  // }
  @Test
  public void test_read_coor() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer("A", 10, 10, "B2\nC8\na4\nZ9\nD7\n", bytes);
    TextPlayer player2 = createTextPlayer("B", 10, 10, "", bytes);

    // App app = new App(p1,p2);

    String prompt = "Please enter a a location to hit at:";
    Coordinate[] expected = new Coordinate[4];
    expected[0] = new Coordinate(1, 2);
    expected[1] = new Coordinate(2, 8);
    expected[2] = new Coordinate(0, 4);
    expected[3] = new Coordinate(3, 7);

    for (int i = 0; i < expected.length - 1; i++) {
      Coordinate p = player.readCoordinate(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }
    // Coordinate p = player.readCoordinate(prompt);
    // assertEquals(prompt + "\n" + "That Coordinate is invalid: it does not have
    // the correct format.\n" +prompt+ "\n", bytes.toString());
    assertThrows(IllegalArgumentException.class, () -> player.readCoordinate(prompt));

    assertThrows(EOFException.class, () -> player2.readCoordinate(prompt));
  }

  @Test
  public void test_read_Choice() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer("A", 10, 10, "B\nC8\nF\ns\nm\n", bytes);
    TextPlayer player2 = createTextPlayer("B", 10, 10, "\n", bytes);

    // App app = new App(p1,p2);

     String prompt = "Please choose a move";
    // Character[] expected = new Character[3];
    // expected[0] = 'F';
    // expected[1] = 'S';
    // expected[2] = 'M';
    
    

    // for (int i = 0; i < expected.length; i++) {
    //   Character p = player.readChoice(prompt);
    //   assertEquals(p, expected[i]); // did we get the right Placement back
    //   assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
    //   bytes.reset(); // clear out bytes for next time around
    // }
    // Coordinate p = player.readCoordinate(prompt);
    // assertEquals(prompt + "\n" + "That Coordinate is invalid: it does not have
    // the correct format.\n" +prompt+ "\n", bytes.toString());

    assertThrows(EOFException.class, () -> player2.readChoice(prompt));

  }
}
