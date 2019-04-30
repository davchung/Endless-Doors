import java.awt.*;

public class Enemy extends GameObject {

	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, "enemy.png");
		this.moveTowardPlayer();
	}

	private void moveTowardPlayer() {
		int xDirection = this.getLocX() - getPlayer().getLocX();
		moveX(1.0);
		moveY(1.0);
		
	}

}
