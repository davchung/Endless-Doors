import java.awt.image.BufferedImage;

public class Environment extends GameObject{
	
	// constructor #1 for Environment
	public Environment(double x, double y, double w, double h, boolean through, String s) {
<<<<<<< HEAD
<<<<<<< HEAD
		super(x, y, w, h, through, Integer.MAX_VALUE, s); // uses GameObject's constructor #1
=======
		super(x, y, w, h, through, s); // uses GameObject's constructor #1
>>>>>>> parent of bb8568c... Adjusted health for all gameobjects
=======
		super(x, y, w, h, through, s); // uses GameObject's constructor #1
>>>>>>> parent of bb8568c... Adjusted health for all gameobjects
	}
	
	// constructor #2 for Environment
	public Environment(double x, double y, double w, double h, boolean through, BufferedImage i) {
<<<<<<< HEAD
<<<<<<< HEAD
		super(x, y, w, h, through, Integer.MAX_VALUE, i); // uses GameObject's constructor #2
=======
		super(x, y, w, h, through, i); // uses GameObject's constructor #2
>>>>>>> parent of bb8568c... Adjusted health for all gameobjects
=======
		super(x, y, w, h, through, i); // uses GameObject's constructor #2
>>>>>>> parent of bb8568c... Adjusted health for all gameobjects
	}
}
