import java.awt.Graphics;

public class Archer extends Player {
	int canMove;
	int canSpecial;
	private static Animation run = new Animation("elf_m_run",4);
	private static Animation idle = new Animation("elf_m_idle",4);

	public Archer(double x, double y) {
		super(x, y, 50, 50,idle.getFirst());
	}
	
	public Attack getAttack() {
		Attack arrow = new Arrow((int)this.getCX(), (int)this.getCY(), 10, 10, this.WIDTH, this.HEIGHT,RPGGame.lastR,RPGGame.lastD,10,200,this.getDamage()/4, "sprites/items/larger_arrow.png");
		canMove = RPGGame.ticks;
		return arrow;
	}
	public Attack getSpecial() {
		Grapple grapple = new Grapple((int)this.getCX(), (int)this.getCY(), 10,10, this.WIDTH, this.HEIGHT,RPGGame.lastR,RPGGame.lastD,15,200,this.getDamage()/4, "sprites/items/lavish_gold_sword.png");
		return grapple;
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

	@Override
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
