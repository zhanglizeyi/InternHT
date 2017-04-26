
import objectdraw.*;
import java.awt.*;
import java.util.*;
import java.lang.Math;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 *Class Name: Snake
 *Creating the class Snake
 */
public class Snake extends ActiveObject implements KeyListener{

	private static final int GROW_BY = 1;
	//how much the Snake grows by when it eats an apple
	private static final int TWO = 2;
	//TWO for avoid hardcoding

	private int leftToGrow;
	//the number of cells the snake has left to grow
	private int size;
	//the diameter of each Snake Segment
	private int delay;
	//the delay between each pause in run
	private DrawingCanvas canvas;


	private Direction currentDir ;
	//which way the snake is going
	private boolean isRunning = false, pause = false, userPressed = false;
	//whether the game is activated or not
	private Coordinate nextApple;
	//the coordinate that snake needs to go in order to grow
	ArrayList<SnakeSegment> snake;
	//the snake is a collection of segments
	ArrayList<Coordinate> snakeCoord;
	//arraylist for all snake segments coordinates
	SnakeSegment head;
	//we need to know where the head is for apple eating and crashing
	SnakeController controller;
	private Coordinate coord;


	/**
	 *Constructor Name: Snake
	 *Purpose: construct the snake 
	 */
	public Snake(Coordinate coord, int size, int delay, DrawingCanvas canvas,
																					 SnakeController controller){
		this.coord = coord;
		this.size = size;
		this.delay = delay;
		this.canvas = canvas;
		this.controller = controller;
		//modify instance variables based on the parameter

		snake = new ArrayList<SnakeSegment>();
		snakeCoord = new ArrayList<Coordinate>();
		//make new arraylists to store snake segment and coordinates

		head = new SnakeSegment(coord, size, true, canvas);
		//construct the head 
		snake.add(head);
		//add head to the arraylist
		currentDir = Direction.RIGHT;
		//face right when start
		snakeCoord.add(coord);
		//first element in snakeCoord

		isRunning = true;
		pause = false;

		canvas.addKeyListener(this);
		//add key listener then start the active object
		start();	

	}//end of constructor Snake


	/**
	 *Method Name: getSnakeCoord()
	 *Purpose: get the snake coordinates
	 *@return ArrayList the snake's coordinate arraylist
	 */
	public ArrayList getSnakeCoord(){
		return this.snakeCoord;
	}//end of getSnakeCoord


	/**
	 *Method Name: setAppleCoord()
	 *Purpose: set nextApple's coordinate in controller
	 *@param Coordinate coord takes in the new coordinatess
	 */
	public void setAppleCoord(Coordinate coord){
		this.nextApple = coord;
	}//end of setAppleCoord



	/**
	 *Method Name: snakeGrow
	 *Purpose: grow the snake by adding one snake segment
	 *@param Coordinate coord takes in the location for new segment
	 */
	public void snakeGrow(Coordinate coord){

		this.coord = coord;
		int leftToGrow = GROW_BY;
		//for good object oriented design, use leftToGrow and GROW_BY

		for(int i = leftToGrow; i>0; i--){
			snake.add(new SnakeSegment(coord,size,false,canvas));
		}

		snakeCoord.add(coord);
		//add snake coordinates to the arraylist

	}//end of snakeGrow



	/**
	 *Method Name: removeAll
	 *Purpose: remove all snake segments from canvas
	 */
	public void removeAll(){

		int z = snake.size()-1;
		//store the current size of the snake
	
		for(int i=z; i>=0; i--){
			//loop through the whole snake	
			snake.get(i).removeBody();
			//remove the filled arc method in segment class
			snake.remove(i);
			//remove the segments from the arraylists
		}

		((JDrawingCanvas)canvas).removeKeyListener(this);
		//remove keylistener from canvas
		snake.add(head);
		//add the head again after remove all segments
	}



	/**
	 *SubClass Name: SnakeSegment
	 *Purpose: create a subclass SnakeSegment
	 */
	private class SnakeSegment{
		//visible apperance of the snake

		private final Color SNAKE_COLOR = Color.GREEN;
		//snake's color
		private FilledArc segment;
		//each part of snake is just an filled arc
		private Coordinate coord;
		//the location of each segments

