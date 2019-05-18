
public class Knight extends Player {
	int canMove;
	int canSpecial;

	public Knight(double x, double y, int w, int h) {
		super(x, y, w, h);
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
	

}
