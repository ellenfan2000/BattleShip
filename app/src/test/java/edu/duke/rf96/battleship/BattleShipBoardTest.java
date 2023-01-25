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
     Ship<Character> s1 = new BasicShip(new Coordinate(0, 0));
     Ship<Character> s2 = new BasicShip(new Coordinate(1, 3));
     Ship<Character> s3 = new BasicShip(new Coordinate(7, 9));

     expected[0][0] = 's';
     expected[1][3] = 's';
     expected[7][9] = 's';
     
     b1.tryAddShip(s1);
     b1.tryAddShip(s2);
     b1.tryAddShip(s3);
     checkWhatIsAtBoard(b1, expected);
    
  }

}
