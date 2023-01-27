package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    //RectangleShip s = new RectangleShip();
    Coordinate c = new Coordinate(2,5);
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
  public void test_Constructor(){

    Coordinate c = new Coordinate(2,5);
    RectangleShip<Character> s = new RectangleShip<Character>(c, 3, 2, 's', '*');
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 5)));
     assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 7)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 7))); 

  }

}
