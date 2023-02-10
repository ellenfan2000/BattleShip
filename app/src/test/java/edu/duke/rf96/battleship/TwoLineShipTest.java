package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TwoLineShipTest {
  @Test
  public void test_makeShip() {
    Coordinate c1 = new Coordinate(2, 5);
    Coordinate c2 = new Coordinate(3, 4);

    TwoLineShip<Character> s = new TwoLineShip<Character>("BattleShip", c1, 3, 1, c2, 4, 1, 'b', '*',new Placement(c1,'H'));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(2, 7)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 4)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 5)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 6)));
    assertEquals(true, s.occupiesCoordinates(new Coordinate(3, 7)));
    assertEquals("BattleShip", s.getName());
    
  }

  @Test
  public void test_getCenter() {
     Coordinate c1 = new Coordinate(2, 5);
    V2ShipFactory fac = new V2ShipFactory();
    Ship<Character> s1 = fac.makeBattleship(new Placement(c1, 'U'));
    Ship<Character> s2 = fac.makeBattleship(new Placement(c1, 'R'));
    Ship<Character> s3 = fac.makeBattleship(new Placement(c1, 'D'));
    Ship<Character> s4 = fac.makeBattleship(new Placement(c1, 'L'));

     assertEquals(new Coordinate(2,6), s1.getCenter());
     assertEquals(new Coordinate(3,6), s2.getCenter());
     assertEquals(new Coordinate(3,6), s3.getCenter());
     assertEquals(new Coordinate(3,5), s4.getCenter());


     
  }
  @Test
  public void test_relativeCoordinates() {
    Coordinate c1 = new Coordinate(2, 5);
    V2ShipFactory fac = new V2ShipFactory();
    Ship<Character> s1 = fac.makeBattleship(new Placement(c1, 'U'));
    Ship<Character> s2 = fac.makeBattleship(new Placement(c1, 'R'));
    Ship<Character> s3 = fac.makeBattleship(new Placement(c1, 'D'));
    Ship<Character> s4 = fac.makeBattleship(new Placement(c1, 'L'));

    Ship<Character> s5 = fac.makeCarrier(new Placement(c1, 'U'));
    Ship<Character> s6 = fac.makeCarrier(new Placement(c1, 'R'));
    Ship<Character> s7 = fac.makeCarrier(new Placement(c1, 'D'));
    Ship<Character> s8 = fac.makeCarrier(new Placement(c1, 'L'));

    assertEquals(new Coordinate(1,-1), s1.getRelativeCoordinate(new Coordinate(3, 5)));
    assertEquals(new Coordinate(1,-1), s2.getRelativeCoordinate(new Coordinate(2, 5)));
    assertEquals(new Coordinate(1,-1), s3.getRelativeCoordinate(new Coordinate(2, 7)));
    assertEquals(new Coordinate(1,-1), s4.getRelativeCoordinate(new Coordinate(4, 6)));

    assertEquals(new Coordinate(2,1), s5.getRelativeCoordinate(new Coordinate(4, 6)));
    assertEquals(new Coordinate(2,1), s6.getRelativeCoordinate(new Coordinate(3, 7)));
    assertEquals(new Coordinate(2,1), s7.getRelativeCoordinate(new Coordinate(4, 5)));
    assertEquals(new Coordinate(2,1), s8.getRelativeCoordinate(new Coordinate(2, 7)));


    Ship<Character> s9 = fac.makeDestroyer(new Placement(c1, 'H'));
    Ship<Character> s10 = fac.makeDestroyer(new Placement(c1, 'V'));
    assertEquals(new Coordinate(0,1), s9.getRelativeCoordinate(new Coordinate(2, 6)));
    assertEquals(new Coordinate(0,1), s10.getRelativeCoordinate(new Coordinate(3, 5)));

    
  
  }

  @Test
  public void test_definateCoordinates() {
    Coordinate c1 = new Coordinate(2, 5);
    V2ShipFactory fac = new V2ShipFactory();
    Ship<Character> s1 = fac.makeBattleship(new Placement(c1, 'U'));
    Ship<Character> s2 = fac.makeBattleship(new Placement(c1, 'R'));
    Ship<Character> s3 = fac.makeBattleship(new Placement(c1, 'D'));
    Ship<Character> s4 = fac.makeBattleship(new Placement(c1, 'L'));

    Ship<Character> s5 = fac.makeCarrier(new Placement(c1, 'U'));
    Ship<Character> s6 = fac.makeCarrier(new Placement(c1, 'R'));
    Ship<Character> s7 = fac.makeCarrier(new Placement(c1, 'D'));
    Ship<Character> s8 = fac.makeCarrier(new Placement(c1, 'L'));

    assertEquals(new Coordinate(3,5), s1.getDefinCoordinate(new Coordinate(1, -1)));
    assertEquals(new Coordinate(2,5), s2.getDefinCoordinate(new Coordinate(1, -1)));
    assertEquals(new Coordinate(2,7), s3.getDefinCoordinate(new Coordinate(1, -1)));
    assertEquals(new Coordinate(4,6), s4.getDefinCoordinate(new Coordinate(1, -1)));


    assertEquals(new Coordinate(4,6), s5.getDefinCoordinate(new Coordinate(2, 1)));
    assertEquals(new Coordinate(3,7), s6.getDefinCoordinate(new Coordinate(2, 1)));
    assertEquals(new Coordinate(4,5), s7.getDefinCoordinate(new Coordinate(2, 1)));
    assertEquals(new Coordinate(2,7), s8.getDefinCoordinate(new Coordinate(2, 1)));

    Ship<Character> s9 = fac.makeDestroyer(new Placement(c1, 'H'));
    Ship<Character> s10 = fac.makeDestroyer(new Placement(c1, 'V'));
    
    assertEquals(new Coordinate(2,6), s9.getDefinCoordinate(new Coordinate(0, 1)));
    assertEquals(new Coordinate(3,5), s10.getDefinCoordinate(new Coordinate(0, 1)));
    
  }

}
