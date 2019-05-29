import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Swampy extends Enemy {

	public Swampy(double x, double y, int level) {
		super(x, y, 45, 55, level,null);
		run = new Animation("swampy_run", 4);
		idle = new Animation("swampy_idle", 4);
		moveDown= 0;
		addLength = 0;
		moveLeft = 10;
		this.speed=1;
		this.increaseMaxHealth(this.health);
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

	@Override
	public void autoMove() {
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
			RPGGame.getPlayer().hit(this.getDamage());
		}
		this.setRight(x);
		if (Math.abs(x) < getSpeed() / 8)
			this.setRight(1);
		this.setDown(y);
		wallCollision();
		if (this.canAttack()) {
			RPGGame.setEnemyAttack(new Attack((int) (getLocX()), (int) (getLocY()+HEIGHT/2), WIDTH, HEIGHT/2, 400,this.getDamage()/3, "sprites/goo.png"));
			this.addCooldown(20);
		}
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
		return "Swampy";
	}
}
