package fc.asciiart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgToAscii {

	public static void main(String[] args) {
		String dir = args[0];
		String fileName = args[1];
		String[] fn = fileName.split("\\.");
		String imgName = fn[0];
		
		BufferedImage img = getImage(dir, fileName);
		
		img = convertToAscii(img, new Color(0, 0, 0), 12, new String("abcdefghijklmnopqrstuvwxyz").split(""), true, false);
		//img = convertToAscii(img, new Color(15, 15, 15), 20, new String("+").split(""), true, false);
		//img = convertToAscii(img, new Color(15, 15, 15), 12, new String("abcdefghijklmnopqrstuvwxyz").split(""), false, true);
		
		//img = invertImage(img);
		exportImage(img, dir, imgName + "_ascii.png");
	}
	

	private static BufferedImage getImage(String dir, String filename) {
		try {
			return ImageIO.read(new File(dir + "/" + filename));
		} catch (IOException e) {e.printStackTrace();}
		
		return null;
	}

	private static BufferedImage convertToBW(BufferedImage img, boolean red, boolean green, boolean blue) {
		
		BufferedImage bwImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		for(int i = 0; i < img.getWidth(); i++) {
			for(int j = 0; j < img.getHeight(); j++){
				int rgba = img.getRGB(i, j);
				Color c = new Color(rgba);
				
				int sum = 0;
				int d = 0;
				for(int a = 0; a < 3; a++) {
					if(a == 0 && red)
					{
						sum += c.getRed();
						d++;
					}
					if(a == 1 && green)
					{
						sum += c.getGreen();
						d++;
					}
					if(a == 2 && blue)
					{
						sum += c.getBlue();
						d++;
					}
				}
				
				int v = sum/d;
				int nRGB = v << 16 | v << 8 | v;
				
				bwImg.setRGB(i, j, nRGB);
			}
		}
		
		return bwImg;
	}
	
	private static BufferedImage invertImage(BufferedImage img) {
		
		BufferedImage invImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		float mp = .5f;
		
		for(int i = 0; i < img.getWidth(); i++) {
			for(int j = 0; j < img.getHeight(); j++) {
				Color cp = new Color(img.getRGB(i, j));
				float[] ct = cp.getColorComponents(null);
				
				for(int a = 0; a < 3; a++) {
					ct[a] = mp - (ct[a] - mp);
				}
				
				invImg.setRGB(i, j, new Color(ct[0], ct[1], ct[2]).getRGB());
			}
		}
		
		return invImg;
	}
	
	private static BufferedImage convertToAscii(BufferedImage img, Color backgroundColor, int pixelSize, String[] characters, boolean inColor, boolean shaded) {
		float pointSize = pixelSize;
		int psc = (int) ((float)pixelSize / 2);
		Font font = new Font("Arial", Font.PLAIN, (int)pointSize);
				
		//BW
		BufferedImage bwImg = convertToBW(img, true, true, true);
		
		//New image
		BufferedImage nImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		Graphics g = nImg.getGraphics();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, nImg.getWidth(), nImg.getHeight());
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		//Create ascii image
		for(int i = 0; i < img.getWidth(); i+=pixelSize) {
			for(int j = 0; j + pixelSize < img.getHeight(); j+=pixelSize) {
				//set letter color
				if(inColor)
					g.setColor(new Color(img.getRGB(i, j)));
				if(shaded && !inColor)
					g.setColor(new Color(bwImg.getRGB(i, j)));
				
				//Select letter for new img
				int c = new Color(bwImg.getRGB(i, j)).getRed();
				float cmx = 255;
				String s = "";
				for(int a = characters.length - 1; a > -1; a--) {
					if(c <= (cmx / characters.length) * (a + 1))
						s = characters[a];
				}
				
				//Draw letter to img
				g.drawString(s, i, j);
			}
		}
		
		return nImg;
	}

	private static void exportImage(BufferedImage img, String dir, String filename) {
		try {
			ImageIO.write(img, "png", new File(dir + filename));
		} catch (IOException e) {e.printStackTrace();}
	}
}
