import objectdraw.*;

public interface Piece extends Highlightable, Hideable{
  public abstract boolean contains(Location point);
  public abstract boolean equals(Object o);
  public abstract Location getCenter();
  public abstract int getId();
  public abstract void move(double dx, double dy);

}
