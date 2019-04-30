import java.awt.*;

public class Enemy extends GameObject {

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png");
	}

	protected void moveTowardPlayer(double x, double y) {
		moveX(x);
		moveY(y);
		
	}

}
