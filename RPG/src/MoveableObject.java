import java.awt.image.BufferedImage;

public class MoveableObject extends GameObject{
	//private field variables
	protected double damage = 5;
	protected double pastX, pastY; // how much they moved in their last frame
	protected double right, down; //current direction they're facing
	private int cooldown = 0;// to move to MO
	private double loss = 0;
	protected double speed = 1.5; 


	public MoveableObject(double x, double y, int width, int height , int startingHealth, BufferedImage b) {
		super(x, y, width, height, false, false, startingHealth, b);
	}
	public double getLoss() {
		return loss;
	}
	public double getSpeed() {
		return this.speed;
	}

	public void update() {
		if (RPGGame.ticks>super.getHittable()) {
			loss=0;
		}
	}
	public void addDamage(double amount) {
		damage+=amount;
	}
	public void setDamage(double d) {
		damage= d;
	}
	public double getDamage() {
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
	public boolean canAttack() {
		if (cooldown >= RPGGame.ticks)
			return false;
		return true;
	}
	public void addCooldown(int ticks) {
		cooldown=RPGGame.ticks+ticks;
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
	public void hit(double damage, int ID) {
		if (RPGGame.ticks>super.getHittable()) {
			//cooldown=26+RPGGame.ticks;
			loss = damage;
		}
		super.hit(damage, ID);
	}
	public void moveAway(GameObject objs) {
		double dx = this.getCX() - objs.getCX();
		double dy = this.getCY() - objs.getCY();
		double m = Math.sqrt(dx * dx + dy * dy);
		dx = this.getSpeed() * dx / m;
		dy = this.getSpeed() * dy / m;
		this.moveX(dx / 5);
		this.moveY(dy / 5);
	}

}
