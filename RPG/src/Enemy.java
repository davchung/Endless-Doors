import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy extends MoveableObject {
	protected Animation run;
	protected Animation idle;
	protected int moveDown = -20;
	protected int addLength = 0;
	protected int moveLeft = 10;
	protected boolean moving= true;

	private static int baseHealth = 0;
	// enemy speed

	// constructor #1 for Enemy
	public Enemy(double x, double y, int w, int h, int level, BufferedImage i) {
		super(x, y, w, h, baseHealth + level * 10, i); 
	}
	protected void setRun(Animation running) {
		run = running;
	}
	protected void setIdle(Animation idling) {
		idle = idling;
	}
	
	protected void drawDamage(Graphics g) {
//		betterDraw(g);
//		if (true)
//			return;
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH +moveLeft*2;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(GameObject.tint(run.getImage(),Color.WHITE), (int) super.locX + dx -moveLeft, (int) super.locY + moveDown,
					(int) (r * (super.WIDTH+moveLeft*2 )), (int) super.HEIGHT+addLength, null);
			return;
		}
		g.drawImage(GameObject.tint(idle.getImage(), Color.WHITE), (int) super.locX + dx-moveLeft , (int) super.locY +moveDown,
				(int) (r * (super.WIDTH+moveLeft*2 )), (int) super.HEIGHT+addLength, null);
	}

	protected void betterDraw(Graphics g) {
		double r = getRight() / Math.abs(getRight());
		int dx = 0;
		if (r < 0)
			dx = (int) super.WIDTH +moveLeft*2;
		if (moving) {
			g.drawImage(run.getImage(), (int) super.locX + dx-moveLeft , (int) super.locY+moveDown ,
					(int) (r * (super.WIDTH+moveLeft*2 )), (int) super.HEIGHT+addLength, null);
			return;
		}
		g.drawImage(idle.getImage(), (int) super.locX + dx-moveLeft , (int) super.locY+moveDown ,
				(int) (r * (super.WIDTH+moveLeft*2 )), (int) super.HEIGHT+addLength, null);
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

	public Animation getIdle() {
		return null;
	}

	public Animation getRun() {
		return null;
	}
	
	

}
