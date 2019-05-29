import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Demon extends Enemy {
	private static Animation run = new Animation("big_demon_run", 4);
	private static Animation idle = new Animation("big_demon_idle", 4);
	private BufferedImage dmg;

	public Demon(double x, double y, int level) {
		super(x, y, 100, 100, level,idle.getFirst());
		dmg = super.getImage("sprites/big_demon_damage.png");
		this.increaseMaxHealth(this.health*.5);
		damage+=level*2;
	}

	@Override
	public void draw(Graphics g) {
		if (super.getHittable() > RPGGame.ticks) {
			betterDraw(g, dmg);
		} else {
			betterDraw(g);
		}
	}
	@Override
	public Animation getRun() {
		return run;
	}
	@Override
	public Animation getIdle() {
		return idle;
	}
	
	public void betterDraw(Graphics g) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH + 40;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(getRun().getImage(), (int) super.locX + dx - 20, (int) super.locY - 40,
					(int) (r * (super.WIDTH + 40)), (int) super.HEIGHT + 40, null);
			return;
		}
		g.drawImage(getIdle().getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
				(int) super.HEIGHT + 20, null);
	}
	public void betterDraw(Graphics g, BufferedImage i) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH + 40;
		g.drawImage(i, (int) super.locX + dx - 20, (int) super.locY - 40, (int) (r * (super.WIDTH + 40)),
				(int) super.HEIGHT + 40, null);
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
			RPGGame.getPlayer().hit(this.getDamage());
		}
		this.setRight(x);
		if (Math.abs(x) < getSpeed() / 8)
			this.setRight(1);
		this.setDown(y);
		wallCollision();
		if (this.canAttack()) {
			RPGGame.setEnemyAttack(new Attack((int) getCX(), (int) getCY(), WIDTH * 3 / 4, HEIGHT * 3 / 4, WIDTH,
					HEIGHT, x, y, 3, 500,this.getDamage(), "sprites/fireball.png"));		
			this.addCooldown(300);
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
