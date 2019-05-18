import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Attack extends GameObject {
	private int vel = 0;
	private double r;
	private double d;
	private int expire;
	private double dmg;

	// this is the constructor for a melee attack;
	public Attack(int x, int y, int width, int height, int pWidth, int pHeight, int right, int down, int duration,double e,
			String s) {
		super(x, y, width, height, true, true, 1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks + duration;
		super.moveX(-pWidth / 2);// centers drawing on player
		super.moveY(-pHeight / 2);
		r = right;
		d = down;
		super.moveX(right * pWidth);// moves to where the player faces
		super.moveY(down * pHeight);
		vel = 0;
		dmg=e;
	}

	public Attack(int x, int y, int width, int height, int pWidth, int pHeight, double right, double down, int velocity,
			int duration, double e, String s) {
		super(x, y, width, height, true, true, 1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks + duration;
		super.moveX(-pWidth / 2);// centers drawing on player
		super.moveY(-pHeight / 2);
		r = right;
		d = down;
		vel = velocity;
		super.moveX(r * pWidth / 2);// moves to where the player faces
		super.moveY(d * pHeight / 2);
		dmg = e;
	}
	
	public Attack(int x, int y, int width, int height, int duration,int damage, String s) {
		super(x, y, width, height, true, true, 1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks + duration;
		vel = 0;
		dmg=damage;
	}

	@Override
	public boolean collides(GameObject other) {
		Rectangle r = super.getRect();
		Rectangle k = new Rectangle((int) (r.getX() + r.getWidth() / 10), (int) (r.getY() + r.getHeight() / 10),
				(int) (r.getWidth() * 8 / 10), (int) (r.getHeight() * 8 / 10));
		if (k.intersects(other.getRect())) {
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		double angle=0;
		if (!(r==0&&d==0)) {
			if (r==0)
				r=.0001;
			angle=-Math.atan(-d/r)+Math.PI/2;
		}
		if (r<0) {
			angle+=Math.PI;
		}
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage i = this.getImg();
		double ratio = ((double) HEIGHT) / i.getHeight();
		double shiftX = (this.WIDTH - i.getWidth() * ratio) / 2;
		g2d.rotate(angle, this.getCX(), this.getCY());
		g.drawImage(i, (int) (locX + shiftX), (int) locY, (int) (i.getWidth() * ratio), (int) HEIGHT, null);
		g2d.rotate(-angle, this.getCX(), this.getCY());
	}
	public double getDamage() {
		return dmg;
	}

	public void update() {
		super.moveX(r * vel);
		super.moveY(d * vel);
	}

	public boolean expire() {
		if (RPGGame.ticks > expire) {
			return true;
		}
		return false;
	}
	
	public void reflect() {
		vel=-Math.abs(vel);
	}
}
