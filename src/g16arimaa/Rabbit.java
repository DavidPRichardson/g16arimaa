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

}
