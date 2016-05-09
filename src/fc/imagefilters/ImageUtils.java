package fc.imagefilters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class ImageUtils {

	private ImageUtils() {}
	
	public static BufferedImage copyImg(BufferedImage toCopy) {
		BufferedImage copy = new BufferedImage(toCopy.getWidth(), toCopy.getHeight(), toCopy.getType());
		for(int i = 0; i < toCopy.getWidth(); i++){
			for(int j = 0; j < toCopy.getHeight(); j++){
				copy.setRGB(i, j, toCopy.getRGB(i, j));
			}
		}
		
		return copy;
	}
	
	public static BufferedImage convertToBW(BufferedImage img, boolean red, boolean green, boolean blue) {
		
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
	
	public static BufferedImage invertImage(BufferedImage img) {
		
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
	
	public static BufferedImage blurImageM1(BufferedImage img, int strength) {
		//New blurred image
		BufferedImage blurImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		float bSize = .1f;
		
		for(int i = 0; i < img.getWidth(); i+=1){
			for(int j = 0; j < img.getHeight(); j+=1){
				Color sqColor = Color.BLACK;
				int x,y = 0;
				int w = img.getWidth();
				int h = img.getHeight();
				
				//Width
				if (i < w - strength) {
					x = (int) (Math.floor(i + Math.random() * strength));
				} else {
					int strengthT = w - i;
					x = (int) (Math.floor(i + Math.random() * strengthT));
				}
				//Height
				if(j < h - strength) {
					y = (int) (Math.floor(j + Math.random() * strength));
				} else {
					int strengthT = h - j;
					y = (int) (Math.floor(j + Math.random() * strengthT));
				}
				
				try {
					sqColor = new Color(img.getRGB(x, y));
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
				blurImg.setRGB(i, j, sqColor.getRGB());
			}
		}

		return blurImg;
	}
	
	public static BufferedImage blurImageM2(BufferedImage img, boolean uniform, int strength) {
		//New blurred image
		BufferedImage blurImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		float bSize = .1f;
		int w = img.getWidth();
		int h = img.getHeight();
		int wd2 = w / 2;
		int hd2 = h / 2;
		
		//fix strength
		strength = Math.abs(strength);
		strength = (strength % 2 == 0) ? strength : strength + 1;
		strength = strength / 2;
		
		long seed = System.currentTimeMillis();
		Random r = new Random(seed);
		
		for(int i = 0; i < img.getWidth(); i+=1){
			for(int j = 0; j < img.getHeight(); j+=1){
				Color sqColor = Color.BLACK;
				
				int x,y;
				int xpStr,xnStr,ypStr,ynStr;
				
				xpStr = ypStr = strength;
				xnStr = ynStr = -strength;
				
//				r.setSeed(seed++);
				
				if(i > w - xpStr) {
					xpStr = w - i;
				} else if(i < -xnStr) {
					xnStr = xnStr - (xnStr + i);
				}
				
				if(j > h - ypStr) {
					ypStr = h - j;
				} else if(j < -ynStr) {
					ynStr = ynStr - (ynStr + j);
				}
				
				x = (int) ((i + xnStr) + r.nextDouble() * (-xnStr + xpStr));
				y = (int) ((j + ynStr) + r.nextDouble() * (-ynStr + ypStr));
				
				try {
					Color a = new Color(img.getRGB(x, y));
					Color b = new Color(img.getRGB(i, j));
					int red = (a.getRed() + b.getRed()) / 2;
					int green = (a.getGreen() + b.getGreen()) / 2;
					int blue = (a.getBlue() + b.getBlue()) / 2;
					sqColor = new Color(red, green, blue);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
				
				blurImg.setRGB(i, j, sqColor.getRGB());
			}
		}

		return blurImg;
	}

	public static ProcessedImage blurImageM3(ProcessedImage img, boolean uniform, int strength) {
		//New blurred image
		ProcessedImage blurImg = new ProcessedImage(new BufferedImage(img.width, img.height, img.type));
		float bSize = .1f;
		int w = img.width;
		int h = img.height;
		int wd2 = w / 2;
		int hd2 = h / 2;
		
		//fix strength
		strength = Math.abs(strength);
		strength = (strength % 2 == 0) ? strength : strength + 1;
		strength = strength / 2;
		
		Random r = new Random(System.currentTimeMillis());

		for(PointSample e : blurImg.rgbArray) {
			Color sqColor = Color.BLACK;
			
			int x,y;
			int xpStr,xnStr,ypStr,ynStr;
			
			xpStr = ypStr = strength;
			xnStr = ynStr = -strength;
			
			if(e.point.x > w - xpStr) {
				xpStr = w - e.point.x;
			} else if(e.point.x < -xnStr) {
				xnStr = xnStr - (xnStr + e.point.x);
			}
			
			if(e.point.y > h - ypStr) {
				ypStr = h - e.point.y;
			} else if(e.point.y < -ynStr) {
				ynStr = ynStr - (ynStr + e.point.y);
			}
			
			x = (int) ((e.point.x + xnStr) + r.nextDouble() * (-xnStr + xpStr));
			y = (int) ((e.point.y + ynStr) + r.nextDouble() * (-ynStr + ypStr));
			
			try {
				sqColor = new Color(img.source.getRGB(x, y));
			} catch (Exception exc) {
				exc.printStackTrace();
				System.exit(1);
			}

			e.rgb = sqColor;
		}
		
		blurImg.updateImage();
		return blurImg;
	}
	
	public static BufferedImage blurImageM4(BufferedImage img, boolean uniform, int strength) {
		ProcessedImage sImg = new ProcessedImage(img);
		ProcessedImage blurImg = new ProcessedImage(new BufferedImage(sImg.width, sImg.height, sImg.source.getType()));
		Random r = new Random(System.currentTimeMillis());
		
		for(int i = 0; i < blurImg.rgbArray.size(); i++) {
			float progress = (float)i / blurImg.rgbArray.size();
			PointSample[] t = sImg.getSqSample(blurImg.rgbArray.get(i).point, strength);
			blurImg.rgbArray.get(i).rgb = t[(int) r.nextDouble() * (t.length - 1)].rgb;
		}
		
		//Update Image
		blurImg.updateImage();
		
		return blurImg.source;
	}
}
