import java.awt.Graphics;

public class Goblin extends Enemy {
	private static Animation run = new Animation("goblin_run", 4);
	private static Animation idle = new Animation("goblin_idle", 4);
	private static double faceR=Math.random()-.5;
	private static double faceD=Math.random()-.5;

	public Goblin(double x, double y, int level) {
		super(x, y, 40, 40, level,idle.getFirst());
		speed=2.5;
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

	// the goblin has a possibility of being faster
	@Override
	public void autoMove() {
		RPGGame.getObjects().remove(this);
		double x = 0, y = 0;
		x = faceR;
		y = faceD;
		double mag = Math.sqrt(x * x + y * y);
		x = this.getSpeed() * x / mag;
		y = this.getSpeed() * y / mag;
		this.moveX(x); // this makes the goblin possibly be faster
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
				if(!(i.equals(RPGGame.getPlayer()))){
					pickRandomDirection();
				}
			}

		}
		for (GameObject i : RPGGame.getObjects()) {
			if (!this.equals(i) && this.collides(i) && (!i.throughable)) {
				wallCollision();
			}
		}

	}
	
	private void pickRandomDirection() {
		faceR= (int)(Math.random()*101)-50;
		faceD= (int)(Math.random()*101)-50;
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
