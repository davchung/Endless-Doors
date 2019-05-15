import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy extends MoveableObject {

	private static Animation run = new Animation("big_demon_run", 4);
	private static Animation idle = new Animation("big_demon_idle", 4);
	private static int baseHealth = 10;
	private double eSpeed = 1.5; // enemy speed

	// constructor #1 for Enemy
	public Enemy(double x, double y, double w, double h, int level) {
		super(x, y, w, h, baseHealth + level * 10, idle.getFirst()); // uses GameObject's constructor #2
	}

	// getters and setters are here
	public double getSpeed() {
		return this.eSpeed;
	}

	@Override
	public void draw(Graphics g) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH + 40;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(run.getImage(), (int) super.locX + dx - 20, (int) super.locY - 40,
					(int) (r * (super.WIDTH + 40)), (int) super.HEIGHT + 40, null);
			return;
		}
		g.drawImage(idle.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
				(int) super.HEIGHT + 20, null);
	}

	@Override
	public void draw(Graphics g, BufferedImage i) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH + 40;
		g.drawImage(i, (int) super.locX + dx - 20, (int) super.locY - 40, (int) (r * (super.WIDTH + 40)),
				(int) super.HEIGHT + 40, null);
	}


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
		int runs = 0;
		for (GameObject i : RPGGame.getObjects()) {
			if (this.collides(i) && (!i.throughable)) {
				double dx = this.getCX() - i.getCX();
				double dy = this.getCY() - i.getCY();
				double m = Math.sqrt(dx * dx + dy * dy);
				dx = eSpeed * dx / m;
				dy = eSpeed * dy / m;
				super.moveX(dx / 10);
				super.moveY(dy / 10);
			}

		}
		for (GameObject i : RPGGame.getObjects()) {
			if (this.collides(i) && (!i.throughable) && runs < 100) {
				runs++;
				wallCollision();
			}
		}

	}

}
