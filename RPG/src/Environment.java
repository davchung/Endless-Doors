import java.awt.*;

public class Environment extends GameObject{
	public Environment(double x, double y, double w, double h, Image i) {
		super(x, y, w, h, i);
	}
	public Environment(double x, double y, double w, double h, String s) {
		super(x, y, w, h, s);
	}
}
