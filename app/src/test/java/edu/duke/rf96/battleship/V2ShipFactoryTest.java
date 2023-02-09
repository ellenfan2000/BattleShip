package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLocs[0], true));
    for (int i = 0; i < expectedLocs.length; i++) {
      assertTrue(testShip.occupiesCoordinates(expectedLocs[i]));
    }
  }

  @Test
  public void test_MakeShips_valid() {
    V2ShipFactory fac = new V2ShipFactory();

    Coordinate c = new Coordinate(0, 0);
    Ship<Character> subma = fac.makeSubmarine(new Placement(c, 'V'));
    checkShip(subma, "Submarine", 's', c, new Coordinate(1, 0));
   
    Ship<Character> destroy = fac.makeDestroyer(new Placement(c, 'H'));
    checkShip(destroy, "Destroyer", 'd', c, new Coordinate(0, 1), new Coordinate(0, 2));

    
    Ship<Character> battle1 = fac.makeBattleship(new Placement(c, 'U'));
     // for(Coordinate c1:battle1.getCoordinates()){
    //   System.out.println(c1.toString());
    // }
    checkShip(battle1, "Battleship", 'b', new Coordinate(0,1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2));
    Ship<Character> battle2 = fac.makeBattleship(new Placement(c, 'R'));
   
    checkShip(battle2, "Battleship", 'b', new Coordinate(0,0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(1, 1));
    
    Ship<Character> battle3 = fac.makeBattleship(new Placement(c, 'D'));
    checkShip(battle3, "Battleship", 'b', new Coordinate(0,0), new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(1, 1));
    Ship<Character> battle4 = fac.makeBattleship(new Placement(c, 'L'));
    checkShip(battle4, "Battleship", 'b', new Coordinate(1,0), new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1));

    
    Ship<Character> carrier1 = fac.makeCarrier(new Placement(c, 'U'));
    // for(Coordinate c1:carrier1.getCoordinates()){
    //   System.out.println(c1.toString());
    // }
    checkShip(carrier1, "Carrier", 'c', new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0),
              new Coordinate(0, 0), new Coordinate(2, 1),new Coordinate(3, 1), new Coordinate(4, 1));

    Ship<Character> carrier2 = fac.makeCarrier(new Placement(c, 'L'));
     checkShip(carrier2, "Carrier", 'c',new Coordinate(0, 4), new Coordinate(0, 2), new Coordinate(0, 3),
               new Coordinate(1, 0),new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3));
     
    Ship<Character> carrier3 = fac.makeCarrier(new Placement(c, 'D'));
     checkShip(carrier3, "Carrier", 'c', new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(0, 0),
              new Coordinate(1, 1), new Coordinate(2, 1),new Coordinate(3, 1), new Coordinate(4, 1));
    Ship<Character> carrier4 = fac.makeCarrier(new Placement(c, 'R'));
    checkShip(carrier4, "Carrier", 'c',new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2),
               new Coordinate(0, 1),new Coordinate(0, 2), new Coordinate(0, 3), new Coordinate(0, 4));

  }
   @Test
   public void test_MakeShips_invalid() {
    V2ShipFactory fac = new V2ShipFactory();

    Coordinate c = new Coordinate(0, 0);

    assertThrows(IllegalArgumentException.class, ()->fac.makeSubmarine(new Placement(c, 'U')));
    assertThrows(IllegalArgumentException.class, ()->fac.makeDestroyer(new Placement(c, 'L')));
    assertThrows(IllegalArgumentException.class, ()->fac.makeBattleship(new Placement(c, 'V')));
    assertThrows(IllegalArgumentException.class, ()->fac.makeCarrier(new Placement(c, 'H')));
    
   }
}
