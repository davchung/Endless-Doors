import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Attack extends GameObject{
	private int vel = 0;
	private double r;
	double d;
	private int expire;
	//this is the constructor for a melee attack;
	public Attack (int x, int y, int width, int height,int pWidth,int pHeight, int right, int down, int duration,String s) {
		super(x ,y, width, height, true,true,1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks+duration;
		super.moveX(-width/2);//centers drawing on player
		super.moveY(-height/2);
		super.moveX(right*width);//moves to where the player faces
		super.moveY(down*height);
		vel = 0;
	}
	public Attack (int x, int y, int width, int height,int pWidth,int pHeight, double x2, double y2,int velocity, int duration,String s) {
		super(x ,y, width, height, true,true,1, s); // uses GameObject's constructor #1
		expire = RPGGame.ticks+duration;
		super.moveX(-width/2);//centers drawing on player
		super.moveY(-height/2);
		r=x2;
		d=y2;
		vel =velocity;
		super.moveX(x2*width/2);//moves to where the player faces
		super.moveY(y2*height/2);
		
	}
	@Override
	public boolean collides(GameObject other) {
		Rectangle r = super.getRect();
		Rectangle k = new Rectangle((int)(r.getX()+r.getWidth()/10),(int)(r.getY()+r.getHeight()/10),(int)(r.getWidth()*9/10),(int)(r.getHeight()*9/10));
		if (k.intersects(other.getRect())) {
			return true;
		}
		return false;
	}
	public void draw2 (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(Math.PI/2,this.getCX(),this.getCY());
		super.draw(g2d);
		g2d.rotate(-Math.PI/2,this.getCX(),this.getCY());
	}
	public void update() {
		super.moveX(r*vel);
		super.moveY(d*vel);
	}
	public boolean expire(){
		if (RPGGame.ticks>expire) {
			return true;
		}
		return false;
	}
}

