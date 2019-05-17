import java.awt.Graphics;

public class Coin extends GameObject {
	private static Animation spin= new Animation("coin", 4); 
	
	public Coin(double x, double y, int gold) {
		super(x, y, 50, 50, true, true, gold, spin.getFirst());

	}
	public void draw(Graphics g) {
		super.draw(g, spin.getImage());
	}

}
