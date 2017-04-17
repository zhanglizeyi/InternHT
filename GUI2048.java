import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GUI2048{
	/*
		size of tile
		margin
	*/	
	private static final int tileSize = 100;
	private static final int tileMargin = 16;

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
}