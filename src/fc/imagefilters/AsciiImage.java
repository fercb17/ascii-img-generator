package fc.imagefilters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AsciiImage {
	
	private BufferedImage sourceImg = null;
	private Color bgColor = Color.BLACK;
	private int pixelSize = 10;
	private String[] characters = { " " };
	boolean inColor = false;
	boolean shaded = false;
	
	public AsciiImage(BufferedImage sourceImg, Color backgroundColor, int pixelSize, String[] characterSet, boolean colored, boolean shaded) {
		this.sourceImg = sourceImg;
		this.bgColor = backgroundColor;
		this.pixelSize = pixelSize;
		this.characters = characterSet;
		this.inColor = colored;
		this.shaded = shaded;
	}
	
	public BufferedImage getAsciiImage() {
		return convertToAscii();
	}
	
//	private BufferedImage convertToAscii(BufferedImage img, Color backgroundColor, int pixelSize, String[] characters, boolean inColor, boolean shaded) {
	private BufferedImage convertToAscii() {
		float pointSize = pixelSize;
		int psc = (int) ((float)pixelSize / 2);
		Font font = new Font("Arial", Font.BOLD, (int)pointSize);
				
		//BW
		BufferedImage bwImg = ImageUtils.convertToBW(sourceImg, true, true, true);
		
		//New image
		BufferedImage nImg = new BufferedImage(sourceImg.getWidth(), sourceImg.getHeight(), sourceImg.getType());
		Graphics g = nImg.getGraphics();
		g.setColor(bgColor);
		g.fillRect(0, 0, nImg.getWidth(), nImg.getHeight());
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		//Create ascii image
		for(int i = 0; i < sourceImg.getWidth(); i+=pixelSize) {
			for(int j = 0; j + pixelSize < sourceImg.getHeight(); j+=pixelSize) {
				//set letter color
				if(inColor)
					g.setColor(new Color(sourceImg.getRGB(i, j)));
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
}