		private static final double UP_ANGLE = 90 + 22.5;
		private static final double LEFT_ANGLE = 90 + UP_ANGLE;
		private static final double DOWN_ANGLE = 90 + LEFT_ANGLE;
		private static final double RIGHT_ANGLE = 90 + DOWN_ANGLE;
		private static final double HEAD_ARC_ANGLE = 360 - 45;
		private double BODY_ARC_ANGLE = 360;
		//head constants angle value

		private boolean isHead;
		//if true then create head, false then create body



		/**
	 	 *Constructor Name: SnakeSegment
	 	 *Purpose: construct the SnakeSegment 
	 	 *@param Coordinate coord 
	 	 *@param int size
	 	 *@param boolean isHead
	 	 *@param DrawingCanvas canvas
	   */
		public SnakeSegment(Coordinate coord, int size, boolean isHead,
															                DrawingCanvas canvas){
			this.coord = coord;
			this.isHead = isHead;
			//update the instance variable when pass in parameters
			
			if(isHead){
				//construct the head when isHead = true
				segment = new FilledArc(coord.getX(),coord.getY(),size,size,RIGHT_ANGLE,HEAD_ARC_ANGLE,canvas);
				segment.setColor(SNAKE_COLOR);
			}

			if(!isHead){
				//construct the body when isHead = false
				segment = new FilledArc(coord.getX(),coord.getY(),size,size,RIGHT_ANGLE,BODY_ARC_ANGLE,canvas);
				segment.setColor(SNAKE_COLOR);
			}

		}//end of constructor SnakeSegment


		/**
		 *Method Name: removeBody
		 *Purpose: remove the filled arc object from canvas when called
		 */
		public void removeBody(){
			segment.removeFromCanvas();
		}


		/**
		 *Method Name: moveTo
		 *Purpose: move the segment
		 *@param Coordinate coord , move the snake to that coordinate
		 */
		public void moveTo(Coordinate coord){

			segment.moveTo(coord.getX(), coord.getY());
			//move the segment according the coordinates that passed in

			snakeCoord.set(0,coord);
			//updating the head's coord

		}//end of moveTo method



		/**
		 *Method Name: getCoord
		 *Purpose: get the coordinate
		 *@return Coordinate return the coordinate 
		 */
		public Coordinate getCoord(){
			return this.coord;
		}//end of getCoord



		/**
		 *Method Name: setHeadAngle
		 *Purpose: set the different head direction based on keyboard
		 *@param String s takes in string to identify which direction 
		 */
		private void setHeadAngle(String s){
			if(s.equals("right")){
				segment.setStartAngle(RIGHT_ANGLE);
			}
			if(s.equals("left")){
			  segment.setStartAngle(LEFT_ANGLE);
			}
			if(s.equals("up")){
				segment.setStartAngle(UP_ANGLE);
			}
			if(s.equals("down")){
				segment.setStartAngle(DOWN_ANGLE);
			}
		}//end of method setHeadAngle

	}//end of class SnakeSegment



