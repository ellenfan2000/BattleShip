package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLocs[0]));
    for (int i = 0; i < expectedLocs.length; i++) {
      assertTrue(testShip.occupiesCoordinates(expectedLocs[i]));
    }
  }

  @Test
  public void test_variousShip() {
    V1ShipFactory fac = new V1ShipFactory();

    Coordinate c = new Coordinate(0, 0);

    Ship<Character> battle = fac.makeBattleship(new Placement(c, 'V'));
    Ship<Character> carrier = fac.makeCarrier(new Placement(c, 'H'));
    Ship<Character> destroy = fac.makeDestroyer(new Placement(c, 'H'));
    Ship<Character> subma = fac.makeSubmarine(new Placement(c, 'V'));

    checkShip(battle, "Battleship", 'b', c, new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0));
    checkShip(carrier, "Carrier", 'c', c, new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(0, 3),
        new Coordinate(0, 4), new Coordinate(0, 5));
    checkShip(destroy, "Destroyer", 'd', c, new Coordinate(0, 1), new Coordinate(0, 2));
    checkShip(subma, "Submarine", 's', c, new Coordinate(1, 0));

  }

}
