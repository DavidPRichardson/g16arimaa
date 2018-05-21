package g16arimaa;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cat extends Piece{

	public Cat(int x, int y,int color) {
		super(x,y,color);
		strength=1;
		try {
			if(color==GOLD) {
				myimage = ImageIO.read(new File("cat_gold.png"));
			}
			else if(color==SILVER) {
				myimage = ImageIO.read(new File("cat_silver.png"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
