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

	// this is the constructor for a melee attack;
	public Attack(int x, int y, int width, int height, int pWidth, int pHeight, int right, int down, int duration,
			String s) {
		super(x, y, width, height, true, true, 1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks + duration;
		super.moveX(-width / 2);// centers drawing on player
		super.moveY(-height / 2);
		super.moveX(right * pWidth);// moves to where the player faces
		super.moveY(down * pHeight);
		vel = 0;
	}

	public Attack(int x, int y, int width, int height, int pWidth, int pHeight, double x2, double y2, int velocity,
			int duration, String s) {
		super(x, y, width, height, true, true, 1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks + duration;
		super.moveX(-width / 2);// centers drawing on player
		super.moveY(-height / 2);
		r = x2;
		d = y2;
		vel = velocity;
		super.moveX(x2 * pWidth / 2);// moves to where the player faces
		super.moveY(y2 * pHeight / 2);

	}

	@Override
	public boolean collides(GameObject other) {
		Rectangle r = super.getRect();
		Rectangle k = new Rectangle((int) (r.getX() + r.getWidth() / 10), (int) (r.getY() + r.getHeight() / 10),
				(int) (r.getWidth() * 9 / 10), (int) (r.getHeight() * 9 / 10));
		if (k.intersects(other.getRect())) {
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		double angle = Math.PI;
		System.out.println(Math.atan(-d/r));
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage i = this.getImg();
		double ratio = ((double) HEIGHT) / i.getHeight();
		double shiftX = (this.WIDTH - i.getWidth() * ratio) / 2;
		g2d.rotate(angle, this.getCX(), this.getCY());
		g.drawImage(i, (int) (locX + shiftX), (int) locY, (int) (i.getWidth() * ratio), (int) HEIGHT, null);
		g2d.rotate(-angle, this.getCX(), this.getCY());
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
}
