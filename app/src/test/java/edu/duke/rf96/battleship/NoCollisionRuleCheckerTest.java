package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    PlacementRuleChecker<Character> rule = new NoCollisionRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, rule);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H'));
    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(3, 2), 'H'));
    Ship<Character> s3 = factory.makeBattleship(new Placement(new Coordinate(3, 1), 'H'));
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(0, 2), 'V'));
    Ship<Character> s5 = factory.makeSubmarine(new Placement(new Coordinate(19, 2), 'H'));
    Ship<Character> s6 = factory.makeCarrier(new Placement(new Coordinate(10, 7), 'V'));

    b.tryAddShip(s1);
    b.tryAddShip(s2);

    assertFalse(rule.checkPlacement(s1, b));
    assertFalse(rule.checkPlacement(s2, b));
    assertFalse(rule.checkPlacement(s4, b));
    assertFalse(rule.checkPlacement(s3, b));
    assertTrue(rule.checkPlacement(s5, b));
    assertTrue(rule.checkPlacement(s6, b));

  }

  /**
   *   0|1|2|3|4|5|6|7|8|9
   * A b|b|b|b| | | | | | A
   * B  | | | | | | | | | B
   * C  | |c|c|c|c|c|c| | C
   * D  | | | | | | | | | D
   * E  | | | |c| | | | |sE
   * F  | | | |c| | | | |sF
   * G  | | | |c| | | | | G
   * H  | | | |c| | | | | H
   * I  | | | |c| | | | | I
   * J  | | | |c| | | | | J
   *   0|1|2|3|4|5|6|7|8|9
   * 
   * Submarine: 1x2 's'
   * Destroyer: 1x3 'd'
   * Battleship: 1x4 'b'
   * Carrier: 1x6 'c'
   */
  @Test
  public void test_chainedRule() {
    PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);

    Board<Character> b = new BattleShipBoard<Character>(10, 10, rule2);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H'));
    assertTrue(rule2.checkPlacement(s1, b));
    
    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(2, 2), 'H'));
    assertTrue(rule2.checkPlacement(s1, b));
    
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(4,9), 'V'));
    assertTrue(rule2.checkPlacement(s1, b));
    
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(4, 4), 'V'));
    assertTrue(rule2.checkPlacement(s1, b));

    b.tryAddShip(s1);
    b.tryAddShip(s2);

    b.tryAddShip(s3);
    b.tryAddShip(s4);

    Ship<Character> s5 = factory.makeBattleship(new Placement(new Coordinate(2, 1), 'H')); //Collision
    assertFalse(rule2.checkPlacement(s5, b));
     
    Ship<Character> s6 = factory.makeCarrier(new Placement(new Coordinate(0, 2), 'V'));//Collision
     assertFalse(rule2.checkPlacement(s6, b));

     Ship<Character> s7 = factory.makeBattleship(new Placement(new Coordinate(-1, 0), 'V'));//InBound 
    assertFalse(rule2.checkPlacement(s7, b));

    Ship<Character> s8 = factory.makeBattleship(new Placement(new Coordinate(8, 0), 'V'));//InBound
    assertFalse(rule2.checkPlacement(s8, b));

    Ship<Character> s9 = factory.makeCarrier(new Placement(new Coordinate(2, 6), 'H'));//InBound and Collision
    assertFalse(rule2.checkPlacement(s9, b));

    Ship<Character> s10 = factory.makeCarrier(new Placement(new Coordinate(9, -1), 'H'));//InBound and Collision
    assertFalse(rule2.checkPlacement(s10, b));

    Ship<Character> s11 = factory.makeCarrier(new Placement(new Coordinate(9, 0), 'V'));//Vertical Inbound
    assertFalse(rule2.checkPlacement(s11, b));

    Ship<Character> s12 = factory.makeCarrier(new Placement(new Coordinate(-3, 5), 'V'));//InBound
    assertFalse(rule2.checkPlacement(s12, b));
  }

}
