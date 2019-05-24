import java.awt.Graphics;

public class Chest extends GameObject {

	private double amountOfGold;
	private static Animation open = new Animation("chest_full_open", 3);

	public Chest(double x, double y, double gold) {
		super(x, y, 50, 50, true, true, 1, open.getFirst());
	}

	public void draw(Graphics g) {
		if (Math.abs(RPGGame.getPlayer().getRect().getCenterX() - this.getRect().getCenterX()) <= 150
				&& Math.abs(RPGGame.getPlayer().getRect().getCenterY() - this.getRect().getCenterY()) <= 150)
			super.draw(g, open.getSecond());
		else if(Math.abs(RPGGame.getPlayer().getRect().getCenterX() - this.getRect().getCenterX()) <= 150
				&& Math.abs(RPGGame.getPlayer().getRect().getCenterY() - this.getRect().getCenterY()) <= 150)
			super.draw(g,open.getThird());
		else {
			super.draw(g,open.getFirst());
		}
	}

}
