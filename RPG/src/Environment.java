import java.awt.image.BufferedImage;

public class Environment extends GameObject{
	
	// constructor #1 for Environment
	public Environment(double x, double y, double w, double h, boolean through, String s) {
		super(x, y, w, h, through, Integer.MAX_VALUE, s); // uses GameObject's constructor #1
	}
	
	// constructor #2 for Environment
	public Environment(double x, double y, double w, double h, boolean through, BufferedImage i) {
		super(x, y, w, h, through, Integer.MAX_VALUE, i); // uses GameObject's constructor #2
	}
}
