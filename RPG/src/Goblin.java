import java.awt.Graphics;

public class Goblin extends Enemy {
	private double faceR=Math.random()-.5;
	private double faceD=Math.random()-.5;

	public Goblin(double x, double y, int level) {
		super(x, y, 40, 40, level,null);
		speed=2.5;
		run = new Animation("goblin_run", 4);
		idle = new Animation("goblin_idle", 4);
		moveDown= -20;
		addLength = 20;
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

	// the goblin has a possibility of being faster
	@Override
	public void autoMove() {
		EndlessDoorsGame.getObjects().remove(this);
		double x = 0, y = 0;
		x = faceR;
		y = faceD;
		double mag = Math.sqrt(x * x + y * y);
		x = this.getSpeed() * x / mag;
		y = this.getSpeed() * y / mag;
		this.moveX(x); // this makes the goblin possibly be faster
		this.moveY(y);
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
	protected void wallCollision() {
		for (GameObject i : EndlessDoorsGame.getObjects()) {
			if (!this.equals(i) && this.collides(i) && (!i.throughable)) {
				if(!(i.equals(EndlessDoorsGame.getPlayer()))){
					pickRandomDirection();
				}
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
		for (GameObject i : EndlessDoorsGame.getObjects()) {
			if (!this.equals(i) && this.collides(i) && (!i.throughable)) {
				wallCollision();
			}
		}

	}
	
	private void pickRandomDirection() {
		int choice = (int)(Math.random()*4);
		if (choice ==0) {
			faceR = (EndlessDoorsGame.getPlayer().getCX() - this.getCX());
			faceD = (EndlessDoorsGame.getPlayer().getCY() - this.getCY()); 
		}else {
			faceR= (int)(Math.random()*101)-50;
			faceD= (int)(Math.random()*101)-50;
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

	@Override
	public String toString() {
		return "Goblin";
	}
}
