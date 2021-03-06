package g16arimaa;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Horse extends Piece{

	public Horse(int x, int y,int color) {
		super(x,y,color);
		strength=3;
		try {
			if(color==GOLD) {
				myimage = ImageIO.read(new File("horse_gold.png"));
			}
			else if(color==SILVER) {
				myimage = ImageIO.read(new File("horse_silver.png"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
