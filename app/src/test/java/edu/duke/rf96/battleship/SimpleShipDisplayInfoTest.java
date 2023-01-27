package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Character> info = new SimpleShipDisplayInfo<Character>('a', 'b');

    assertEquals('a', info.getInfo(new Coordinate(0, 0), false));
    assertEquals('b', info.getInfo(new Coordinate(0, 0), true));

  }

}