	/**
	 *Method Name: move
	 *Purpose: move the snake
	 *@return boolean moves when not crushed, return false when crushed
	 */
	private boolean move(){

		Coordinate oldCoord = new Coordinate((-1)*size,(-1)*size);
		Coordinate tmpCoord = new Coordinate((-1)*size,(-1)*size);
		Coordinate newCoord = new Coordinate((-1)*size,(-1)*size);
		//some temporary coordinates have to be initialized, keep it local

		int dimensions = controller.getDimensions();
		//get the dimensions from the controller

		int dim_plus_size = dimensions+size;
		int sub_Bound = (-1)*(TWO)*size;
		//some local variable to check if snake crash on the wall


		if(controller.getWin()){
			//if controller check there is no place left to put new apple, win
			isRunning = false;
			return isRunning;
		}


		if(currentDir == Direction.RIGHT){
			int z = snake.size();
			//get the size of snake
			head.setHeadAngle("right");
			//set the head orientation

			newCoord = new Coordinate(snake.get(0).getCoord().getX()+size,
													  				snake.get(0).getCoord().getY());
			//the new coordinate for the snake head to move

			tmpCoord = new Coordinate(snake.get(0).getCoord().getX(),
													          snake.get(0).getCoord().getY());
			//store the current head coordinates

			if(newCoord.getX() == (dim_plus_size)){
				//check if crash on wall, game over

				isRunning = false;
				controller.showGameOver();
				return isRunning;
			}

			snake.get(0).moveTo(newCoord);
			snake.get(0).coord = newCoord;
			//move the head if it is not crash on wall

			snakeCoord.set(0,newCoord);
			//snake head updating

			for(int i = 1; i<z; ++i){
				//loop through the body of the snake, then move them one by one
				oldCoord = new Coordinate(snake.get(i).getCoord().getX(),
																		snake.get(i).getCoord().getY());
				//store the previous segment coordinates

				snake.get(i).moveTo(tmpCoord);
				//move all segments one by one 

				snakeCoord.set(0,newCoord);
				//update the head's coordinate

				snakeCoord.set(i,tmpCoord);
				snake.get(i).coord = tmpCoord;
				//snake body moving

				if(newCoord.equals(tmpCoord)){
					//if head crash into the body

					isRunning = false;
					controller.showGameOver();
					return isRunning;
				}

				tmpCoord = oldCoord;
				//update body's coordinate

			}//end of moving snake body


			if(newCoord.equals(nextApple)){
				//if snake head coord equal to apple coord
				snakeGrow(oldCoord);
				//add an segment at the end of the snake
				nextApple = controller.placeApple(canvas);
				//place a new apple on canvas
				}		

		}//end of Direction.RIGHT



		if(currentDir == Direction.LEFT){

			int z = snake.size();
			//get the snake size
			head.setHeadAngle("left");
			//set the head orientation

			newCoord = new Coordinate(snake.get(0).getCoord().getX()-size,
                                    snake.get(0).getCoord().getY());
			//calculate the new coordinate for snake head
      tmpCoord = new Coordinate(snake.get(0).getCoord().getX(),
                           snake.get(0).getCoord().getY());
      //store the current coordinate for snake head

      if(newCoord.getX() == sub_Bound){
      	//check if crush on wall
				isRunning = false;
				controller.showGameOver();
				return isRunning;
			}

      snake.get(0).moveTo(newCoord);
      snake.get(0).coord = newCoord;
      //move the head

			snakeCoord.set(0,newCoord);
			//snake head updating coordinate

			for(int i = 1; i<z; i++){
				//loop through snake body
				oldCoord = new Coordinate(snake.get(i).getCoord().getX(),
																		snake.get(i).getCoord().getY());
				//store the orginal coordinates

				snake.get(i).moveTo(tmpCoord);
				//move the segments one by one
				snakeCoord.set(0,newCoord);
				//update the head coordinates 

				snakeCoord.set(i,tmpCoord);
        snake.get(i).coord = tmpCoord;
        //snake body updating coordinates

				if(newCoord.equals(tmpCoord)){
					//if crash into the body
					isRunning = false;
					controller.showGameOver();
					return isRunning;
				}

        tmpCoord = oldCoord;
        //store the old coordinates for body to move
      }

			if(newCoord.equals(nextApple)){
				//check if eat the apple or not
				snakeGrow(oldCoord);
				nextApple = controller.placeApple(canvas);
			}	

		}//end of Direction.LEFT



		if(currentDir == Direction.UP){

			int z = snake.size();
			//get the snake size
			head.setHeadAngle("up");
			//head orientation changing

			newCoord = new Coordinate(snake.get(0).getCoord().getX(),
															snake.get(0).getCoord().getY()-size);
			//calculate the new head coordinate

			tmpCoord = new Coordinate(snake.get(0).getCoord().getX(),
                           snake.get(0).getCoord().getY());
			//store the head's coordinate

      if(newCoord.getY() == sub_Bound){
      	//check if crush on the wall
				isRunning = false;
				controller.showGameOver();
				return isRunning;
			}

			snake.get(0).moveTo(newCoord);
      snake.get(0).coord = newCoord;
			snakeCoord.set(0,newCoord);
			//update head's coordinate

			for(int i = 1; i<z; i++){
				//loop through the snake body
				oldCoord = new Coordinate(snake.get(i).getCoord().getX(),
																		snake.get(i).getCoord().getY());
				//store the body's old coord
        snake.get(i).moveTo(tmpCoord);
        snakeCoord.set(0,newCoord);
        //update coordinate after move

				snakeCoord.set(i,tmpCoord);
        snake.get(i).coord = tmpCoord;
        //snake body updating

				if(newCoord.equals(tmpCoord)){
					//check if crash into the body
					isRunning = false;
					controller.showGameOver();
					return isRunning;
				}

        tmpCoord = oldCoord;
        //store the coordinates
      }

				if(newCoord.equals(nextApple)){
				//if eats an apple
					snakeGrow(oldCoord);
					nextApple = controller.placeApple(canvas);	
					//place new apple	
				}

		}//end of Direction.UP



		if(currentDir == Direction.DOWN){

			int z = snake.size();
			//snake size
			head.setHeadAngle("down");
			//head orientation

			newCoord = new Coordinate(snake.get(0).getCoord().getX(),
																	snake.get(0).getCoord().getY()+size);
			//calculate the new coordinates for head
			tmpCoord = new Coordinate(snake.get(0).getCoord().getX(),
                           snake.get(0).getCoord().getY());
			//store the current coordinate of the head

      if(newCoord.getY() == dim_plus_size){
      	//check if crash on the wall
				isRunning = false;
				controller.showGameOver();
				return isRunning;
			}

      snake.get(0).moveTo(newCoord);
      snake.get(0).coord = newCoord;
			snakeCoord.set(0,newCoord);		
			//snake head updating

			for(int i = 1; i<z; ++i){
				oldCoord = new Coordinate(snake.get(i).getCoord().getX(),
																	snake.get(i).getCoord().getY());
				//getting the body's coordinate

        snake.get(i).moveTo(tmpCoord);
        snake.get(i).coord = tmpCoord;
        snakeCoord.set(0,newCoord);
        //update the head
				snakeCoord.set(i,tmpCoord);
				//snake body updating

				if(newCoord.equals(tmpCoord)){
					//check if crash on the body
					isRunning = false;
					controller.showGameOver();
					return isRunning;
				}
        tmpCoord = oldCoord;
        //store the body coord for next segment
      }

			if(newCoord.equals(nextApple)){
				//check if eats an apple or not
				snakeGrow(oldCoord);
				nextApple = controller.placeApple(canvas);						
			}		

		}//end of Direction.UP			

		return isRunning;
	}//end of move Method
	



