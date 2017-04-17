import java.awt.*;
import objectdraw.*;

public class PuzzlePiece implements Piece{
  private FramedRect Grid;
  private int PuzzlePieceID;
  private VisibleImage img;
  private Image Pic;

  private Location loc;
  private Location center;

  private int TWO = 2;
  private int size = 100;

  public PuzzlePiece(Image Pic, int id, Location loc, DrawingCanvas canvas){
    this.loc = loc;
    this.img = new VisibleImage(Pic, loc, canvas);
    Grid = new FramedRect(loc, size, size, canvas);
    PuzzlePieceID = id;
    this.center = new Location(loc.getX()+size/TWO, loc.getY()+size/TWO); 
  }

  public Location getCenter(){
    return this.center;
  }
  
  public int getId(){
    return PuzzlePieceID;
  }

  public void show(){
    img.show();
    Grid.show();
  }

  public void hide(){
    img.hide();
  }

  public void showHighlight(Color color){
    Grid.setColor(color);
  }
  public void hideHighlight(){
    Grid.hide();
  }
  public boolean contains( Location point ){
    return img.contains(point);
  }

  public void move(double dx, double dy){
    img.move(dx,dy);
    Grid.move(dx,dy);
    this.center.translate(dx,dy);
  }

  public boolean equals(Piece piece){
    if(this.getId() == piece.getId()){
      return true;
    }else{
      return false;
    }
  }

}
