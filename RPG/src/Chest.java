import java.awt.*;

public class Chest extends GameObject{
	
	double amountOfGold;
	public Chest(double x, double y, double w, double h, double gold, String s) {
		super(x, y, w, h, s);
		amountOfGold = gold;
	}
	
}
