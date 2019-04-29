import java.util.ArrayList;

public class Player extends GameObject {
	//directions go clockwise, 0 is north, 7 is north-west
	private int direction = 0;
	public Player(double x, double y, double w, double h) {
		super(x, y, w, h, "player.png");
	}

	public void attack(ArrayList<String> keys, int ticks) {

	}

}
