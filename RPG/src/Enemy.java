import java.awt.Graphics;

public class Enemy extends GameObject {
	
	private static Animation run = new Animation("big_demon_run", 4);
	private static Animation idle = new Animation("big_demon_idle", 4);
	private double eSpeed = 1.5; // enemy speed

	// constructor #1 for Enemy
	public Enemy(double x, double y, double w, double h) {
		super(x, y, w, h, true, idle.getFirst()); // uses GameObject's constructor #2
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
			dx = (int) super.WIDTH;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(run.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
					(int) super.HEIGHT + 20, null);
			return;
		}
		g.drawImage(idle.getImage(), (int) super.locX + dx, (int) super.locY - 20, (int) (r * super.WIDTH),
				(int) super.HEIGHT + 20, null);
	}

	// these methods are for movement
	@Override
	public void moveX(double howMuch) {
		if (RPGGame.ticks > getHittable())
			super.moveX(howMuch);
	}
	@Override
	public void moveY(double howMuch) {
		if (RPGGame.ticks > getHittable())
			super.moveY(howMuch);
	}
	protected void autoMove() {
		// makes the enemy follow the player
			RPGGame.getObjects().remove(this);
			double x = 0, y = 0;
			x = (RPGGame.getPlayer().getCX() - this.getCX());
			y = (RPGGame.getPlayer().getCY() - this.getCY());

			double mag = Math.sqrt(x * x + y * y);
			x = this.getSpeed() * x / mag;
			y = this.getSpeed() * y / mag;
			if (this.collides(RPGGame.getPlayer())) {
				if (this.attack(RPGGame.ticks)) {
					RPGGame.setEnemyAttack(new Attack((int) this.getLocX() + 25, (int) this.getLocY() + 25, (int) x, (int) y, RPGGame.ticks, "flame.png"));
				}
			}
			this.moveX(x);
			this.moveY(y);
			System.out.println(x);
			if (Math.abs(x)<eSpeed/8)
				x=1;
			this.setRight(x);
			this.setDown(y);
			for (GameObject i : RPGGame.getObjects()) {
				if (this.collides(i) && (i instanceof Wall)) {
					double dx = this.getCX() - i.getCX();
					double dy = this.getCY() - i.getCY();
					double m = Math.sqrt(dx * dx + dy * dy);
					dx = eSpeed * dx / m;
					dy = eSpeed * dy / m;
					this.moveX(dx);
					this.moveY(dy);
				}

			}
			RPGGame.getObjects().add(this);

		
	}
}
