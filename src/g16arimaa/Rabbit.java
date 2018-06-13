package g16arimaa;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rabbit extends Piece{

	public Rabbit(int x, int y,int color) {
		super(x,y,color);//original constructor
		strength=0;
		try {//set the images based on its color
			if(color==GOLD) {
				myimage = ImageIO.read(new File("mouse_gold.png"));
			}
			else if(color==SILVER) {
				myimage = ImageIO.read(new File("mouse_silver.png"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//override the possible moves
	public boolean possibleMoves(int moved_xgrid,int moved_ygrid,boolean check_backward) {
		if(Math.abs(moved_xgrid - getX()) + Math.abs(moved_ygrid - getY()) == 1&&!check_backward) {//if check_backward is false, 
																							//check if the moved grid is next to piece
			return true;
		}
		//if it is moving backward
		if(color==GOLD&&moved_ygrid-getY()==1) {
			return false;
		}
		if(color==SILVER&&moved_ygrid-getY()==-1) {
			return false;
		}
		//if it is not moving backward, check if the moved grid is next to the piece
		if(Math.abs(moved_xgrid - getX()) + Math.abs(moved_ygrid - getY()) == 1) {
			return true;
		}
		return false;
	}
	
	//rabbit's original method, returns if it is on the other side
	public boolean isOtherSide() {
		if(color==GOLD&&getY()==0) {
			return true;
		}
		if(color==SILVER&&getY()==7) {
			return true;
		}
		return false;
	}

}
