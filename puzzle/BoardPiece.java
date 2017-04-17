import java.awt.*;
import objectdraw.*;

public class BoardPiece implements Piece{
  private FramedRect BlackGrid;
  private FramedRect GreenGrid;
  private int ID;
  private VisibleImage img;
  private Image Pic;
  private int BoardPieceID;

  private Location loc;
  private Location center;
  
  private int TWO = 2;
  private int size = 100;
  private int CENTER_BOX_SIZE = 50;
  private double centerX, centerY;

  public BoardPiece(Image Pic, int id, Location loc, DrawingCanvas canvas){
    this.loc = loc;
    img = new VisibleImage(Pic, loc, canvas);
    BlackGrid = new FramedRect(loc, size, size, canvas); 
    GreenGrid = new FramedRect(loc, size+1, size+1, canvas);
    this.center = new Location(loc.getX()+size/TWO, loc.getY()+size/TWO); 
  
    BoardPieceID = id;
    img.hide();
    GreenGrid.hide();
  }

  public int getId(){
    return BoardPieceID;
  }

  public Location getCenter(){
    return this.center;
  }

  public void hide(){
    GreenGrid.hide();
    BlackGrid.hide();
  }

  public void show(){
    img.show();
  }
  public boolean contains(Location point){ 
  
   if((point.getX()>(this.center.getX()-25)) && (point.getX()<(this.center.getX()+25))&&
       (point.getY()>(this.center.getY()-25)) && (point.getY()<(this.center.getY()+25))){
    return true;
   }else{
     return false;
   }
  }

  public void move(double dx, double dy){
    img.move(dx,dy);
  }

  public boolean equals(Piece piece){
    if(this.getId() == piece.getId()){
      return true;
     }else{
       return false;
     }
  }

  public void hideHighlight(){
    GreenGrid.hide();
    BlackGrid.show();
  }

  public void showHighlight(Color color){
    GreenGrid.setColor(color); 
    GreenGrid.show();
  }
  
  public void removeFromCanvas(){
    GreenGrid.removeFromCanvas();
    BlackGrid.removeFromCanvas();
  
  }
  
}
