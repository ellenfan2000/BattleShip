package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TwoLineShipTest {
  @Test
  public void test_makeShip() {
    Coordinate c1 = new Coordinate(2, 5);
    Coordinate c2 = new Coordinate(3, 4);

    TwoLineShip<Character> s = new TwoLineShip<Character>("BattleShip", c1, 3, 1, c2, 4, 1, 'b', '*');
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 7)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 4)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 7)));
    assertEquals("BattleShip", s.getName());
    
  }

}
