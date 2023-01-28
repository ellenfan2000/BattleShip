package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10,20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }
  
  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expect){
    for(int row = 0; row  < b.getHeight(); row++){
      for(int col = 0; col < b.getWidth(); col++){
        Coordinate c = new Coordinate(row, col);
        assertEquals(expect[row][col],b.whatIsAt(c));
      }
    }
  }


  @Test
  public void test_empty_board(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10,20);
    Character expected[][] = new Character[20][10] ;
    checkWhatIsAtBoard(b1, expected);
  }

  @Test
  public void test_board_with_ship(){
     BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10,20);
     
     Character expected[][] = new Character[20][10] ;
     //     Coordinate c1 = new Coordinate(0, 0);
     Ship<Character> s1 = new RectangleShip<Character>(new Coordinate(0, 0), 's', '*');
     Ship<Character> s2 = new RectangleShip<Character>(new Coordinate(1, 3), 's', '*');
     Ship<Character> s3 = new RectangleShip<Character>(new Coordinate(7, 9), 's', '*');

     expected[0][0] = 's';
     expected[1][3] = 's';
     expected[7][9] = 's';
     
     b1.tryAddShip(s1);
     b1.tryAddShip(s2);
     b1.tryAddShip(s3);
     checkWhatIsAtBoard(b1, expected);
  }
  @Test
  public void test_add_ship(){
    PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);

    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2);
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H'));
    
    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(2, 2), 'H'));
    
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(4,9), 'V'));
    
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(4, 4), 'V'));

    assertTrue(b.tryAddShip(s1));
    assertTrue(b.tryAddShip(s2));

    assertTrue(b.tryAddShip(s3));
    assertTrue(b.tryAddShip(s4));

    Character expected[][] = new Character[10][10];
    for(int i  = 0; i<4; i++){
      expected[0][i] = 'b';
    }

     for(int i  = 2; i<8; i++){
      expected[2][i] = 'c';
    }

     for(int i  = 4; i<10; i++){
      expected[i][4] = 'c';
    }

      for(int i  = 4; i<6; i++){
      expected[i][9] = 's';
    }
    
    Ship<Character> s5 = factory.makeBattleship(new Placement(new Coordinate(2, 1), 'H')); //Collision
    assertFalse(b.tryAddShip(s5));
     
    Ship<Character> s6 = factory.makeCarrier(new Placement(new Coordinate(0, 2), 'V'));//Collision
     assertFalse(b.tryAddShip(s6));

     Ship<Character> s7 = factory.makeBattleship(new Placement(new Coordinate(-1, 0), 'V'));//InBound 
    assertFalse(b.tryAddShip(s7));
    Ship<Character> s8 = factory.makeBattleship(new Placement(new Coordinate(8, 0), 'V'));//InBound

    assertFalse(b.tryAddShip(s8));

    Ship<Character> s9 = factory.makeCarrier(new Placement(new Coordinate(2, 6), 'H'));//InBound and Collision
    assertFalse(b.tryAddShip(s9));

    Ship<Character> s10 = factory.makeCarrier(new Placement(new Coordinate(9, -1), 'H'));//InBound and Collision
    assertFalse(b.tryAddShip(s10));

    Ship<Character> s11 = factory.makeCarrier(new Placement(new Coordinate(9, 0), 'V'));//Vertical Inbound
    assertFalse(b.tryAddShip(s11));

    Ship<Character> s12 = factory.makeCarrier(new Placement(new Coordinate(-3, 5), 'V'));//InBound
    assertFalse(b.tryAddShip(s12));

    checkWhatIsAtBoard(b, expected);
  }
  

}
