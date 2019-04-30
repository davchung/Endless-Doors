import java.awt.*;
import java.awt.image.BufferedImage;

public class Environment extends GameObject{
	public Environment(double x, double y, double w, double h, BufferedImage i) {
		super(x, y, w, h, i);
	}
	public Environment(double x, double y, double w, double h, String s) {
		super(x, y, w, h, s);
	}
}
