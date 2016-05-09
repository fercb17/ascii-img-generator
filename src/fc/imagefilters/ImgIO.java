package fc.imagefilters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgIO {
	
	private ImgIO() {}
	
	public static BufferedImage importImage(String dir, String filename) {
		try {
			return ImageIO.read(new File(dir + "/" + filename));
		} catch (IOException e) {e.printStackTrace();}
		
		return null;
	}
	
	public static BufferedImage importImage(String file) {
		try {
			return ImageIO.read(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void exportImage(BufferedImage img, String dir, String filename) {
		try {
			ImageIO.write(img, "png", new File(dir + filename));
		} catch (IOException e) {e.printStackTrace();}
	}
}
