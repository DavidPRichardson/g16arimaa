package g16arimaa;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dog extends Piece{

	public Dog(int x, int y,int color) {
		super(x,y,color);
		strength=2;
		try {
			if(color==GOLD) {
				myimage = ImageIO.read(new File("dog_gold.png"));
			}
			else if(color==SILVER) {
				myimage = ImageIO.read(new File("dog_silver.png"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
