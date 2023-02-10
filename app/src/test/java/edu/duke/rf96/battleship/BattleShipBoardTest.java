package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10,20,'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }
  
  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20,'X'));
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expect){
    for(int row = 0; row  < b.getHeight(); row++){
      for(int col = 0; col < b.getWidth(); col++){
        Coordinate c = new Coordinate(row, col);
        assertEquals(expect[row][col],b.whatIsAtForSelf(c));
      }
    }
  }


  @Test
  public void test_empty_board(){
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10,20,'X');
    Character expected[][] = new Character[20][10] ;
    checkWhatIsAtBoard(b1, expected);
  }

  @Test
  public void test_board_with_ship(){
     BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10,20,'X');
     
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

    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2,'X');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H'));
    
    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(2, 2), 'H'));
    
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(4,9), 'V'));
    
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(4, 4), 'V'));

    assertNull(b.tryAddShip(s1));
    assertNull(b.tryAddShip(s2));

    assertNull(b.tryAddShip(s3));
    assertNull(b.tryAddShip(s4));

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
    assertNotNull(b.tryAddShip(s5));
     
    Ship<Character> s6 = factory.makeCarrier(new Placement(new Coordinate(0, 2), 'V'));//Collision
     assertNotNull(b.tryAddShip(s6));

     Ship<Character> s7 = factory.makeBattleship(new Placement(new Coordinate(-1, 0), 'V'));//InBound 
    assertNotNull(b.tryAddShip(s7));
    Ship<Character> s8 = factory.makeBattleship(new Placement(new Coordinate(8, 0), 'V'));//InBound

    assertNotNull(b.tryAddShip(s8));

    Ship<Character> s9 = factory.makeCarrier(new Placement(new Coordinate(2, 6), 'H'));//InBound and Collision
    assertNotNull(b.tryAddShip(s9));

    Ship<Character> s10 = factory.makeCarrier(new Placement(new Coordinate(9, -1), 'H'));//InBound and Collision
    assertNotNull(b.tryAddShip(s10));

    Ship<Character> s11 = factory.makeCarrier(new Placement(new Coordinate(9, 0), 'V'));//Vertical Inbound
    assertNotNull(b.tryAddShip(s11));

    Ship<Character> s12 = factory.makeCarrier(new Placement(new Coordinate(-3, 5), 'V'));//InBound
    assertNotNull(b.tryAddShip(s12));

    checkWhatIsAtBoard(b, expected);
  }
  

  @Test
  public void test_hitat(){
    PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);

    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2,'X');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H'));
    
    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(2, 2), 'H'));
    
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(4,9), 'V'));
    
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(4, 4), 'V'));

    assertNull(b.tryAddShip(s1));
    assertNull(b.tryAddShip(s2));

    assertNull(b.tryAddShip(s3));
    assertNull(b.tryAddShip(s4));

    //test for missed hit
    assertNull(b.fireAt(new Coordinate(4,7)));
    assertNull(b.fireAt(new Coordinate(6,7)));
    assertNull(b.fireAt(new Coordinate(1,9)));

    //test for hit
    assertSame(b.fireAt(new Coordinate(0,0)), s1);
    assertSame(b.fireAt(new Coordinate(0,1)), s1);
    assertFalse(s1.isSunk());
    assertSame(b.fireAt(new Coordinate(0,2)), s1);
    assertSame(b.fireAt(new Coordinate(0,3)), s1);
    assertTrue(s1.isSunk());

    
    assertSame(b.fireAt(new Coordinate(5,4)), s4);
    assertSame(b.fireAt(new Coordinate(6,4)), s4);
    assertFalse(s4.isSunk());
  }

  @Test
  public void test_whatisatForEnemy(){
     PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
      PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2,'X');
    V1ShipFactory factory = new V1ShipFactory();

    Character expect[][] = new Character[10][10];
    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 0), 'H'));
    
    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(2, 2), 'H'));
    
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(4,9), 'V'));
    
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(4, 4), 'V'));

    assertNull(b.tryAddShip(s1));
    assertNull(b.tryAddShip(s2));

    assertNull(b.tryAddShip(s3));
    assertNull(b.tryAddShip(s4));

     //test for missed hit
    assertNull(b.fireAt(new Coordinate(4,7)));
    assertNull(b.fireAt(new Coordinate(6,7)));
    assertNull(b.fireAt(new Coordinate(1,9)));

    //test for hit
    assertSame(b.fireAt(new Coordinate(0,0)), s1);
    assertSame(b.fireAt(new Coordinate(0,1)), s1);
    assertFalse(s1.isSunk());
    assertSame(b.fireAt(new Coordinate(0,2)), s1);
    assertSame(b.fireAt(new Coordinate(0,3)), s1);
    assertTrue(s1.isSunk());

    
    assertSame(b.fireAt(new Coordinate(5,4)), s4);
    assertSame(b.fireAt(new Coordinate(6,4)), s4);
    assertFalse(s4.isSunk());

    expect[4][7] = 'X';
    expect[6][7] = 'X';
    expect[1][9] = 'X';
    expect[0][0] = 'b';
    expect[0][1] = 'b';
    expect[0][2] = 'b';
    expect[0][3] = 'b';

    expect[5][4] = 'c';
    expect[6][4] = 'c';
    
     for(int row = 0; row  < b.getHeight(); row++){
      for(int col = 0; col < b.getWidth(); col++){
        Coordinate c = new Coordinate(row, col);
        assertEquals(expect[row][col],b.whatIsAtForEnemy(c));
      }
    }
  }

  @Test
  public void test_isLose(){
     String myView = "  0|1|2|3\n" +
        "A  | | |d A\n" +
        "B s|s| |d B\n" +
        "C  | | |d C\n" +
        "  0|1|2|3\n";
    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b);

    V1ShipFactory factory = new V1ShipFactory();

    Ship<Character> s1 = factory.makeSubmarine(new Placement(new Coordinate(1, 0), 'H'));

    Ship<Character> s2 = factory.makeDestroyer(new Placement(new Coordinate(0, 3), 'V'));

    assertNull(b.tryAddShip(s1));
    assertNull(b.tryAddShip(s2));

    b.fireAt(new Coordinate(1, 0));
    b.fireAt(new Coordinate(1, 1));
    assertFalse(b.isLose());
    
    b.fireAt(new Coordinate(2, 1));// miss
    b.fireAt(new Coordinate(2, 2));// miss
    b.fireAt(new Coordinate(0, 3));
    assertFalse(b.isLose());
    b.fireAt(new Coordinate(2, 3));
    b.fireAt(new Coordinate(1, 3));
    b.fireAt(new Coordinate(0, 3));
    assertTrue(b.isLose());
     
  }

   @Test
  public void test_whichshipisat(){
     PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2,'X');
    V2ShipFactory factory = new V2ShipFactory();

    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 1), 'U'));
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(0,5), 'H'));
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(7, 2), 'L'));
    Ship<Character> s5 = factory.makeBattleship(new Placement(new Coordinate(2, 5), 'R'));

    assertNull(b.tryAddShip(s1));

    assertNull(b.tryAddShip(s3));
    assertNull(b.tryAddShip(s4));
    assertNull(b.tryAddShip(s5));

    assertEquals(s1, b.whichShipisAt(new Coordinate(1,2)));
    assertEquals(s3, b.whichShipisAt(new Coordinate(0,6)));
     assertEquals(s4, b.whichShipisAt(new Coordinate(8,2)));
     assertNull(b.whichShipisAt(new Coordinate(0, 0)));

   }

  @Test
  public void test_moveShip(){
    PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2,'X');
    V2ShipFactory factory = new V2ShipFactory();

    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 1), 'U'));
    Ship<Character> s3 = factory.makeSubmarine(new Placement(new Coordinate(0,5), 'H'));

    Ship<Character> s2 = factory.makeCarrier(new Placement(new Coordinate(3, 2), 'D'));
    Ship<Character> s4 = factory.makeCarrier(new Placement(new Coordinate(7, 0), 'L'));
    Ship<Character> s5 = factory.makeBattleship(new Placement(new Coordinate(2, 5), 'R'));

    Ship<Character> s6 = factory.makeSubmarine(new Placement(new Coordinate(0,5), 'V'));
    Ship<Character> s7 = factory.makeBattleship(new Placement(new Coordinate(5,7), 'D'));
    Ship<Character> s8 = factory.makeBattleship(new Placement(new Coordinate(0,1), 'D'));
    

    assertNull(b.tryAddShip(s1));

    assertNull(b.tryAddShip(s3));
    assertNull(b.tryAddShip(s4));
    assertNull(b.tryAddShip(s5));

    b.fireAt(new Coordinate(0, 6));
    b.fireAt(new Coordinate(4, 5));
    b.fireAt(new Coordinate(3, 6));
    b.fireAt(new Coordinate(8, 1));
    b.fireAt(new Coordinate(8, 3));
    // // System.out.print(view.displayMyOwnBoard());

    assertNull(b.tryMoveShip(s6, s3));
    assertNull(b.tryMoveShip(s7, s5));
    assertNull(b.tryMoveShip(s2, s4));
    assertNotNull(b.tryMoveShip(s8, s5));
    //System.out.print(view.displayMyOwnBoard());

    assertTrue(s6.wasHitAt(new Coordinate(1,5)));
    assertTrue(s2.wasHitAt(new Coordinate(4,3)));
    assertTrue(s2.wasHitAt(new Coordinate(6,3)));
    assertTrue(s7.wasHitAt(new Coordinate(5,7)));
    assertTrue(s7.wasHitAt(new Coordinate(6,8)));
    
    
  }

 @Test
  public void test_Sonar(){
   PlacementRuleChecker<Character> rule1 = new InBoundsRuleChecker<Character>(null);
    PlacementRuleChecker<Character> rule2 = new NoCollisionRuleChecker<Character>(rule1);
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, rule2,'X');
    V2ShipFactory factory = new V2ShipFactory();

    Ship<Character> s1 = factory.makeBattleship(new Placement(new Coordinate(0, 1), 'U'));
    Ship<Character> s2 = factory.makeSubmarine(new Placement(new Coordinate(0,5), 'H'));
    Ship<Character> s3 = factory.makeCarrier(new Placement(new Coordinate(7, 0), 'L'));
    Ship<Character> s4 = factory.makeBattleship(new Placement(new Coordinate(2, 5), 'R'));


    assertNull(b.tryAddShip(s1));
    assertNull(b.tryAddShip(s2));
    assertNull(b.tryAddShip(s3));
    assertNull(b.tryAddShip(s4));
    
    HashMap<String,Integer> result1 = b.sonarScan(new Coordinate(3,4));
    assertEquals(5, result1.get("Battleship"));
    assertNull(result1.get("Submarine"));
    assertNull(result1.get("Carrier"));

    assertThrows(IllegalArgumentException.class, ()->b.sonarScan(new Coordinate(10,9)));
    
    
    HashMap<String,Integer> result2 = b.sonarScan(new Coordinate(0,8));
    assertEquals(2, result2.get("Submarine"));
    assertNull(result2.get("Carrier"));
    assertNull(result2.get("Battleship"));


     HashMap<String,Integer> result3 = b.sonarScan(new Coordinate(8,1));
    assertEquals(6, result3.get("Carrier"));
    assertNull(result3.get("Submarine"));
    assertNull(result3.get("Battleship"));
   
   
 }
}
