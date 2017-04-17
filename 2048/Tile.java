import java.util.*;
import java.awt.*;

public class Tile{
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
			case 2: return new Color(255, 245, 238); //white  
			case 4: return new Color(255, 245, 239); //pink-white
			case 8: return new Color(255, 228, 225); //pink-orange
			case 16: return new Color(252, 157, 154); //orange
			case 32: return new Color(254, 67, 101); //pink-red
			case 64: return new Color(255, 105, 180); //red
			case 128: return new Color(127, 255, 212); //yellow
			case 256: return new Color(127, 252, 197);
			case 512: return new Color(127, 248, 180);
			case 1024: return new Color(127, 245, 167);
			case 2048: return new Color(127, 245, 150);
		}
		return new Color(0xcdc1b4);
	}
}	
