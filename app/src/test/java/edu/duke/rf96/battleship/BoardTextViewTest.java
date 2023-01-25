package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = "  0|1\n" +
        "A  |  A\n" +
        "B  |  B\n" +
        "  0|1\n";
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
    // you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
        "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" +
        "B  | |  B\n" +
        "C  | |  C\n" +
        "D  | |  D\n" +
        "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_filled_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A s| |  A\n" +
        "B  |s|  B\n" +
        "C  |s|  C\n" +
        "D  | |  D\n" +
        "E  | |s E\n";
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5);

    Ship<Character> s1 = new BasicShip(new Coordinate(0, 0));
    Ship<Character> s2 = new BasicShip(new Coordinate(1, 1));
    Ship<Character> s3 = new BasicShip(new Coordinate(2, 1));
    Ship<Character> s4 = new BasicShip(new Coordinate(4, 2));

    b1.tryAddShip(s1);
    b1.tryAddShip(s2);
    b1.tryAddShip(s3);
    b1.tryAddShip(s4);

    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader + expectedBody + expectedHeader, view.displayMyOwnBoard());

  }

}
