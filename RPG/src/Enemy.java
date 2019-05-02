import java.awt.*;

public class Enemy extends GameObject {
	
	private double speed = 0.5;
	public double getSpeed() { return this.speed; }

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png");
	}

	protected void moveTowardPlayer(double x, double y) {
		moveX(x);
		moveY(y);
		
	}

}
