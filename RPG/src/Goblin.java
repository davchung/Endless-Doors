import java.awt.Graphics;

public class Goblin extends Enemy {
	private static Animation run = new Animation("goblin_run", 4);
	private static Animation idle = new Animation("goblin_idle", 4);

	public Goblin(double x, double y, int level) {
		super(x, y, 40, 40, level,idle.getFirst());
		
	}

	@Override
	public void draw(Graphics g) {
		if (super.getHittable() > RPGGame.ticks) {
			//super.draw(g, damage);
		} else {
			betterDraw(g);
		}
	}

	private void betterDraw(Graphics g) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH +20;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(getRun().getImage(), (int) super.locX + dx-10 , (int) super.locY-20 ,
					(int) (r * (super.WIDTH+20 )), (int) super.HEIGHT+20, null);
			return;
		}
		g.drawImage(getIdle().getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
				(int) super.HEIGHT + 20, null);	
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
		return "Goblin";
	}
}
