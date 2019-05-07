import java.awt.image.BufferedImage;

public class Environment extends GameObject{
	public Environment(double x, double y, double w, double h, BufferedImage i, boolean through) {
		super(x, y, w, h, i, through);
	}
	public Environment(double x, double y, double w, double h, String s, boolean through) {
		super(x, y, w, h, s, through);
	}
}
