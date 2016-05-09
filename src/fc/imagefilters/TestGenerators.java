package fc.imagefilters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class TestGenerators {

//	public static void main(String[] args) {
//		String dir = args[0];
//		String fileName = args[1];
//		String[] fn = fileName.split("\\.");
//		String imgName = fn[0];
//		
//		ProcessedImage img = new ProcessedImage(dir + "/" + fileName);
//		
//		//AsciiImage aimg = new AsciiImage(ImgIO.importImage(dir, fileName), new Color(0, 0, 0), 12, new String("abcdefghijklmnopqrstuvwxyz").split(""), true, false);
//		//ImgIO.exportImage(aimg.getAsciiImage(), dir, imgName + "_ascii.png");
//		
//		//BlockyImage bimg = new BlockyImage(ImgIO.importImage(dir, fileName), 20, true, true);
//		//ImgIO.exportImage(bimg.getGeneratedImage(), dir, imgName + "_blocky.png");
//		System.out.println("start");
//		System.out.println("end");
//	}
//	
//	public static ProcessedImage lorenzAttractor(ProcessedImage img) {
//		ProcessedImage pImg = new ProcessedImage(img.width, img.height, img.type);
//		Graphics g = pImg.source.getGraphics();
//		g.setColor(new Color(15,15,15));
//		g.fillRect(0, 0, pImg.width, pImg.height);
//		
//		float p = 8.0f/3.0f;
//		
//		for (int a = 0; a < pImg.height; a+=100) {
//			for (int i = 0; i < pImg.width; i++) {
//				for (int j = 0; j < pImg.height; j++) {
//					int x = i;
//					int y = (int) (Math.sin(i) * 50) + a;
//
//					if (x < img.width && y < img.height && x > 0 && y > 0) {
//						g.setColor(new Color(img.source.getRGB(x, y)));
//						g.fillOval(x, y, 4, 4);
//					}
//				}
//			}
//		}
//		return pImg;
//	}
}