
public class Chest extends GameObject{

	private double amountOfGold;

	public Chest(double x, double y, double gold) {
		super(x, y, 50, 50, false, true, 1, "Sprites/chest_empty_open_anim_f0.png");
		setAmountOfGold(gold);
	}

	public double getAmountOfGold() {
		return amountOfGold;
	}

	public void setAmountOfGold(double amountOfGold) {
		this.amountOfGold = amountOfGold;
	}

}
