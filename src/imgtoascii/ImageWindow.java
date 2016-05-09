package imgtoascii;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fc.imagefilters.AsciiImage;
import fc.imagefilters.ComicBookFilter;
import fc.imagefilters.ImageUtils;
import fc.imagefilters.ImgIO;
import fc.imagefilters.ProcessedImage;

public class ImageWindow extends JFrame {

	//Images
	private ProcessedImage source;
	private ProcessedImage currentImage;
	private ProcessedImage toExport;
	
	//File information
	private String directory = "";
	private String outputFileName = "";
	private String inputFileName = "";
	
	private boolean closed = false;
	private String windowName = "";
	
	private JPanel title, fileManagement, options, canvasJP;
	
	private ImageCanvas imgCanvas;
	
	@SuppressWarnings("unused")
	private ImageWindow() {}
	
	public ImageWindow(String name) {
		super(name);
		
		initialize(name, 250, 600, true, EXIT_ON_CLOSE);
		
		addWindowLayout();
		
		addListenerIW();
		
		setVisible(true);
	}
	
	public ImageWindow(String name, int width, int height) {
		super(name);
		
		initialize(name, width, height, true, EXIT_ON_CLOSE);
		
		addWindowLayout();
		
		addListenerIW();
		
		setVisible(true);
		
	}
	
	public boolean isWindowOpen() { return closed; }
	public void addToContent() { }
	
	private void initialize(String wName, int width, int height, boolean resizable, int closeOperation) {
		windowName = wName;
		setSize(width, height);
		setResizable(resizable);
		setDefaultCloseOperation(closeOperation);
	}
	
	private void addWindowLayout() {
		//Set Layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER)); //Main Section
		
		JPanel main = new JPanel(layout);
		//main.setBackground(Color.gray);
		add(main);
		
		
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = (int) (getWidth() * .49);
		c.anchor = GridBagConstraints.NORTHWEST;
		
		//Title init
		c.gridx = 0; c.gridy = 0;
		c.gridwidth = 40; c.gridheight = 1;
		c.ipady = 10;
		newTitle();
		main.add(title, c);
		
		//FileManagement init
		c.gridx = 0; c.gridy = 1;
		c.gridheight = 2;
		c.gridwidth = 20;
		newFileManagement();
		main.add(fileManagement, c);
		
		//Options init
		c.gridx = 0; c.gridy = 3;
		c.gridheight = 1; c.gridwidth = 20;
		newOptions();
		main.add(options, c);
		
		//Canvas init
		c.ipadx = 0; c.weightx = 1;
		c.gridx = 0; c.gridy = 5;
		c.gridheight = 1; c.gridwidth = 20;
		newCanvas();
		//canvasJP.setBackground(Color.GRAY);
		add(imgCanvas, c);
	}
	
	private void newTitle() {
		title = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel titleText = new JLabel(windowName, 10);
		titleText.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
		title.add(titleText);
	}
	
	private void newFileManagement() {
		fileManagement = new JPanel(new GridLayout(2, 2));
		JTextField importText = new JTextField();
		JTextField exportText = new JTextField();
		
		JButton importBtn = new JButton("Import");
		JButton exportBtn = new JButton("Export");
		
		importBtn.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				String[] fileLocation = getDirectory(importText.getText());
				directory = fileLocation[0];
				inputFileName = fileLocation[1];
				System.out.println(directory + inputFileName);
				source = new ProcessedImage(ImgIO.importImage(directory + "/" + inputFileName));
				currentImage = new ProcessedImage(source.source);
				updateImgCanvas();
			}
		});
		
		exportBtn.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				String[] outDir = getDirectory(exportText.getText());
				
				ImgIO.exportImage(toExport.source, outDir[0], outDir[1]);
			}
		});
		
		fileManagement.add(importText);
		fileManagement.add(importBtn);
		fileManagement.add(exportText);
		fileManagement.add(exportBtn);
	}
	
	private void newOptions() {
		options = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JButton bwBtn = new JButton("Ascii");
		JButton act1Btn = new JButton("Dot");
		JButton updateBtn = new JButton("UpdateImg");
		
		bwBtn.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				AsciiImage ai = new AsciiImage(currentImage.source, new Color(20,20,20), 15, new String("abcdefghijklmnopqrstuvwxyz").split(""), true, true);
				currentImage = new ProcessedImage(ai.getAsciiImage());
				imgCanvas.updateCanvasImage(currentImage.source);
			}
		});
		
		act1Btn.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent e) {
				currentImage = new ProcessedImage(new ComicBookFilter(currentImage, 15, true).getGeneratedImage());
				imgCanvas.updateCanvasImage(currentImage.source);
			}
			
		});
		
		updateBtn.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent e) {
				toExport = new ProcessedImage(currentImage.source);
			}
		});
		
		options.add(bwBtn);
		options.add(act1Btn);
		options.add(updateBtn);
	}
	
	private void newCanvas() {
		//canvasJP = new JPanel(new FlowLayout(FlowLayout.CENTER));		
		imgCanvas = new ImageCanvas(1000, 563);
		//canvasJP.add(imgCanvas);
	}
	
	private void updateImgCanvas() {
//		this.getContentPane().add(imgCanvas);
//		imgCanvas.getGraphics().drawImage(currentImage.source, 0, 0, null);
		imgCanvas.updateCanvasImage(source.source);
		this.revalidate();
		this.repaint();
	}

	private void addListenerIW() {
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				closed = false;
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				closed = true;
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private String[] getDirectory(String text) {
		StringBuilder fn = new StringBuilder();
		StringBuilder dn = new StringBuilder();
		
		char[] ca = text.toCharArray();
		//FileName
		boolean sw = false;
		for(int i = text.length() - 1; i > -1; i--) {
			if(!sw){
				if(ca[i] == '/' || ca[i] == '\\')
				{
					sw = true;
					dn.append(ca[i]);
				} else {
					fn.append(ca[i]);							
				}
			} else {
				dn.append(ca[i]);
			}
		}
		
		return new String[] { dn.reverse().toString(), fn.reverse().toString() };
	}
}

class ImageCanvas extends Canvas {
	
	public BufferedImage img;
	private int w = 0;
	private int h = 0;
	
	public ImageCanvas (int width, int height) {
		this.setBackground(new Color(255,250,250));
		setSize(width, height);
		w = width;
		h = height;
	}
	
	public void updateCanvasImage(BufferedImage pImg) {
		double scale = 0;
		if(pImg.getWidth() > pImg.getHeight()) {
			scale = (double)1300 / pImg.getWidth();
		} else {
			scale = (double)731 / pImg.getHeight();
		}
		int xs = (int) (pImg.getWidth() * scale);
		int xy = (int) (pImg.getHeight() * scale);
		
		this.setSize(xs, xy);
		Image temp = pImg.getScaledInstance(xs, xy, Image.SCALE_FAST);
		this.img = new BufferedImage(xs, xy, pImg.getType());
		Graphics g = this.img.createGraphics();
		g.drawImage(temp, 0, 0, null);
		
		paint(this.getGraphics());
	}
	
	public void paint(Graphics g) {
		
		this.getGraphics().drawImage(img, 0, 0, null);
	}
}
