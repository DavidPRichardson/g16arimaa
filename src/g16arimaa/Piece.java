package g16arimaa;

import java.awt.Graphics;
import java.awt.Image;

public class Piece {

	int x,y,strength;
	boolean isPresent;
	Image myimage;
	
	//constructor
	public Piece(int x,int y) {
		this.x=x;
		this.y=y;
		isPresent=true;
	}
	
	//paint component
	public void paintPiece(Graphics g) {
		
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
	
	

}
