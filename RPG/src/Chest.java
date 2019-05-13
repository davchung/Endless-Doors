
public class Chest extends GameObject{

	private double amountOfGold;

	public Chest(double x, double y, double w, double h, double gold) {
<<<<<<< HEAD
		super(x, y, w, h, false, Integer.MAX_VALUE, "chest.png");
=======
		super(x, y, w, h, false, "chest.png");
>>>>>>> parent of bb8568c... Adjusted health for all gameobjects
		setAmountOfGold(gold);
	}

	public double getAmountOfGold() {
		return amountOfGold;
	}

	public void setAmountOfGold(double amountOfGold) {
		this.amountOfGold = amountOfGold;
	}

}
