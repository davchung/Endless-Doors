import java.awt.image.BufferedImage;

public abstract class Enemy extends MoveableObject {

	private static int baseHealth = 10;
	// enemy speed

	// constructor #1 for Enemy
	public Enemy(double x, double y, int w, int h, int level, BufferedImage i) {
		super(x, y, w, h, baseHealth + level * 10, i); // uses GameObject's constructor #2
	}

	// getters and setters are here


	public void autoMove() {
		// makes the enemy follow the player
		double x = 0, y = 0;
		if (this.getLocX() - RPGGame.getPlayer().getLocX() > 0) {
			x = -this.getSpeed();
		} else {
			x = this.getSpeed();
		}
		if (this.getLocY() - RPGGame.getPlayer().getLocY() > 0) {
			y = -this.getSpeed();
		} else {
			y = this.getSpeed();
		}

		moveX(x);
		moveY(y);
		wallCollision();
	}


	protected void wallCollision() {
		for (GameObject i : RPGGame.getObjects()) {
			if (!this.equals(i) && this.collides(i) && (!i.throughable)) {
				double dx = this.getCX() - i.getCX();
				double dy = this.getCY() - i.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = super.getSpeed() * dx / m;
				dy = super.getSpeed() * dy / m;
				super.moveX(dx / 5);
				super.moveY(dy / 5);
				if (!(i instanceof Enemy)) {
					i.hit(this.getDamage()/2);
				}
			}

		}
		for (GameObject i : RPGGame.getObjects()) {
			if (!this.equals(i) && this.collides(i) && (!i.throughable)) {
				wallCollision();
			}
		}

	}

	public Animation getIdle() {
		return null;
	}

	public Animation getRun() {
		return null;
	}

}
