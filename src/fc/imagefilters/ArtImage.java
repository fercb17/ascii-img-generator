package fc.imagefilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class ArtImage {
	
	protected BufferedImage source;
	protected BufferedImage gImage;
	protected boolean sourceChanged = false;
	protected Color bgColor = new Color(10, 10, 10);
	protected Color testColor = new Color(226, 77, 90);
	
	public void generateImage() {}
	
	public BufferedImage getGeneratedImage() {
		if(sourceChanged || gImage == null) {
			generateImage();
			return gImage;
		} else {
			return gImage;
		}
	}
	
	public void setSourceImage(BufferedImage img) {
		source = img;
	}
}