	/**
	 *Method Name: keyPressed 
	 *Purpose: implements keyListener then respond to keyPressed
	 *@param KeyEvent e takes in key code
	 */
	public void keyPressed(KeyEvent e){

		int keyCode = e.getKeyCode(); 
		//get the key code when user pressed on key board

		userPressed = true;
		controller.setNewGame();
		//make checkNewGame in controller to true after moving the snake

		if(keyCode == KeyEvent.VK_RIGHT){
			//press the right key
			userPressed = true;
			currentDir = Direction.RIGHT;
		}
		if(keyCode == KeyEvent.VK_LEFT){
			//press the left key
			userPressed = true;
			currentDir = Direction.LEFT;
		}
		if(keyCode == KeyEvent.VK_UP){
			//press the up key
			userPressed = true;
			currentDir = Direction.UP;
		}
		if(keyCode == KeyEvent.VK_DOWN){
			//press the down key
			userPressed = true;
			currentDir = Direction.DOWN;
		}
		if(keyCode == KeyEvent.VK_SPACE){
			//pause the game by press on space bar
			//pause start with false

			if(!pause){
				//if not pausing
				pause = true;
				//change to true after press once
			}else{
				pause = false;
				//second press turn to false
			}
		}

	}//end of class keyPressed




	/**
	 *Method Name: run
	 *Purpose: keeps active objects moving
	 */
	public void run(){

			while(isRunning){
				//while move method return true

				if(userPressed && !pause){
					//if user not pausing the game
					move();
					//move the snake
		  	}
				pause(delay);
				//pause sometime for animation 
			}
	}


	//have to have those method since implements keyListener
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}//end of class Snake