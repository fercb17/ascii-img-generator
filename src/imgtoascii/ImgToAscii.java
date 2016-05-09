package imgtoascii;

public class ImgToAscii {

	private static long lastCheck = 0;
	private static long deltaTime = 0;
	private static long refreshRate = 1;
	
	public static void main(String[] args) {
		ImageWindow tw = new ImageWindow("Image Window", 1600, 900);
		
		
		boolean first = true;
		while(tw.isWindowOpen()) {
			//First pass
			if(first) {
				first = false;
				lastCheck = System.currentTimeMillis();
			}
			
			//Update
			deltaTime += System.currentTimeMillis() - lastCheck;
			lastCheck = System.currentTimeMillis();
			
			if(deltaTime >= refreshRate) {
				//tw.updateTimerRows(deltaTime);
				
				//End of update
				deltaTime = 0;
			}
		}
	}

}
