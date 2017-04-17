import java.awt.*;
import objectdraw.*;

public class Puzzle extends WindowController{

  private BoardPiece[] BPiece;
  private PuzzlePiece[] PPiece;
  private static final int CANVAS_WIDTH = 735;
  private static final int CANVAS_HEIGHT = 380;

  private static final int NUM_OF_IMAGES = 9;
  private Image[] Img = new Image[NUM_OF_IMAGES]; ;
  private Image[] PuzzleImg = new Image[NUM_OF_IMAGES];
  private static final int PIECES_PER_COL = 3;
  private static final int PIECES_PER_ROW = 3;
  private static final int PUZZLE_SPACING = 20;
  private static final int PUZZLE_OFFSET = 355;
  private static final int BOARD_MARGIN_X = 25;
  private static final int BOARD_MARGIN_Y = 40;
  private static final int SIDE_LENGTH = 100;

  private static int Random;
  private static boolean exist;
  private static int num;
  private static int count;

  private Text WinText; 
  private static final int TEXT_X = 375;
  private static final int TEXT_Y = BOARD_MARGIN_Y + SIDE_LENGTH * (4 / 
      PIECES_PER_ROW);
  private static int Px,Py,Bx,By;

  private int[] BPId = new int[NUM_OF_IMAGES];
  private int[] PPId = new int[NUM_OF_IMAGES]; 
  private static boolean[] PPieceGrabbed = new boolean[NUM_OF_IMAGES];
  private static boolean[] BPieceContained = new boolean[NUM_OF_IMAGES];
  private static boolean[] BPieceLocked = new boolean[NUM_OF_IMAGES];

  private static Location LastPoint;
  private Location[] BPLoc = new Location[NUM_OF_IMAGES]; 
  private Location[] BPCenter = new Location[NUM_OF_IMAGES];
  private Location[] PPLoc = new Location[NUM_OF_IMAGES];
  private static Location[] PPCenter = new Location[NUM_OF_IMAGES];  

  private static int LockedCount = 0;
  private static int index;
  private boolean[] array = new boolean[NUM_OF_IMAGES];
  public static void main(String[] args){
    new Acme.MainFrame(new Puzzle(), args, CANVAS_WIDTH, CANVAS_HEIGHT);
  }

  public void begin(){
    this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
    WinText = new Text("YOU WIN!!!", TEXT_X, TEXT_Y, canvas);
    WinText.setBold(true);
    WinText.setFontSize(50);
    WinText.setColor(Color.GREEN);

    WinText.hide();

    Bx = 0;
    By = 0;
    this.BPiece = new BoardPiece[NUM_OF_IMAGES];    
    for(int i=0; i<9; i++){ 
      Img[i] = getImage("p"+i+".jpg"); 
    }
    for (int i = 0; i < 9; i++){
      Bx = BOARD_MARGIN_X + SIDE_LENGTH * (i % PIECES_PER_COL);
      By = BOARD_MARGIN_Y + SIDE_LENGTH * (i / PIECES_PER_ROW);
      BPLoc[i] = new Location(Bx,By);
     // BPCenter[i] = new Location(Bx,By);

      BPiece[i] = new BoardPiece(Img[i], i, BPLoc[i], canvas); 
      BPiece[i].hideHighlight();
   //   BPCenter[i] = BPiece[i].getCenter();
      BPId[i] = BPiece[i].getId();
    }
  
    Px = 0;
    Py = 0;
    this.PPiece = new PuzzlePiece[NUM_OF_IMAGES];
    for(int i=0; i<9; i++){ 
      PuzzleImg[i] = getImage("p"+i+".jpg"); 
    }
    
    for(int i=0; i<9; i++){
      Px = PUZZLE_OFFSET + PUZZLE_SPACING * (i % PIECES_PER_COL + 1)
        + SIDE_LENGTH * (i % PIECES_PER_COL);
      Py = PUZZLE_SPACING * (i / PIECES_PER_ROW + 1) +
        SIDE_LENGTH * (i / PIECES_PER_ROW);
      PPLoc[i] = new Location(Px,Py);
      PPCenter[i] = new Location(Px,Py);
    
      PPiece[i] = getRandomPiece(PPLoc[i] , canvas);
      System.out.println("PPiece-> "+ PPiece[i].getId());
      PPId[i] = PPiece[i].getId(); 
//System.out.println("Id: " + PPId[i]);
      PPiece[i].showHighlight(Color.BLACK);
//need change !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      PPCenter[i] = PPiece[i].getCenter();
      System.out.println("PPCenter -> " +  i  + PPCenter[i]);
    }
  }

  public PuzzlePiece getRandomPiece(Location point, DrawingCanvas canvas){
    RandomIntGenerator random = new RandomIntGenerator(0, 8); 

    while(true){
    num = random.nextValue(); 
      if(array[num] == false){ 
        array[num] = true;   //loop whenever num not equal pervious...
        break;
      }
    }
    
    System.out.println("id num"+ num);
    return new PuzzlePiece(PuzzleImg[num], num, point, canvas); 
  }

  public void onMousePress( Location point ){
    
    for(int i=0; i<9; i++){
   //   this.PPieceGrabbed[i] = (this.PPiece[i].contains(point));
      if(this.PPiece[i].contains(point)){

        this.PPieceGrabbed[i] = true;
      index = i;
      }
    }
    LastPoint = point;
  }

  public void onMouseDrag( Location point ){
    double dx = point.getX() - LastPoint.getX();
    double dy = point.getY() - LastPoint.getY();
 
      if(this.PPieceGrabbed[index]){
        double centerX = this.PPCenter[index].getX();
        double centerY = this.PPCenter[index].getY();
        this.PPCenter[index] = new Location(centerX+dx,centerY+dy); 

        if(centerX>0  && centerX<this.canvas.getWidth() &&
            centerY>0 && centerY<this.canvas.getHeight()){
        this.PPiece[index].move(dx,dy); 
        this.PPLoc[index].translate(dx,dy);
        }
      }
      LastPoint = point;
    }

  public void onMouseRelease(Location point){
      //double dx = point.getX() - LastPoint.getX();   
      //double dy = point.getY() - LastPoint.getY();   
/*
      if(this.PPieceGrabbed[index]){
        double centerX = this.PPCenter[index].getX();
        double centerY = this.PPCenter[index].getY();
        this.PPCenter[index] = new Location(centerX+dx,centerY+dy); 
      }
      */
      //System.out.println("point center -> "+this.PPiece[index].getCenter());
      //System.out.println("box center -> "+this.PPiece[index].getCenter());

    for(int i = 0; i<9;i++){
    //    LastPoint = point;
        boolean x = this.BPiece[i].contains(this.PPiece[index].getCenter());
        boolean y = this.BPiece[i].equals(this.PPiece[index]);
        System.out.println("BPiece -> " + i + " " + this.BPiece[i].getCenter() + "contains -> " + x + " equals -> " + y );
      if (x && y){
            System.out.println("enter" + LockedCount);
            this.BPieceContained[i] = true; 
            this.BPiece[i].show();
            this.BPiece[i].showHighlight(Color.GREEN);
            this.PPiece[index].hide(); 
            this.PPiece[index].hideHighlight();
            this.BPieceLocked[i] = true; 
            LockedCount++;
          }
        }
    
    
    if(LockedCount == 9){
      for(int i=0; i<9; i++){ 
       this.BPiece[i].hide();
       this.BPiece[i].removeFromCanvas();
      }
      this.WinText.show();  
    }
  }
}
