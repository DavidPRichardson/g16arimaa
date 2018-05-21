package g16arimaa;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Camel extends Piece{

	public Camel(int x, int y,int color) {
		super(x,y,color);
		strength=4;
		try {
			if(color==GOLD) {
				myimage = ImageIO.read(new File("camel_gold.png"));
			}
			else if(color==SILVER) {
				myimage = ImageIO.read(new File("camel_silver.png"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
