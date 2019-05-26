
import java.awt.Graphics;

public class Wogol extends Enemy {


	public Wogol(double x, double y, int level) {
		super(x, y, 40, 60, level,null);
		run = new Animation("wogol_run", 4);
		idle = new Animation("wogol_idle", 4);
		moveDown= -20;
		addLength = 20;
		moveLeft = 10;
	}

	@Override
	public void draw(Graphics g) {
		if (super.getHittable() > RPGGame.ticks) {
			drawDamage(g);
		} else {
			betterDraw(g);
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

	// the Wogol now moves away from the player and can shoot
	@Override
	public void autoMove() {
		RPGGame.getObjects().remove(this);
		double x = 0, y = 0;
		x = -(RPGGame.getPlayer().getCX() - this.getCX());
		y = -(RPGGame.getPlayer().getCY() - this.getCY());
		double mag = Math.sqrt(x * x + y * y);
		x = this.getSpeed() * x / mag;
		y = this.getSpeed() * y / mag;
		if (GameObject.randInt(1, 2) == 1) { // this gives the Wogol the ability to shoot
			if (this.canAttack()) {
				RPGGame.setEnemyAttack(new Attack((int) getCX(), (int) getCY(), WIDTH * 3 / 4, HEIGHT * 3 / 4, WIDTH,
						HEIGHT, -x, -y, 3, 500, this.getDamage(), "Sprites/fireball_f2.png"));
				this.addCooldown(300);
			}
		}
		if (mag<250) {
			this.moveX(x);
			this.moveY(y);
			moving = true;
		} else {
		moving = false;
		}
		while (this.collides(RPGGame.getPlayer())) {
			this.moveX(-x / 10);
			this.moveY(-y / 10);
			RPGGame.getPlayer().hit(this.getDamage());
		}
		this.setRight(x);
		if (Math.abs(x) < getSpeed() / 8)
			this.setRight(1);
		this.setDown(y);
		wallCollision();
		RPGGame.getObjects().add(this);
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
		return "Wogol";
	}
}
