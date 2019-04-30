import java.util.ArrayList;

public class Player extends GameObject {
	//directions go clockwise, 0 is north, 7 is north-west
	private int direction = 0;
	private int cooldown = 0;
	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, "player.png");
	}
	public void setDirection(int dir) {
		direction = dir;
	}
	public int getDirection() {
		return direction;
	}

	public void attack(int right, int down, int ticks) {
		
		
	}

}
