package g16arimaa;

import java.awt.Graphics;
import java.awt.Image;

public class Piece {

	int x,y,strength;
	boolean isPresent;
	Image myimage;
	int color;
	final int GOLD=1, SILVER=2;
	
	//constructor
	public Piece(int x,int y,int color) {
		this.x=x;
		this.y=y;
		this.color=color;
		isPresent=true;
	}
	
	//paint component
	public void paintPiece(Graphics g, int gridsize) {
		g.drawImage(myimage,(int)x*gridsize,y*gridsize,null);
	}

	//getters and setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStrength() {
		return strength;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void removed() {
		isPresent=false;
	}
	
	public int getColor() {
		return color;
	}
	
	

}
