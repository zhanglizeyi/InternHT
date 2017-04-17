/*
 *Name: Meiyi He
 *Login: cs11fawq
 *PID: A91041817
 *Date: Nov.13.2015
 *File Name: SnakeController.java
 *This file defines a controller that handle keyboardEvents of SnakeGame
 */
import objectdraw.*;
import java.awt.*;
import java.util.*;
import java.lang.Math;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 *Class Name: SnakeController
 *Creating the class SnakeController
 */
public class SnakeController extends WindowController 
											      implements ActionListener, KeyListener{

	private static final int Y_PADDING = 50;
	private static final int LINUX_MIGHT_HAVE_THIS_EXTRA_WEIRD_PADDING = 6;
	//window padding
	private static final int MIN_DIM = 500;
	private static final int MAX_DIM = 800;
	//dimensions range 500-800
	private static final int MIN_SPEED = 1000;
	private static final int MAX_SPEED = 100;
	//moving speed 100-1000
	private static final int MIN_SIZE = 20;
	private static final int MAX_SIZE = 400;
	//snake size range 20-400
	private static final int NUM_TOP_COLUMNS = 2;
	private static final int NUM_TOP_ROWS = 1;

	private static final int TEXT_SIZE = 40;
	private static final int ADD_SCORE =100;
	private static final int TWO = 2;
	private static final int THREE = 3;

	private static int dimensions;
	private int side;
	private int size;
	private int delay;
	private int score;
	private int highScore;
	//instance variable 

	private JPanel northPanel = new JPanel();
	private JPanel buttonPanel =new JPanel();
	private JLabel scoreLabel = new JLabel();
	private JLabel highScoreLabel = new JLabel();
	private JButton newgame = new JButton(PA8Strings.NEW_GAME);
	private Text gameOverText = new Text("GAME OVER",0,0,canvas);
	private Text winText = new Text("YOU WON!!", 0,0,canvas);
	private Text pauseText = new Text("PAUSED",0,0,canvas);;
	//layout of the game, JPanel stuff

	private boolean gameOver,won,paused,check,checkNewGame;
	//check whether gameOver, won, pause or restart

	private Snake snake;
	//snake object
	private Coordinate START_COORD;
	//start coordinate of every snake

	private FilledRect apple;
	private final Color APPLE_COLOR = Color.RED;
	//apple color
	private Coordinate appleCoord;
	//apple coordinate
	private Coordinate new_apple_coord;
	//the new apple coordinates

	private RandomIntGenerator random;
	//generate a random number to place apples
	
	private ArrayList<Coordinate> boardCoord;
	//arraylist for the empty board coordinates
	private ArrayList<Coordinate> snakeCoord;
	//arraylist for the snake Coordinates



	/**
	 *Constructor Name: SnakeController
	 *Purpose: override the original controller to takes in command line args
	 */
	public SnakeController(String [] args){
		
		if((args.length<THREE)||(args.length>THREE)){
			//if args less then 3 then it will print out usage and exit
			System.out.printf(PA8Strings.USAGE);
			System.exit(1);

		}else{

			dimensions = Integer.parseInt(args[0]);
    	size = Integer.parseInt(args[1]);
			delay = Integer.parseInt(args[TWO]);
			//store the value that user passed in command line

			if((dimensions>= MIN_DIM)&&(dimensions<=MAX_DIM)){
			//check if demension in 500-800
				if((size>=MIN_SIZE)&&(size<=MAX_SIZE)){
				//check if segment size in 20-400
					if(dimensions%size == 0){
					//check if dimension is evenly divisible by size
						if(size<=dimensions/TWO){
						//check if size is less than half the dimension
							if((delay>=MAX_SPEED)&&(delay<=MIN_SPEED)){
							//check the speed of the snake in 100-1000
								
							}else{
							//delay time out of range
								System.out.printf(PA8Strings.USAGE);
								System.exit(1);
							}
						}else{
						//size larger than half the dimensions
							System.out.printf(PA8Strings.SEG_TOO_LARGE,
																					size,dimensions,dimensions);
							System.out.printf(PA8Strings.USAGE);
					  	System.exit(1);
						}
					}else{
					//dimensions cannot be evenly divisible by size
						System.out.printf(PA8Strings.SEG_DOES_NOT_FIT,
																					size,dimensions,dimensions);
						System.out.printf(PA8Strings.USAGE);
						System.exit(1);
					}
				}else{
				//size out of range
					System.out.printf(PA8Strings.OUT_OF_RANGE,size,MIN_SIZE,MAX_SIZE);
					System.out.printf(PA8Strings.USAGE);
					System.exit(1);
				}
			}else{
			//dimensions out of range
				System.out.printf(PA8Strings.OUT_OF_RANGE,dimensions,MIN_DIM,MAX_DIM);
				System.out.printf(PA8Strings.USAGE);
				System.exit(1);
			}
		}
	}//end of SnakeController constructor




	/**
	 *Method Name: Main Method
	 *Purpose: takes in 3 args dimension, size, delay then initialize canvas
   */
	public static void main(String [] args){
		if(args.length == THREE){
			new Acme.MainFrame(new SnakeController(args), args, dimensions,
	   																	          dimensions+Y_PADDING);
	 						    //if all satisfied, then create a new Acme.MainFrame
		}else{
			new SnakeController(args);
		}
	}//end of main



	/**
   *Method Name: begin
   *Purpose: initialize the program 
   */
	public void begin(){

		check = true;
		this.checkNewGame = false;
		score = 0;
		layoutGame();
		START_COORD = new Coordinate(size,size);

		snake = new Snake(new Coordinate(size,size),size,delay,canvas,this);
		//construct the snake
		apple = new FilledRect(0,0,size,size,canvas);
		apple.setColor(APPLE_COLOR);
		//construct the apple 

    appleCoord = placeApple(canvas);
    //when begin generate an apple

    snake.setAppleCoord(appleCoord);
    //send the current apple's coordinate back to snake to detect
    //if the snake eats an apple or not
    
    newgame.addActionListener(this);
		canvas.addKeyListener(this);
		///add KeyListener on canvas 
					
		canvas.requestFocusInWindow();
	}//end of begin



	/**
	 *Method Name: layoutGame
	 *Purpose: Construct the game layout, add JLabel, JButtons
	 */
	private void layoutGame(){

		northPanel.setLayout(new GridLayout(1,TWO));
		//north JPanel
		scoreLabel.setText(String.format(PA8Strings.SCORE,score));
		highScoreLabel.setText(String.format(PA8Strings.HIGH_SCORE,highScore));
		northPanel.add(scoreLabel);
		northPanel.add(highScoreLabel);
		//JLabel for north panel

		buttonPanel.add(newgame);
		//south JPanel

		Container contentPane = getContentPane();
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		//add contenpane 

		double pauseX, pauseY;
		pauseText.setFontSize(TEXT_SIZE);
		pauseX = (dimensions/TWO) - (pauseText.getWidth()/TWO);
		pauseY = (dimensions/TWO) - (pauseText.getHeight()/TWO);
		pauseText.moveTo(pauseX, pauseY);
		pauseText.hide();
		//pause text object

		double gOverX, gOverY;
		gameOverText.setFontSize(TEXT_SIZE);
		gOverX = (dimensions/TWO) - (gameOverText.getWidth()/TWO);
		gOverY = (dimensions/TWO) - (gameOverText.getHeight()/TWO);
		gameOverText.moveTo(gOverX, gOverY);
		gameOverText.hide();
		//gameover text object

		double winX, winY;
		winText.setFontSize(TEXT_SIZE);
		winX = (dimensions/TWO) - (winText.getWidth()/TWO);
		winY = (dimensions/TWO) - (winText.getHeight()/TWO);
		winText.moveTo(winX, winY);
		winText.hide();
		//win text object


		contentPane.validate();
	}//end of layoutGame



	/**
	 *Method Name: placeApple
	 *Purpose: return an coordinate to randomly place the apple
	 *@param canvas the canvas to put apple on
	 *@return Coordinate the apple's coordinate
	 */
	public Coordinate placeApple(DrawingCanvas canvas){

		boardCoord = new ArrayList<Coordinate>();
		//initializing the board coordinates arraylist

		for(int i = 0; i<dimensions; i+=size){
			for(int j = 0; j<dimensions; j+=size){

				boardCoord.add(new Coordinate(i,j));
				//based on the size and dimensions of the user input, 
				//create board coordinates arraylist
			}
		}


		int snakeSize = snake.getSnakeCoord().size();
		//get the size of snake segment
		int boardSize = boardCoord.size();
		//get the size of board coordinates


		for(int i= 0; i<snakeSize; i++){
			//loop through the current snake size
			boardCoord.remove(snake.getSnakeCoord().get(i));
			//remove the coordinates that occupy by the snakes
			boardSize = boardCoord.size();
			//update the board coordinates size
		}


		random = new RandomIntGenerator(0,boardSize-1);     
    int randomNum = random.nextValue();
    //generate a random number based on the current board coordinate size


    if((boardSize>=0)&&(snakeSize>1)){
    	//update the score only when an apple is eaten 

    	this.score=score+ADD_SCORE;
			scoreLabel.setText(String.format(PA8Strings.SCORE,score));
    }


    if(boardSize>0){
    	//if there still have empty spot on board, make a new apple
		
			new_apple_coord = boardCoord.get(randomNum);
			apple.moveTo(new_apple_coord.getX(),new_apple_coord.getY());

		}else{
			//if there is no more empty spaces, stop generating apple
			//show the win text on the canvas
	
			this.score=score+ADD_SCORE;
			scoreLabel.setText(String.format(PA8Strings.SCORE,score));
			//update the score 

			winText.show();
			gameOverText.hide();
			pauseText.hide();
			winText.sendToFront();
			//show the text "YOU WON!!"

			won = true;
			//change the boolean value to true

			if(apple!= null){
				apple.hide();
			}//hide the apple after winning
		}


		return new_apple_coord;
	}//end of placeApple method



	/**
	 *Method Name: getWin
	 *Purpose: return true if won the game
	 *@return boolean won 
	 */
	public boolean getWin(){
		//send the win message to the snake
		return won;
	}


	/**
	 *Method Name: showGameOver
	 *Purpose: show the game over text if crash the wall or eats itself
	 *only show gmae over if not won or crashing
	 */
	public void showGameOver(){

		if(checkNewGame){
			//prevent showing "game over" everytime user pressed the newgame button

			if(!won){
				//if user didn't win the game, show game over

			gameOverText.show();
			gameOverText.sendToFront();
			pauseText.hide();
			winText.hide();

			gameOver = true;
			won = false;
			}
		}
	}//end of showGameOver method



	/**
	 *Method Name: setNewGame
	 *Purpose:change the checkNewGame boolean in snake to control the text show
	 */
	public void setNewGame(){
		this.checkNewGame = true;
	}


	/**
	 *Method Name: getDimensions
	 *Purpose: return the dimensions of current canvas
	 *@return int dimension of the canvas
	 */
	public int getDimensions(){
		return this.dimensions;
	}


	/**
	 *Method Name: getAppleCoord
	 *Purpose: get the apple coord
	 *@return Coordinate appleCoord
	 */
	public Coordinate getAppleCoord(){
		return appleCoord;
	}


	/**
	 *Method Name: actionPerformed
	 *Purpose: to make canvas keep listening to the keycode
	 *@param ActionEvent evt only activate when user hit on the button
	 */
	public void actionPerformed(ActionEvent evt){
		canvas.requestFocusInWindow();
		//when restart the game, make key pressed still working properly

		if(evt.getSource().equals(newgame)){
		//if user pressed on the newgame button on canvas	

			gameOverText.hide();
			pauseText.hide();
			winText.hide();
			//hide all text objects

			if(score>highScore){
				highScore = score;
				highScoreLabel.setText(String.format(PA8Strings.HIGH_SCORE,
																												highScore));
				//update the high score
			}

			apple.removeFromCanvas();
			snake.removeAll();
			//calling method to remove all segments of the snake

			score = 0;
			gameOver = false;
			won = false;
			//set the game to the original view
			
			((JButton)evt.getSource()).removeActionListener(this);
			((JDrawingCanvas)canvas).removeKeyListener(this);
			//remove listeners from canvas
			
			begin();
		}
	}



	/**
	 *Method Name: keyPressed
	 *Purpose: get the keyboard codes that user pressed on
	 *@param KeyEvent e the keycode of the keyboard
	 */
	public void keyPressed(KeyEvent e){
		
		int keyCode = e.getKeyCode();
		//get the keycode
			
		if(keyCode == KeyEvent.VK_SPACE && check){
			//pause the game
			if((!gameOver)&&(!won)){
				//if game not over and user didn't win the game
				pauseText.show();
				winText.hide();
				gameOverText.hide();
				pauseText.sendToFront();
				check = false;
				//pressed once to make check to false
			}

		}else if((keyCode == KeyEvent.VK_SPACE) && (check == false)){
			if((!gameOver)&&(!won)){
				//if game didn't over and user didn't win

				pauseText.hide();
				gameOverText.hide();
				pauseText.hide();
				check = true;
			//second press will make the check to true, resume the game
			}
		}//end of press the space bar 
	}//end of keyPressed



	//since implementing keyListener, have to have those empty method
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}//end of class SnakeController
