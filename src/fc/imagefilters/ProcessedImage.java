package fc.imagefilters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ProcessedImage {
	public BufferedImage source;
	
	public int height = 0;
	public int width = 0;
	public int type = 0;
	public ArrayList<PointSample> rgbArray = new ArrayList<PointSample>();
	
	public ProcessedImage(BufferedImage source) {
		this.source = source;
		
		initialize();
	}
	
	public ProcessedImage(String file) {
		this.source = ImgIO.importImage(file);
		
		initialize();
	}
	
	public ProcessedImage(int width, int height, int type) {
		this.source = new BufferedImage(width, height, type);
		
		initialize();
	}
	
	public ProcessedImage(ProcessedImage img) {
		this.source = new BufferedImage(img.width, img.height, img.type);
		initialize();
	}
	
	public void updateImage() {
		for(PointSample e : rgbArray) {
			source.setRGB(e.point.x, e.point.y, e.rgb.getRGB());
		}
	}
	
	public ProcessedImage getCopy() {
		return new ProcessedImage(source);
	}
	
	public PointSample[] getSqSample(Point2 origin, int strength) {
		
		int x,y;
		int xpStr,xnStr,ypStr,ynStr;
		
		xpStr = ypStr = strength;
		xnStr = ynStr = -strength;
		
		if(origin.x > width - xpStr) {
			xpStr = width - origin.x;
		} else if(origin.x < -xnStr) {
			xnStr = xnStr - (xnStr + origin.x);
		}
		
		if(origin.y > height - ypStr) {
			ypStr = height - origin.y;
		} else if(origin.y < -ynStr) {
			ynStr = ynStr - (ynStr + origin.y);
		}
		
		int count = 1;
		for(int i = origin.x + xnStr; i < origin.x + xpStr; i++)
			for(int j = origin.y + ynStr; j < origin.y + ypStr; j++)
				count++;
		
		PointSample[] t = new PointSample[count];
		count = 0;
		for(int i = origin.x + xnStr; i < origin.x + xpStr; i++) {
			for(int j = origin.y + ynStr; j < origin.y + ypStr; j++) {
				t[count++] = new PointSample(new Color(source.getRGB(i, j)), new Point2(i, j));
			}
		}
		
		return t;
	}
	
	
	private void initialize() {
		generateRGBArray();
		
		height = source.getHeight();
		width = source.getWidth();
		type = source.getType();
	}
	
	private void generateRGBArray() {
		for(int i = 0; i < source.getWidth(); i++) {
			for(int j = 0; j < source.getHeight(); j++) {
				rgbArray.add(new PointSample(new Color(source.getRGB(i, j)), new Point2(i, j)));
			}
		}
	}
}