package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    // RectangleShip s = new RectangleShip();
    Coordinate c = new Coordinate(2, 5);
    HashSet<Coordinate> ans = RectangleShip.makeCoords(c, 3, 2);
    assertEquals(6, ans.size());
    assertEquals(true, ans.contains(new Coordinate(2, 5)));
    assertEquals(true, ans.contains(new Coordinate(3, 5)));
    assertEquals(true, ans.contains(new Coordinate(2, 6)));
    assertEquals(true, ans.contains(new Coordinate(3, 6)));
    assertEquals(true, ans.contains(new Coordinate(2, 7)));
    assertEquals(true, ans.contains(new Coordinate(3, 7)));
  }

  @Test
  public void test_Constructor() {

    Coordinate c = new Coordinate(2, 5);
    RectangleShip<Character> s = new RectangleShip<Character>("submarine",c, 3, 2, 's', '*');
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 7)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 7)));
    assertEquals("submarine", s.getName());
  }

  @Test
  public void test_RecordHit() {
    Coordinate c = new Coordinate(2, 5);
    Coordinate c2 = new Coordinate(2, 7);
    Coordinate c3 = new Coordinate(3, 6);
    RectangleShip<Character> s = new RectangleShip<Character>("submarine",c, 3, 2, 's', '*');
    assertThrows(IllegalArgumentException.class,() -> s.recordHitAt(new Coordinate(0, 0)));
    s.recordHitAt(c);
    s.recordHitAt(c2);
    s.recordHitAt(c3);

    assertEquals(true, s.wasHitAt(c));
    assertEquals(true, s.wasHitAt(c2));
    assertEquals(true, s.wasHitAt(c3));

  }

  @Test
  public void test_sink(){
    Coordinate c = new Coordinate(2, 5); 
    RectangleShip<Character> s = new RectangleShip<Character>("submarine", c, 3, 2, 's', '*');
    for(int row = 2; row < 3; row ++ ){
      for(int col = 5; col < 8; col++){
        s.recordHitAt(new Coordinate(row, col));
      }
      assertEquals(false, s.isSunk());
    }
     for(int col = 5; col < 8; col++){
        s.recordHitAt(new Coordinate(3, col));
      }
    assertEquals(true, s.isSunk());
    
  }

  @Test
  public void test_getdisplayInfo(){
    Coordinate c = new Coordinate(2, 5); 
    RectangleShip<Character> s = new RectangleShip<Character>("submarine",c, 3, 2, 's', '*');
    for(int row = 2; row < 3; row ++ ){
      for(int col = 5; col < 8; col++){
        s.recordHitAt(new Coordinate(row, col));
      }
    }

    for(int row = 2; row < 3; row ++ ){
      for(int col = 5; col < 8; col++){
        assertEquals('*', s.getDisplayInfoAt(new Coordinate(row, col)));
      }
    }
     for(int col = 5; col < 8; col++){
        assertEquals('s', s.getDisplayInfoAt(new Coordinate(3, col)));
      }
  }

}
