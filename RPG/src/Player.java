import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends GameObject {	
	//directions go clockwise, 0 is north, 7 is north-west
	private int direction = 0;
	private int cooldown = 0;
	private Animation a = new Animation();
	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, "player.png");
		a = new Animation();
	}
	public void setDirection(int dir) {
		direction = dir;
	}

	public boolean attack(int ticks) {
		if (cooldown>=ticks)
			return false;
		cooldown = ticks+100;
		System.out.println("attacked");
		return true;
		
	}
	
	

}
