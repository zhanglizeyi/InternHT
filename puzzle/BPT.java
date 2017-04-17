import java.awt.*;
import objectdraw.*;

public class BPT implements Piece{
	private VisibleImage img;
	private int id;
	private FramedRect borders, highlights;

	private Location loc;
	private Location center;

	private int CENTER_BOX_SIZE = 50;
	private int size = 100;
	private double center_x, center_y;

	public BPT(Image img, int id, Location loc, DrawingCanvas canvas){
		this.loc = loc;
		img = new VisibleImage(img, loc, canvas);
		borders = new FramedRect(loc, size, size, canvas);
		highlights = new FramedRect(loc, size+1, size+1, canvas);
		this.center = new Location(loc.getX()+size/2, loc.getY()+size/2);
	
		this.id = id;
		img.hide();
		highlights.hide();
	}

	public boolean contains(Location point){
		if((point.getX()>(this.center.getX()-25)) 
			&& (point.getX()<(this.center.getX()+25)) 
			&&(point.getY()>(this.center.getY()-25)) 
			&& (point.getY()<(this.center.getY()+25))){
    		return true;
   		}else{
     		return false;
   		}
	}
  	
  	public boolean equals(Piece p){
  		if(this.id == p.getId())
  			return true;
  		else
  			return false;
  	}
  	
  	public Location getCenter(){
  		return this.center;
  	}
  	
  	public int getId(){
  		return id;
  	}

  	public void move(double dx, double dy){
  		img.move(center_x, center_y);
  	}

  	public void show(){

  	}

  	public void hide(){
  		borders.hide();
  		highlights.hide();
  	}

  	public void showHighlight(Color color){
  		borders.hide();
  		highlights.show();
  	}	

  	public void hideHighlight(){
  		highlights.hide();
  		borders.show();
  	}
}