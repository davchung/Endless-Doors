import java.awt.*;

public class Chest extends GameObject{
	
	private double amountOfGold;
	
	public Chest(double x, double y, double w, double h, double gold) {
		super(x, y, w, h, "chest.png");
		amountOfGold = gold;
	}
	
}
