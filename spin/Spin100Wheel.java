
import objectdraw.*;
import java.awt.*;
import java.lang.Math;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 *Class Name: Spin100Wheel
 *Creating the class Spin100Wheel
 */
public class Spin100Wheel extends ActiveObject
													implements ActionListener{

	private static final int NUM_OF_IMAGES = 20;
	private static final int IMG_ARRAY = 19;
	private VisibleImage[] spinImg = new VisibleImage[NUM_OF_IMAGES];
	private Image[] pics = new Image[NUM_OF_IMAGES];
	private Spin100Wheel wheel;

	private int delay;//delay time to show the animation
	private Location topNumLoc;
	private Spin100Controller controller;
	private int minTicks;
	private DrawingCanvas canvas;
	private static final int DISPLAY = 5;//only display 5 images on canvas
	private static final int DISPLAY_ARRAY = 4;
	private static final double IMAGE_WIDTH = 185;
	private static final double IMAGE_HEIGHT = 99;

	private Location[] displayLoc = new Location[DISPLAY];
	private double displayLocX,displayLocy;//display the image

	private int ticks;//how many time it should spins
	private RandomDoubleGenerator randomNum;
	private double HALF_RANDOM = 0.65;//before 0.65 increment, vice versa 
	private double RANDOM_STEP = 0.05;//magic num to add ticks

	private static final int DELAY1 = 1;
	private static final int DELAY5 = 5;
	private static final int DELAY20 = 20;//increase by 1 when 1-20
	private static final int DELAY200 = 200;//increase by 5 when 20-200
	private static final int DELAY500 = 500;//increase by 20 when 200-500
	private static final int MAX_TICKS = 13;//max num of ticks to add

	private int scoreIndex;
	private int[] IMG_INDEX = {55,95,50,85,30,65,10,45,70,25,90,5,100,15,
																										80,35,60,20,40,75};
																										//image index
	
	/**
	 *Constructor Name: Spin100Wheel
	 *Purpose: construct the spin wheel 
	 */
	public Spin100Wheel(Image[] pics, int delay, Location topNumLoc,
		Spin100Controller controller, int minTicks, DrawingCanvas canvas, 
																											int scoreIndex){

		this.pics = pics;
		this.canvas = canvas;
		this.scoreIndex = scoreIndex;
		this.delay = delay;
		this.controller = controller;
		this.minTicks = minTicks;
		this.topNumLoc = topNumLoc;
		//get the value when construct the object


		for(int i=0; i<DISPLAY; i++){
			displayLocX = topNumLoc.getX();
			displayLocy = topNumLoc.getY() + i*IMAGE_HEIGHT;
			displayLoc[i] = new Location(displayLocX, displayLocy);
		}//display the original spin wheel when begin 


		for(int i=0; i<1; i++){

			int j = i % NUM_OF_IMAGES;
			
      for(int k=DISPLAY_ARRAY; k>=0 ;k--,j++){ 
				//k < 5, display 5 images

				if(j>IMG_ARRAY){
					//if larger than 19, change to 0 to increase
					j=0;
				}

		 		spinImg[k] = new VisibleImage(pics[j], displayLoc[k],canvas);
		 		//construct the visible Image array

			}//end of inner for Loop
		}//end of outer for Loop

		start();
		//start the animation(run)
	
  }//end of constructor


	/**
	 *Method Name: actionPerformed
	 *Do nothing
	 */
	public void actionPerformed(ActionEvent evt){}



	/**
	 *Method Name: getTicks
	 *Purpose: get the tick by random a double 
	 *@return int , return the corresponding ticks increments value
	 *no param
	 */
	private synchronized int getTicks(){
		
		RandomDoubleGenerator randomNum = new RandomDoubleGenerator(0,1);
		double num = randomNum.nextValue();
		//generate a double from 0 - 1
		
    int result = 0;//start with 0, maximum is 13

		if(num<HALF_RANDOM){
			result = (int)(num/RANDOM_STEP);
      //if the random double from 0-0.65, return from 1 to 13 
		}else{
			result = MAX_TICKS - (int)((num/RANDOM_STEP)-MAX_TICKS);
      //if between 0.65 to 1, return from 13 to 6
		}

		return result;
	
  }//end of getTicks




	/**
	 *Method Name: setTicks
	 *Purpose: set the ticks in controller
	 *No param, no return
	 */
	public synchronized void setTicks(){
		this.ticks = minTicks + getTicks();
	}//end of setTicks



	/**
	 *Method Name: getIndex
	 *Purpose: get the stopping image index based on ticks 
	 *@return int , index of the image
	 */
	public synchronized int getIndex(){
			
		int l = 0;
	  l= (this.ticks) % NUM_OF_IMAGES + 1;
    //use the tick number to calculate the index

		if(l>IMG_ARRAY){
	 		l= 0;
	 	}//set back to 0 if over 19
		
    return l;
	}//end of getIndex



	/**
	 *Method Name: resetWheel
	 *Purpose: reset the wheel as the start up page
	 *No param, no return
	 */
	public synchronized void resetWheel(){

		for(int i=0; i<1; i++){
			int j = i % NUM_OF_IMAGES;

			for(int k=DISPLAY_ARRAY; k>=0 ;k--,j++){ 
        //k < 5 to display
				if(j>IMG_ARRAY){
					//if larger than 19, change to 0 to increase
					j=0;
				}
		 		spinImg[k].setImage(pics[j]);
				}//end of inner for Loop
			}//end of outer for Loop

	}//end of resetWheel


	/**
	 *Method Name: run
	 *Purpose: spin the wheel
	 *no param, no return
	 */
	public void run(){ 

		while(true){	
			//forever loop to keep running
			for(int i=0; i<ticks; i++){ 
					
				int j = i%NUM_OF_IMAGES;

				for(int k=DISPLAY_ARRAY; k>=0 ;k--,j++){ 
					//display 5 images
					
					if(j == NUM_OF_IMAGES){
						j=0;
					}
						
					spinImg[k].setImage(pics[j]);
					//set the image 

				}//end of inner for Loop

				if(delay>=DELAY1 && delay<DELAY20){
					delay++;
				}else if(delay>=DELAY20 && delay<DELAY200){
					delay+=DELAY5;
				}else if(delay>=DELAY200 && delay<DELAY500){
					delay+=DELAY20;
				}//increase delay time correspondingly

				if(i == ticks-1){
					controller.setScore();
					//set score method to update player's score
				}

				pause(delay);//pause some time to see animation
	  	}//end of outer for Loop
		
			ticks = 0;
			delay = 1;
			//set the delay and tick back to original value
			pause(delay);	

	 }//end of infinite while loop
	}//end of run method
}//end of constructor
