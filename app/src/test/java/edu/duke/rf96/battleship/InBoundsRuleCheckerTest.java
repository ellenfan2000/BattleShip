package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {

    PlacementRuleChecker<Character> rule = new InBoundsRuleChecker<>(null);
    Board<Character> b = new BattleShipBoard<Character>(10, 26, rule);

    V1ShipFactory ships = new V1ShipFactory();

    Ship<Character> s1 = ships.makeBattleship(new Placement(new Coordinate(-1, 0), 'V'));
    assertFalse(rule.checkPlacement(s1, b));

    Ship<Character> s2 = ships.makeBattleship(new Placement(new Coordinate(25, 0), 'V'));
    assertFalse(rule.checkPlacement(s2, b));

    Ship<Character> s3 = ships.makeSubmarine(new Placement(new Coordinate(24, 0), 'V'));
    assertTrue(rule.checkPlacement(s3, b));

    Ship<Character> s4 = ships.makeCarrier(new Placement(new Coordinate(4, 0), 'H'));
    assertTrue(rule.checkPlacement(s4, b));

    Ship<Character> s5 = ships.makeCarrier(new Placement(new Coordinate(5, 0), 'H'));
    assertTrue(rule.checkPlacement(s5, b));

    Ship<Character> s6 = ships.makeCarrier(new Placement(new Coordinate(5, -1), 'H'));
    assertFalse(rule.checkPlacement(s6, b));

  }

}
