import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Demon extends Enemy {
	private BufferedImage damage;

	public Demon(double x, double y, int w, int h, int level) {
		super(x, y, w, h, level);
		damage = super.getImage("sprites/big_demon_damage.png");
	}

	@Override
	public void draw(Graphics g) {
		if (super.getHittable() > RPGGame.ticks) {
			super.draw(g, damage);
		} else {
			super.draw(g);
		}
	}

	// these methods are for movement
	@Override
	public void moveX(double howMuch) {
		if (RPGGame.ticks > getHittable()) {
			super.moveX(howMuch);
		} else {
			super.moveX(-howMuch / 3);
		}
	}

	@Override
	public void moveY(double howMuch) {
		if (RPGGame.ticks > getHittable()) {
			super.moveY(howMuch);
		} else {
			super.moveY(-howMuch / 3);
		}
	}

	@Override
	public void autoMove() {
		// makes the enemy follow the player
		RPGGame.getObjects().remove(this);
		double x = 0, y = 0;
		x = (RPGGame.getPlayer().getCX() - this.getCX());
		y = (RPGGame.getPlayer().getCY() - this.getCY());

		double mag = Math.sqrt(x * x + y * y);
		x = this.getSpeed() * x / mag;
		y = this.getSpeed() * y / mag;
		this.moveX(x);
		this.moveY(y);
		while (this.collides(RPGGame.getPlayer())) {
			this.moveX(-x / 10);
			this.moveY(-y / 10);
			RPGGame.getPlayer().hit(10);
		}
		this.setRight(x);
		if (Math.abs(x) < getSpeed() / 8)
			this.setRight(1);
		this.setDown(y);
		wallCollision();
		if (this.canMove(300)) {
			RPGGame.setEnemyAttack(new Attack((int) getCX(), (int) getCY(), WIDTH * 3 / 4, HEIGHT * 3 / 4, WIDTH,
					HEIGHT, x, y, 3, 500, "Sprites/fireball.png"));
		}
		RPGGame.getObjects().add(this);
	}

	@Override
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

	@Override
	public String toString() {
		return "Demon";
	}

}
