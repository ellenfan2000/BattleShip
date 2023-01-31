package edu.duke.rf96.battleship;

/*
This class describe the placement of a ship
including coordinate and oritation
orientation V: vertical, H: horizontal
where: coordinate of the top left corner of the ship
 */
public class Placement {
  // where: coordinate of the top left corner of the ship
  private final Coordinate where;

  // orientation V: vertical, H: horizontal
  private final char orien;

  /*
   * Construct a Placement
   */
  public Placement(Coordinate w, char or) {
    where = w;
    orien = or;
    if (orien != 'H' &&orien != 'V' ){
       throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
    }
  }

  /*
   * Construct a Placement
   * 
   * @param descr : string got from players in the form "A3H", "B9H"
   */
  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Placement should have 3 characters but have " + descr.length());
    }
    descr = descr.toUpperCase();
    String coorStr = descr.substring(0, 2);
    char orienC = descr.charAt(2);

    where = new Coordinate(coorStr);
    if (orienC != 'H' && orienC != 'V') {
      throw new IllegalArgumentException("Placement Orientation should be H or V but is " + orienC);
    }
    orien = orienC;
  }

  public Coordinate getCoordinate() {
    return where;
  }

  public char getOrientation() {
    return orien;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.getCoordinate()) && orien == p.getOrientation();
    }
    return false;
  }

  @Override
  public String toString() {
    return where.toString() + ": " + orien;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
