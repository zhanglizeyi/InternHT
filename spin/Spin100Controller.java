/*
 *Name: Meiyi He
 *Login: cs11fawq
 *PID: A91041817
 *Date: Nov.09.2015
 *File Name: Spin100Controller.java
 *This file defines a controller that handle click events for spinwheel
 */

import objectdraw.*;
import java.awt.*;
import java.lang.Math;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *Class Name: Spin100Controller
 *Creating the class Spin100Controller
 */
public class Spin100Controller extends WindowController 
															 implements ActionListener{

	private static final int NUM_OF_IMAGES = 20;
	private static final double IMAGE_WIDTH = 185;
	private static final double IMAGE_HEIGHT = 99;
	private static final int MIN_TICKS = 50; 
 	private static final int WHEEL_DELAY = 1;
	private static final int WHEEL_Y_COORD = 10;
	private static final int GAME_OVER_Y_COORD = 5;
	private int P1_SCORE, P2_SCORE;
  
	private static final int CANVAS_WIDTH = 840;
	private static final int CANVAS_HEIGHT = 660;
	private static final int HALF_CANVAS_WIDTH = 420;

	private static final int ONE = 1;
	private static final int TWO = 2;
  private static final int THREE = 3;
	private static final int FIVE = 5;
	private static final int WIN_TEXT_SIZE = 16;
	private static final double WHEEL1_X_COORD =(HALF_CANVAS_WIDTH-
																											IMAGE_WIDTH)/2;
	private static final double WHEEL2_X_COORD=HALF_CANVAS_WIDTH+
																											WHEEL1_X_COORD;
  private static final int DISPLAY = 5;//display 5 images
  private static final int BEST_SCORE = 100;
	private Spin100Wheel wheel1;
	private Spin100Wheel wheel2;
	//construct spin100wheel object

	private Location wheel1Loc;
	private double wheel1x, wheel1y;
	private Location wheel2Loc;
	private double wheel2x, wheel2y;
	//location for spinwheel objects

	private Spin100Controller controller;
	//controller

	private JPanel northPanel, southPanel, buttonPanel;
	//JPanel

	private JButton clickP1Button,clickP2Button,
									finishP1Button,finishP2Button,
                                 restartButton;
	//JButtons


	private JLabel northText1,p1ScoreText, p2ScoreText;
	//JLabel
  private static final int FONT_24 = 24;
  private static final int FONT_16 = 16;

	private boolean p1_finish,p2_finish,restart;
	//boolean value to control the buttons

	private int wheel1ticks,wheel2ticks;
	//int value for wheel ticks

	private int score;
	private	int p1score,p2score;
	private int index;

	private int[] IMG_INDEX = {55,95,50,85,30,65,10,45,70,25,90,5,100,15,
																										  80,35,60,20,40,75};
	//image array index 


	private Text p1WinText, p2WinText, tieText;
	//text objects
	private double p1Win_X,p2Win_X,tieText_X;
	//text objects coordinates X
  private static final Color WINNER_COLOR = Color.GREEN;
  //text color green
  private static final Color TIE_COLOR = Color.BLUE;
  //text color blue

	private Location arrow1Loc,arrow2Loc;
	//RED arrow locations
	private static final double ARROW1_X = IMAGE_WIDTH+30;
	private static final double ARROW2_X = HALF_CANVAS_WIDTH + ARROW1_X;
	private static final double ARROW_Y = WHEEL_Y_COORD+ 
																				(5/2)*IMAGE_HEIGHT -20;
	//RED arrow coordinates
	private FilledArc arrow1,arrow2;
	//RED arrow objects
	private static final Color ARROW_COLOR = Color.RED;
	//RED arrow color
	private static final double ARROW_SIZE = 150;
	private static final double START_ANGLE = -15;
	private static final double ARC_ANGLE = 30;
	//arrow size and angles


	/**
	 *Main Method
	 *Purpose: set the canvas
	 */
	public static void main(String[] args){
    new Acme.MainFrame(new Spin100Controller(), args, 
    																   CANVAS_WIDTH, CANVAS_HEIGHT);
  }


  /**
   *Method Name: begin
   *Purpose: initialize the program 
   */
	public void begin(){

		northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(THREE,ONE));
		//set the header JPanel

		northText1 = new JLabel("Spin100",SwingConstants.CENTER);
		northText1.setFont(new Font("Comic Sans MS", Font.BOLD,FONT_24));
		p1ScoreText = new JLabel("Player1's score is " + P1_SCORE,
																					  SwingConstants.CENTER);
		p1ScoreText.setFont(new Font("Serif", Font.BOLD,FONT_16));
		p2ScoreText = new JLabel("Player2's score is " + P2_SCORE,
																						SwingConstants.CENTER);
		p2ScoreText.setFont(new Font("Serif", Font.BOLD,FONT_16));
		//set the Header Label text, font size, font type

		northPanel.add(northText1,BorderLayout.NORTH);
		northPanel.add(p1ScoreText,BorderLayout.CENTER);
		northPanel.add(p2ScoreText,BorderLayout.SOUTH);
		//add the Text JLabel on the Header Panel

		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(ONE,FIVE));
		buttonPanel = new JPanel();
		//set the button panels

		clickP1Button = new JButton("Click to Spin P1");
		finishP1Button = new JButton("Finish Player1");
		restartButton = new JButton("Restart");
		clickP2Button = new JButton("Click to Spin P2");
		finishP2Button = new JButton("Finish Player2");
		//JButtons

		southPanel.add(clickP1Button);
		southPanel.add(finishP1Button);
		southPanel.add(restartButton);
		southPanel.add(clickP2Button);
		southPanel.add(finishP2Button);
		//add buttons on the Panel

		Container contentPane = getContentPane();
		contentPane.add(northPanel, BorderLayout.NORTH);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		//add JPanel onto the content Pane

		contentPane.validate();//validate the content Pane

		
    Image[] WHEEL_IMG = new Image[ NUM_OF_IMAGES ];
		for(int i=0; i<NUM_OF_IMAGES; i++){
			WHEEL_IMG[i] = getImage("Big_Wheel-"+IMG_INDEX[i]+".png");
		}//get the image from library


	 	wheel1x = WHEEL1_X_COORD;
	 	wheel1y = WHEEL_Y_COORD;
  	wheel1Loc = new Location(wheel1x, wheel1y);
		wheel1 = new Spin100Wheel(WHEEL_IMG, WHEEL_DELAY, wheel1Loc,
            	 									 		this, MIN_TICKS, canvas, index);
		//construct wheel1


		arrow1Loc = new Location(ARROW1_X,ARROW_Y);
		arrow1 = new FilledArc(arrow1Loc,ARROW_SIZE,ARROW_SIZE,
																			START_ANGLE,ARC_ANGLE,canvas);
		arrow1.setColor(ARROW_COLOR);
		arrow1.sendToFront();
		//Red arrow for wheel1



		p1WinText = new Text("P1 Winner",0,0,canvas);
		p1WinText.setFontSize(WIN_TEXT_SIZE);
		p1WinText.setBold(true);
    p1WinText.setColor(WINNER_COLOR);
		p1Win_X =	(WHEEL1_X_COORD - p1WinText.getWidth())/TWO;
		p1WinText.moveTo(p1Win_X, GAME_OVER_Y_COORD);
		p1WinText.hide();
		//winning text for player1


		wheel2x = WHEEL2_X_COORD;
		wheel2y = WHEEL_Y_COORD;
		wheel2Loc = new Location(wheel2x,wheel2y);
		wheel2 = new Spin100Wheel(WHEEL_IMG, WHEEL_DELAY, wheel2Loc,
																		this, MIN_TICKS, canvas, index);
		//construct wheel2


		arrow2Loc = new Location(ARROW2_X,ARROW_Y);
		arrow2 = new FilledArc(arrow2Loc,ARROW_SIZE,ARROW_SIZE,
																			START_ANGLE,ARC_ANGLE,canvas);
		arrow2.setColor(ARROW_COLOR);
		arrow2.sendToFront();
		//Red Arrow for wheel2



		p2WinText = new Text("P2 Winner",0,0,canvas);
		p2WinText.setFontSize(WIN_TEXT_SIZE);
		p2WinText.setBold(true);
    p2WinText.setColor(WINNER_COLOR);
		p2Win_X = WHEEL2_X_COORD + p1Win_X + IMAGE_WIDTH;
		p2WinText.moveTo(p2Win_X, GAME_OVER_Y_COORD);
		p2WinText.hide();
		//winning text for player 2



		tieText = new Text("Tie",0,0,canvas);
		tieText.setFontSize(WIN_TEXT_SIZE);
		tieText.setBold(true);
    tieText.setColor(TIE_COLOR);
		tieText_X = (CANVAS_WIDTH/TWO-tieText.getWidth()/TWO);
		tieText.moveTo(tieText_X, GAME_OVER_Y_COORD);
		tieText.hide();
		//tie text of the game
 

		clickP1Button.addActionListener(this);
		clickP2Button.addActionListener(this);
		restartButton.addActionListener(this);
		finishP1Button.addActionListener(this);
		finishP2Button.addActionListener(this);
		//add listener on buttons
	}


	/**
	 *Method Name: getScore
	 *Purpose: get the score from the index
	 *@param x int value to take in index
	 *@return this.score to set the scores
	 */
	private synchronized int getScore(int x){
		this.score = IMG_INDEX[x];
		return this.score;
	}


	/**
	 *Method Name: setScore
	 *Purpose: set the score of PlayerScoreText
	 *no return, no param
	 */
	public synchronized void setScore(){
		p1ScoreText.setText("Player1's score is " + p1score);
    p2ScoreText.setText("Player2's score is " + p2score);
    //set score after the animation finished
	}



	/**
	 *Method Name: actionPerformed
	 *Purpose: sense the click on the buttons
	 *@param evt , check which button is being clicked
	 *no return value
	 */
	public void actionPerformed( ActionEvent evt ){
	
    if(evt.getSource() == clickP1Button){
			//if click on P1 button
			
			if((p1_finish == false)||restart){
				wheel1.setTicks();
				p1score = p1score + getScore(wheel1.getIndex());
				//update p1 score

				if(p1score>=BEST_SCORE){
					clickP1Button.setEnabled(false);
					finishP1Button.setEnabled(false);
					//grey out p1 buttons	
					p1_finish = true;			
				}//if p1 score >= 100, stop immediately
			}

		}//end of click start p1 button


    if(evt.getSource() == finishP1Button){
			//if click on finish P1 button
			
      if(p1_finish == false){
				p1_finish = true;
				clickP1Button.setEnabled(false);
				finishP1Button.setEnabled(false);
				//grey out p1 buttons
			}
		}//end of finish p1 button


		if(p1_finish == true){
			//only when p1 finish, p2 able to start

			if(evt.getSource() == clickP2Button){
				//if click on p2 start button

				if(p2_finish == false){	
					wheel2.setTicks();
					p2score = p2score + getScore(wheel2.getIndex());
          //update the p2 score

          if(p1score>BEST_SCORE && p2score>BEST_SCORE){
						tieText.show();
            clickP2Button.setEnabled(false); 
            finishP2Button.setEnabled(false);
            p2_finish = true;
				  }//tie the game 
	   		
          if(p2score>BEST_SCORE){
						clickP2Button.setEnabled(false);
						finishP2Button.setEnabled(false);	
						p2_finish = true;		
            
            if(p1score<BEST_SCORE){
              p1WinText.show();
            }//if p2>100 but p1<100, p1 win

				  }//if p2>= 100, grey out the buttons
			  }
			}//end of click start p2 button



			if(evt.getSource() == finishP2Button){
				//if p2 click the finish buttons

        if(p2_finish == false){
					//grey out the p2 buttons if finish
					p2_finish = true;
					clickP2Button.setEnabled(false);
					finishP2Button.setEnabled(false);
				}

				
        if(p1score == p2score){
					tieText.show();
          //tie the game
				}else if(p1score>BEST_SCORE && p2score>BEST_SCORE){
          tieText.show();
          //when both over 100
        }else if(p1score>BEST_SCORE && p2score<BEST_SCORE){
          p2WinText.show();
          //when p1>100 but p2<100
        }else if(p1score<BEST_SCORE && p2score>BEST_SCORE){
          p1WinText.show();
          //when p1<100 but p2>100
        }else if(p1score<BEST_SCORE && p2score<BEST_SCORE){
          //when both p1 p2 <100
          
          if((BEST_SCORE-p1score)<(BEST_SCORE-p2score)){
            p1WinText.show();
            //when p1 close to 100
          }else{
            p2WinText.show();
            //when p2 close to 100
          }
        
        }//end of all possible game over conditions
			}//end of finish p2 buttons
		
    }



		if(p1_finish == true && p2_finish == true){
		//if both finish, able to restart the game

			if(evt.getSource() == restartButton){
				p1score = 0;
				p2score = 0;
				p1_finish = false;
				p2_finish = false;
				restart = true;
				//reset all values to be 0 and boolean to false

				clickP1Button.setEnabled(true);
				finishP1Button.setEnabled(true);
				clickP2Button.setEnabled(true);
				finishP2Button.setEnabled(true);
				//reEnable the buttons 

				wheel1.resetWheel();
				wheel2.resetWheel();
				//reconstruct the wheels

				p1WinText.hide();
				p2WinText.hide();
				tieText.hide();
				//hide all texts

				p1ScoreText.setText("Player1's score is " + p1score);
				p2ScoreText.setText("Player2's score is " + p2score);
				//set the score on JLabel back to 0

			}//end of click restart buttons
		}//end of if both finish conditions

	}//end of action performed

}

