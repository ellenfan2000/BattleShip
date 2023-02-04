package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
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
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
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
    Board<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');

    Ship<Character> s1 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');
    Ship<Character> s2 = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*');
    Ship<Character> s3 = new RectangleShip<Character>(new Coordinate(2, 1), 's', '*');
    Ship<Character> s4 = new RectangleShip<Character>(new Coordinate(4, 2), 's', '*');

    b1.tryAddShip(s1);
    b1.tryAddShip(s2);
    b1.tryAddShip(s3);
    b1.tryAddShip(s4);

    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader + expectedBody + expectedHeader, view.displayMyOwnBoard());

  }

  @Test
  public void test_display_enemy() {
    String myView = "  0|1|2|3\n" +
        "A  | | |d A\n" +
        "B s|s| |d B\n" +
        "C  | | |d C\n" +
        "  0|1|2|3\n";

    String enemyView = "  0|1|2|3\n" +
        "A  | | |d A\n" +
        "B s| | |  B\n" +
        "C  |X|X|  C\n" +
        "  0|1|2|3\n";
    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b);

    V1ShipFactory factory = new V1ShipFactory();

    Ship<Character> s1 = factory.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));

    Ship<Character> s2 = factory.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));

    assertNull(b.tryAddShip(s1));
    assertNull(b.tryAddShip(s2));

    assertEquals(myView, view.displayMyOwnBoard());

    b.fireAt(new Coordinate(1, 0));
    b.fireAt(new Coordinate(2, 1));// miss
    b.fireAt(new Coordinate(2, 2));// miss
    b.fireAt(new Coordinate(0, 3));

    assertEquals(enemyView, view.displayEnemyBoard());
  }

  @Test
  public void test_MyBoardWithEnemy() {
    String myhead = "my";
    String enemyhead = "enemy";

    String expected = "     my                       enemy\n" +
        "  0|1|2|3                    0|1|2|3\n" +
        "A  | | |* A                A  | | |d A\n" +
        "B s|*| |d B                B  |s| |  B\n" +
        "C  | | |d C                C  |X|X|  C\n" +
        "  0|1|2|3                    0|1|2|3\n";

    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view1 = new BoardTextView(b1);

    Board<Character> b2 = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view2 = new BoardTextView(b2);

    V1ShipFactory factory = new V1ShipFactory();

    Ship<Character> s1 = factory.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));

    Ship<Character> s2 = factory.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));

    assertNull(b1.tryAddShip(s1));
    assertNull(b1.tryAddShip(s2));

    assertNull(b2.tryAddShip(s1));
    assertNull(b2.tryAddShip(s2));

    b1.fireAt(new Coordinate(1, 1));
    b1.fireAt(new Coordinate(2, 1));// miss
    b1.fireAt(new Coordinate(2, 2));// miss
    b1.fireAt(new Coordinate(0, 3));

    b2.fireAt(new Coordinate(1, 1));
    b2.fireAt(new Coordinate(2, 1));// miss
    b2.fireAt(new Coordinate(2, 2));// miss
    b2.fireAt(new Coordinate(0, 3));

    assertEquals(expected, view1.displayMyBoardWithEnemyNextToIt(view2, myhead, enemyhead));
  }
}
