package edu.duke.rf96.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_Initialize() {
    Coordinate c1 = new Coordinate(1, 2);
    Placement p1 = new Placement(c1, 'H');
    assertEquals(p1.getCoordinate(), c1);
    assertEquals(p1.getOrientation(), 'H');

    assertThrows(IllegalArgumentException.class, ()-> new Placement(c1,'G'));
  }

  @Test
  public void test_string_construct_valid() {
    Placement p1 = new Placement("A3H");
    assertEquals(0, p1.getCoordinate().getRow());
    assertEquals(3, p1.getCoordinate().getColumn());
    assertEquals('H', p1.getOrientation());

    Placement p2 = new Placement("G9v");
    assertEquals(6, p2.getCoordinate().getRow());
    assertEquals(9, p2.getCoordinate().getColumn());
    assertEquals('V', p2.getOrientation());

    Placement p3 = new Placement("A3h");
    assertEquals(0, p3.getCoordinate().getRow());
    assertEquals(3, p3.getCoordinate().getColumn());
    assertEquals('H', p1.getOrientation());

    Placement p4 = new Placement("a3H");
    assertEquals(0, p4.getCoordinate().getRow());
    assertEquals(3, p4.getCoordinate().getColumn());
    assertEquals('H', p1.getOrientation());
  }

  @Test
  public void test_string_construct_error() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("123"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A2D"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("12."));
    assertThrows(IllegalArgumentException.class, () -> new Placement("C103"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A5d"));
  }

  @Test
  public void test_equals() {
    Placement p1 = new Placement("A3H");
    Placement p2 = new Placement("a3h");
    Placement p3 = new Placement("a3V");
    Placement p4 = new Placement("B3V");

    assertEquals(p1, p2);
    assertEquals(p2, p1);
    assertNotEquals(p1, p3);
    assertNotEquals(p3, p4);
    assertNotEquals(p1, "(0, 3): H");
  }

  @Test
  public void test_toString() {
    Placement p1 = new Placement("A3H");
    Placement p2 = new Placement("B3h");
    Placement p3 = new Placement("D9V");

    assertEquals(p1.toString(), "(0, 3): H");
    assertEquals(p2.toString(), "(1, 3): H");
    assertEquals(p3.toString(), "(3, 9): V");

  }

  @Test
  public void test_hash() {
    Placement p1 = new Placement("A3H");
    Placement p2 = new Placement("a3h");
    Placement p3 = new Placement("a3V");
    Placement p4 = new Placement("B3V");
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p3.hashCode(), p4.hashCode());
    assertNotEquals(p2.hashCode(), p4.hashCode());

  }

}
