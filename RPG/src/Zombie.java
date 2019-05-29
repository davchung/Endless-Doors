import java.awt.Graphics;

public class Zombie extends Enemy {

	public Zombie(double x, double y, int level) {
		super(x, y, 40, 60, level,null);
		run = new Animation("zombie_run", 4);
		idle = new Animation("zombie_idle", 4);
		moveDown= 0;
		addLength = 0;
		moveLeft = 10;
	}

	@Override
	public void draw(Graphics g) {
		if (super.getHittable() > EndlessDoorsGame.ticks) {
			drawDamage(g);
		} else {
			betterDraw(g);
		}
	}

	// these methods are for movement
	@Override
	public void moveX(double howMuch) {
		if (EndlessDoorsGame.ticks > getHittable()) {
			super.moveX(howMuch);
		} else {
			super.moveX(-howMuch / 3);
		}
	}

	@Override
	public void moveY(double howMuch) {
		if (EndlessDoorsGame.ticks > getHittable()) {
			super.moveY(howMuch);
		} else {
			super.moveY(-howMuch / 3);
		}
	}

	// the zombie has a possibility of being slower
	@Override
	public void autoMove() {
		EndlessDoorsGame.getObjects().remove(this);
		double x = 0, y = 0;
		x = (EndlessDoorsGame.getPlayer().getCX() - this.getCX());
		y = (EndlessDoorsGame.getPlayer().getCY() - this.getCY());

		double mag = Math.sqrt(x * x + y * y);
		x = this.getSpeed() * x / mag;
		y = this.getSpeed() * y / mag;
		this.moveX(x / GameObject.randInt(1, 2)); // this makes the zombie possibly be slower
		this.moveY(y / GameObject.randInt(1, 2));
		while (this.collides(EndlessDoorsGame.getPlayer())) {
			this.moveX(-x / 10);
			this.moveY(-y / 10);
			EndlessDoorsGame.getPlayer().hit(this.getDamage());
		}
		this.setRight(x);
		if (Math.abs(x) < getSpeed() / 8)
			this.setRight(1);
		this.setDown(y);
		wallCollision();
		EndlessDoorsGame.getObjects().add(this);
	}
	@Override
	public Animation getRun() {
		return run;
	}
	@Override
	public Animation getIdle() {
		return idle;
	}

	@Override
	public String toString() {
		return "Zombie";
	}
}
