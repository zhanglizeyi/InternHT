/*
 *Author: Ze Li
 *
 *Project: 2048 grid game
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game2048 extends JPanel {

	static class Tile{
		int value;

		public Tile(){
			this(0);
		}

		public Tile(int num){
			value = num;
		}

		public boolean isEmpty(){
			return value == 0;
		}

		public Color getForeground(){
			return value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
		}
		public Color getBackGround(){
			switch(value){
				case 2: return new Color(0xF9F5F2); //white  
				case 4: return new Color(0xF9EBDB); //pink-white
				case 8: return new Color(0xF9C8A4); //pink-orange
				case 16: return new Color(0xF9A97D); //orange
				case 32: return new Color(0xF98C50); //pink-red
				case 64: return new Color(0xF96348); //red
				case 128: return new Color(0xF9F5A2); //yellow
				case 256: return new Color(0xF6F975);
				case 512: return new Color(0xF9F741);
				case 1024: return new Color(0xF9DE0C);
				case 2048: return new Color(0xF9CC00);
			}
			return new Color(0xcdc1b4);
		}
	}	

	/*
		size of tile
		margin
	*/	

	private static final int tileSize = 86;
	private static final int tileMargin = 16;

	//generic type of tile, boolean check, int score
	private Tile[] tile;
	boolean isWin;
	boolean isLose;
	int score;

	/* constructor */
	public Game2048(){
		setPreferredSize(new Dimension(500, 500));
		setFocusable(true);
		//key adapter
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){

				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					resetGame();
				
				if(!isCanMove())
					isLose = true;
		
				if(!isLose && !isWin){	
					switch (e.getKeyCode()){
						case KeyEvent.VK_UP:
							up_key();
							break;
						case KeyEvent.VK_DOWN:
							down_key();
							break;
						case KeyEvent.VK_LEFT:
							left_key();
							break;
						case KeyEvent.VK_RIGHT:
							right_key();
							break;
					}
				}

				if(!isWin && !isCanMove())
					isLose = true;

				repaint();
			}
		});
		resetGame();
	}

	private void tile(){
		List<Tile> l = availableSpace();
		if(!availableSpace().isEmpty()){
			int index = (int)(Math.random() * l.size() % l.size());
			Tile emptyTime = l.get(index);
			emptyTime.value = Math.random() < 0.9 ? 2 : 4;
		}
	}

	private List<Tile> availableSpace(){
		final List<Tile> l = new ArrayList<>(16);
		for(Tile t : tile){
			if(t.isEmpty())
				l.add(t);
		}
		return l;
	}

	//key 
	public void up_key(){
		System.out.println("up");
		tile = rotate(270);
		left_key();
		tile = rotate(90);
	}

	public void down_key(){
		System.out.println("down");
		tile = rotate(90);
		left_key();
		tile = rotate(270);
	}

	public void left_key(){
		System.out.println("left");
		boolean needAddTile = false;
		for(int i=0; i<4; i++){
			Tile[] tList = getLine(i);
			Tile[] merge = merge(move(tList));
			setLine(i, merge);
			if(!needAddTile && !compare(tList, merge)){
				needAddTile = true;
			}
		}
		if(needAddTile) 
			tile();
	}

	public void right_key(){
		System.out.println("right");
		tile = rotate(180);
		left_key();
		tile = rotate(180);
	}

	//tile position
	private Tile tileAt(int x, int y){
		System.out.println("tile at position x: "+x+" y: "+ y);
		return tile[x+y*4];
	}

	//re-init game
	public void resetGame(){
		score = 0;
		isWin = isLose = false;
		//init tiles
		tile = new Tile[4 * 4];
		for(int i=0; i<tile.length; i++)
			tile[i] = new Tile();
		tile();
		tile();
	}

	//check tile move ability
	public boolean isCanMove(){
		if(!(availableSpace().size() == 0))
			return true;
		
		for(int x=0; x<4; x++){
			for(int y=0; y<4; y++){
				Tile t = tileAt(x,y);
				if((x<3 && t.value == tileAt(x+1, y).value) || (x<3 && t.value == tileAt(x+1, y).value)){
					return true;
				}
			}
		}
		return false;
	}

	//compare two tile lists 
	private boolean compare(Tile[] t1, Tile[] t2){
		
		if(t1 == t2) return true;
		else if(t1.length != t2.length) return false;

		for(int i=0; i<t1.length; i++){
			if(t1[i].value != t2[i].value)
				return false;
		}
		return true;
	}

	//rotation of grid
	private Tile[] rotate(int angle){
		Tile[] newT = new Tile[4*4];
		int offsetX=3, offsetY=3;
		if(angle == 90)
			offsetY = 0;
		else if(angle == 270)
			offsetX = 0;
		
		double radius = Math.toRadians(angle);
		int cos = (int) Math.cos(radius);
		int sin = (int) Math.sin(radius);

		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				int newX = (i*cos)-(j*sin)+offsetX;
				int newY = (i*sin)+(j*cos)+offsetY;
				newT[(newX) + (newY) * 4] = tileAt(i,j);
			}
		}
		return newT;
	}

	private Tile[] move(Tile[] old){
		LinkedList<Tile> l = new LinkedList<>();
		for(int i=0; i<4; i++){
			if(!old[i].isEmpty())
				l.addLast(old[i]);
		}
		if(l.size() == 0)
			return old;
		else{
			Tile[] newT = new Tile[4];
			ensureSize(l, 4);
			for(int i=0; i<4; i++){
				newT[i] = l.removeFirst();
			}
			return newT;
		}
	}

	private Tile[] merge(Tile[] old){
		LinkedList<Tile> l = new LinkedList<>();
		for(int i=0; i<4 && !old[i].isEmpty(); i++){
			int num = old[i].value;
			if(i<3 && old[i].value == old[i+1].value){
				num *= 2;
				score += num;
				if(num == 2048) isWin = true;
				i++;	
			}
			l.add(new Tile(num));
		}

		if(l.size() == 0)
			return old;
		else{
			ensureSize(l,4);
			return l.toArray(new Tile[4]);
		}
	}

	private static void ensureSize(java.util.List<Tile> l, int s){
		while(l.size() != s){
			l.add(new Tile());
		}
	}

	private Tile[] getLine(int ind){
		Tile[] result = new Tile[4];
		for(int i=0; i<4; i++){
			result[i] = tileAt(i, ind);
		}
		return result;
	}

	private void setLine(int ind, Tile[] t){
		//obj, int srcPos, obj dest, int destPos, int length				
		System.arraycopy(t, 0, tile, ind*4, 4);
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(new Color(0xbbada0));
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				draw(g, tile[i+j*4], i, j);
			}
		}
	}

	private void draw(Graphics g, Tile t, int x, int y){
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		int value = t.value;
		int offX = x * (tileMargin + tileSize) + tileMargin;
		int offY = y * (tileMargin + tileSize) + tileMargin;
		g2D.setColor(t.getBackGround());
		//int x, y, width, heihgt, arcwidth, archeight
		g2D.fillRoundRect(offX, offY, tileSize, tileSize, 20, 20);
		g2D.setColor(t.getForeground());
		final int size = value < 100 ? 24 : value < 1000 ? 24 : 20;

		final Font font = new Font("Arial", Font.BOLD, size);
    	g2D.setFont(font);

    	String s = String.valueOf(value);
    	final FontMetrics fm = getFontMetrics(font);

    	final int w = fm.stringWidth(s);
    	final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

   		if (value != 0){
   			g2D.drawString(s, offX + (tileSize - w) / 2, offY + tileSize - (tileSize - h) / 2 - 2);
   		}

		if(isWin || isLose){
			g2D.setColor(new Color(255, 255, 255, 30));
			g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
			g2D.setColor(new Color(78,139,200));

			if(isWin){
				String won = "You Won!";
				FontMetrics m = getFontMetrics(font);
				int wid = m.stringWidth(won);
				int hgt = -(int) fm.getLineMetrics(won, g).getBaselineOffsets()[2];
				g2D.drawString(won, this.getWidth()/2-wid/2, this.getHeight()/2+hgt);
			}
			if(isLose){
				String game = "Game Over!";
				String lose = "You Lose!";
				FontMetrics m = getFontMetrics(font);				
				int hgt1 = -(int) fm.getLineMetrics(game, g).getBaselineOffsets()[2];
				int wid1 = m.stringWidth(game);
				int hgt2 = -(int) fm.getLineMetrics(lose, g).getBaselineOffsets()[2];
				int wid2 = m.stringWidth(lose);
				g2D.drawString(game, this.getWidth()/2-wid1/2, this.getHeight()/2-hgt1);
				g2D.drawString(lose, this.getWidth()/2-wid2/2, this.getHeight()/2-(hgt1+hgt2+5));
			}
	
			if(isWin||isLose){
				String p = "Press ESC to play AGAIN!";
				FontMetrics m = getFontMetrics(font);
				int wid = m.stringWidth(p);
				int hgt = -(int) fm.getLineMetrics(p, g).getBaselineOffsets()[2];
				g2D.drawString("Press ESC to play AGAIN!", this.getWidth()/2-wid/2, this.getHeight() - 40);
			}

		}
		
		String str = "Score: ";
		FontMetrics m = getFontMetrics(font);
		int wid = m.stringWidth(str);
		int hgt = -(int) fm.getLineMetrics(str, g).getBaselineOffsets()[2];
		g2D.setFont(new Font("Arial", Font.PLAIN, 18));
		g2D.drawString(str + score, this.getWidth()-(wid+40), this.getHeight()-hgt);

	}

	public static void main(String[] args){
		System.out.println("2048");
		
		JFrame game = new JFrame();
		game.setTitle("Game 2048");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(500, 500);
		game.setResizable(false);

		game.add(new Game2048());
		//relocate in center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		game.setLocation(dim.width/2-game.getSize().width/2, dim.height/2-game.getSize().height/2);
		//visible
		game.setVisible(true);

	}
}	