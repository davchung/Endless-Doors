import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Arrow extends Attack{

	public Arrow(int x, int y, int width, int height, int pWidth, int pHeight, double right, double down, int velocity,
			int duration, double e, String s) {
		super(x, y, width, height, pWidth, pHeight, right, down, velocity, duration, e, s);
	}
	
	@Override
	public void draw(Graphics g) {
		double angle = 0;
		if (!(r == 0 && d == 0)) {
			if (r == 0)
				r = .0001;
			angle = -Math.atan(-d / r) + Math.PI / 2;
		}
		if (r < 0) {
			angle += Math.PI;
		}
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage i = this.getImg();
		double ratio = (50) / i.getHeight();
		double shiftX = (this.WIDTH - i.getWidth() * ratio) / 2;
		g2d.rotate(angle, this.getCX(), this.getCY());
		g.drawImage(i, (int) (locX + shiftX), (int) locY, (int) (i.getWidth() * ratio), 50, null);
		g2d.rotate(-angle, this.getCX(), this.getCY());
	}


}
