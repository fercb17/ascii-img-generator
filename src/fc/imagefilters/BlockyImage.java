package fc.imagefilters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Box.Filler;

public class BlockyImage extends ArtImage {
	
	private Color borderColor = Color.LIGHT_GRAY;
	private int squareSize = 10; //in pixels
	private boolean avgSqColor = false;
	private boolean randomSqColor = true;
	private boolean inColor = true;
	
	private double bSize = .1f;

	public BlockyImage(BufferedImage sourceImg) {
		this.source = sourceImg;
	}
	
	public BlockyImage(BufferedImage sourceImg, int squareSize, boolean avgSqColor, boolean inColor) {
		this.source = sourceImg;
		this.squareSize = squareSize;
		this.avgSqColor = avgSqColor;
		this.inColor = inColor;
	}
	
	@Override
	public void generateImage() {
		generateBlockyImage();
	}
	
	private BufferedImage generateBlockyImage() {
		
		BufferedImage sourceT = ImageUtils.copyImg(source);

		//New blocky image
		BufferedImage blockyImg = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
//		Graphics g = blockyImg.getGraphics();
//		g.setColor(bgColor);
//		g.fillRect(0, 0, blockyImg.getWidth(), blockyImg.getHeight());
		
		for(int i = 0; i < source.getWidth() - squareSize; i+=1){
			for(int j = 0; j < source.getHeight() - squareSize; j+=1){
				Color sqColor = Color.BLACK;
				
				if(randomSqColor){
					int x = (int) (Math.floor(i + Math.random() * squareSize));
					int y = (int) (Math.floor(j + Math.random() * squareSize));
					sqColor = new Color(sourceT.getRGB(x, y));
				}
				
				//Create square
				double lower = Math.floor(squareSize * bSize);
				double upper = Math.ceil(1 - lower);
				for(int a = i; a < squareSize; a++){
					for(int b = j; b < squareSize; b++){
//						if(a < i + lower || a > i + upper && b < j + lower || b > j + upper){ //border
//							img.setRGB(a, b, testColor.getRGB());
//						} else { //inside
//							img.setRGB(a, b, sqColor.getRGB());
//						}
						blockyImg.setRGB(a, b, 255 << 16 | 0 << 8 | 0);
					}
				}
				
				blockyImg.setRGB(i, j, sqColor.getRGB());
//				g.setColor(sqColor);
//				g.fillRect(i, j, squareSize, squareSize);
			}
		}
		
		gImage = blockyImg;
		return gImage;
	}
}
