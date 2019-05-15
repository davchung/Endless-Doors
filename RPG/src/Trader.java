
public class Trader extends GameObject{
	
	public Trader() {
		super(950, 625, 50, 75, true, false, Integer.MAX_VALUE, "trader.png");
		System.out.println("Trader health: " + super.getHealth());
	}
}
