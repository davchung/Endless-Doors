import java.awt.image.BufferedImage;

public class MoveableObject extends GameObject{
	//private field variables
	private int damage = 5;
	protected double pastX, pastY; // how much they moved in their last frame, to move to MO
	protected double right, down; //current direction they're facing, or something, to move to MO
	private int cooldown = 0;// to move to MO

	public MoveableObject(double x, double y, double width, double height , int startingHealth,
			BufferedImage b) {super(x, y, width, height, false, false, startingHealth, b);
	}

	public int getDamage() {
		return this.damage;
	}
	public double getPastX() {
		return this.pastX;
	}
	public double getPastY() {
		return this.pastY;
	}
	
	public double getRight() {
		return this.right;
	}
	public double getDown() {
		return this.down;
	}
	public void setRight(double r) {
		this.right = r;
	}
	public void setDown(double d) {
		this.down = d;
	}

	public int getCooldown() {
		return this.cooldown;
	}
	public void setCooldown(int cd) {
		this.cooldown = cd;
	}
	public boolean attack(int ticks) {
		if (cooldown >= RPGGame.ticks)
			return false;
		cooldown = RPGGame.ticks + ticks;
		return true;
	}
	@Override
	public void moveX(double howMuch) {
		super.moveX(howMuch);
		pastX = howMuch;
	}
	@Override
	public void moveY(double howMuch) {
		super.moveY(howMuch);
		pastY=howMuch;
	}
	@Override
	public void hit(int damage) {
		super.hit(damage);
		if (RPGGame.ticks>super.getHittable())
			cooldown+=26;
	}

}
