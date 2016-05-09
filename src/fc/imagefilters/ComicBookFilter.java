package fc.imagefilters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ComicBookFilter extends ArtImage {
	
	private ProcessedImage pSource;
	private int circleSize = 10;
	private boolean bevelled = false;
	private boolean border = false;
	private int borderSize = 0;
	
	public ComicBookFilter(ProcessedImage img, int circleSize) {
		this.pSource = img;
		this.circleSize = circleSize;
	}
	
	public ComicBookFilter(ProcessedImage img, int circleSize, boolean bevelled) {
		this.pSource = img;
		this.circleSize = circleSize;
		this.bevelled = bevelled;
	}
	
	public ComicBookFilter(ProcessedImage img, int circleSize, boolean border, int borderSize) {
		this.pSource = img;
		this.circleSize = circleSize;
		this.border = border;
		this.borderSize = borderSize;
	}
	
	@Override
	public void generateImage() {
		generateComicImage();
	}
	
	private BufferedImage generateComicImage() {
		//New comic image
		ProcessedImage comicImg = new ProcessedImage(pSource.width, pSource.height, pSource.type);
		Graphics g = comicImg.source.getGraphics();
		g.setColor(bgColor);
		g.fillRect(0, 0, comicImg.width, comicImg.height);
		
		//Border
		int bevelSize = (int)Math.ceil(circleSize * 1.1f);
		int borderCircleSize = circleSize + (2 * this.borderSize);
		
		if(bevelled) {
			for(int i = 0; i < comicImg.width; i+=bevelSize) {
				for(int j = 0; j < comicImg.height; j+=bevelSize) {
					g.setColor((new Color(pSource.source.getRGB(i,j))).darker());
					g.fillOval(i, j, bevelSize, bevelSize);
					g.setColor(new Color(pSource.source.getRGB(i,j)));
					g.fillOval(i, j, circleSize, circleSize);
				}
			}
		} else if (border) {
			for(int i = 0; i < comicImg.width; i+=borderCircleSize) {
				for(int j = 0; j < comicImg.height; j+=borderCircleSize) {
					g.setColor(new Color(pSource.source.getRGB(i,j)).darker());
					g.fillOval(i, j, borderCircleSize, borderCircleSize);
					g.setColor(new Color(pSource.source.getRGB(i,j)));
					g.fillOval(i + borderSize, j + borderSize, circleSize, circleSize);
				}
			}
		} else {
			for(int i = 0; i < comicImg.width; i+=circleSize) {
				for(int j = 0; j < comicImg.height; j+=circleSize) {
					g.setColor(new Color(pSource.source.getRGB(i,j)));
					g.fillOval(i, j, circleSize, circleSize);
				}
			}
		}
		
		gImage = comicImg.source;
		return gImage;
	}
	
}
