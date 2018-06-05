package g16arimaa;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rabbit extends Piece{

	public Rabbit(int x, int y,int color) {
		super(x,y,color);
		strength=0;
		try {
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
	
	public boolean possibleMoves(int moved_xgrid,int moved_ygrid,boolean check_backward) {
		if(Math.abs(moved_xgrid - getX()) + Math.abs(moved_ygrid - getY()) == 1&&!check_backward) {
			return true;
		}
		if(color==GOLD&&moved_ygrid-getY()==1) {
			return false;
		}
		if(color==SILVER&&moved_ygrid-getY()==-1) {
			return false;
		}
		if(Math.abs(moved_xgrid - getX()) + Math.abs(moved_ygrid - getY()) == 1) {
			return true;
		}
		return false;
	}
	
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
