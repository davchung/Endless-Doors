import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Demon extends Enemy {
	private BufferedImage damage;
	public Demon(double x, double y, double w, double h, int level) {
		super(x, y, w, h, level);
		damage = super.getImage("sprites/big_demon_damage.png");
	}

	@Override
	public void draw(Graphics g) {
		if(super.getHittable()>RPGGame.ticks) {
			super.draw(g,damage);
		}else {
			super.draw(g);
		}
	}
	@Override
	public void autoMove() {
		// makes the enemy follow the player
		RPGGame.getObjects().remove(this);
		double x = 0, y = 0;
		x = (RPGGame.getKnight().getCX() - this.getCX());
		y = (RPGGame.getKnight().getCY() - this.getCY());

		double mag = Math.sqrt(x * x + y * y);
		x = this.getSpeed() * x / mag;
		y = this.getSpeed() * y / mag;
		this.moveX(x);
		this.moveY(y);
		while (this.collides(RPGGame.getKnight())) {
			this.moveX(-x/10);
			this.moveY(-y/10);
		}
		this.setRight(x);
		if (Math.abs(x) < getSpeed() / 8)
			this.setRight(1);
		this.setDown(y);
		super.wallCollision();
		if (this.attack(80)) {
			RPGGame.setEnemyAttack(new Attack((int) this.getLocX() + (int)super.WIDTH/2, (int) this.getLocY() + (int)super.HEIGHT/2, (int) x, (int) y, RPGGame.ticks, "flame.png"));
		}
		RPGGame.getObjects().add(this);
	}
}
