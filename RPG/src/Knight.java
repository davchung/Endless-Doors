import java.awt.Graphics;

public class Knight extends Player {
	int canMove;
	int canSpecial;
	private static Animation run = new Animation("knight_f_run",4);
	private static Animation idle = new Animation("knight_f_idle",4);

	public Knight(double x, double y) {
		super(x, y, 50, 50, idle.getFirst());
	}
	
	public Attack getAttack() {
		Attack sword = new Attack((int)this.getCX(), (int)this.getCY(), this.WIDTH, this.HEIGHT, this.WIDTH, this.HEIGHT,RPGGame.lastR,RPGGame.lastD,20,this.getDamage(), "sprites/items/lavish_gold_sword.png");
		canMove = RPGGame.ticks+20;
		return sword;
	}
	public Attack getSpecial() {
		Attack shield = new Shield((int)this.getLocX(), (int)this.getLocY(), this.WIDTH, this.HEIGHT,50);
		return shield;
	}
	
	@Override
	public void hit(double damage) {
		if (RPGGame.getSpecial().size()==0) {
			super.hit(damage);
		}
	}
	
	@Override
	public void hit(double damage, int gameID) {
		if (RPGGame.getSpecial().size()==0) {
			super.hit(damage,gameID);
		}
	}

	@Override
	public boolean canMove() {
		if (RPGGame.ticks>canMove)
			return true;
		return false;
	}

	@Override
	protected boolean canSpecial() {
		if (RPGGame.ticks>canSpecial)
			return true;
		return false;
	}

	@Override
	protected void addSpecialCooldown(int i) {
		canSpecial = RPGGame.ticks+i;
	}
	public void draw(Graphics g, int r) {
		int dx = 0; 
		if (r<0) 
			dx = (int)WIDTH;
		if (getDown() != 0 || getRight() != 0) {
			g.drawImage(run.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);
			return;
		}

		g.drawImage(idle.getImage(), (int)super.locX+dx, (int)super.locY-20, (int)(r*super.WIDTH), (int)super.HEIGHT+20, null);

	}
	

}
