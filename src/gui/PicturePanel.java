package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import model.VisualizerModel;

public class PicturePanel extends JPanel{

	private class MyMouseAdapter extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			mousePos = e.getPoint();
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			mousePos = null;
		}
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VisualizerModel m_model;
	private JPanel colorPanel;
	private MyMouseAdapter mouseListener = new MyMouseAdapter();
	private Point mousePos;
    public boolean pictureHasChanged = false;
    int scalingFactor = 1;
    
	public PicturePanel(VisualizerModel model, JPanel cp) {

		m_model = model;
		colorPanel = cp;
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}

	public void setScalingFactor(int sF) {
		scalingFactor = sF;
	}
	
	BufferedImage img2 = null;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int color = -1;
		BufferedImage img = null;
		if(pictureHasChanged) {
			pictureHasChanged = false;
		    img = (BufferedImage) m_model.getImage();
			if(img != null) {
				/// scaling
				img2 = new BufferedImage(img.getWidth()*scalingFactor, img.getHeight()*scalingFactor, BufferedImage.TYPE_3BYTE_BGR);
				for(int h = 0; h < img.getHeight(); h++) 
					for(int w = 0; w < img.getWidth(); w++) {
						int c = img.getRGB(w, h);
							
						for(int k = 0; k < scalingFactor; k++)
							for(int i = 0; i < scalingFactor; i++) {
								img2.setRGB(w*scalingFactor + i, h*scalingFactor + k, c);
						}
							
					}
			}
		}
		g2d.drawImage(img2, 0, 0, null);
		this.revalidate();
		/// pixel color
		if (mousePos != null && img2 != null) {
			try {
				color = img2.getRGB(mousePos.x, mousePos.y);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				color = 0;
			}
			Color c = new Color(color);
			m_model.setColor(c);
			colorPanel.setBackground(c);
			colorPanel.repaint();
		}
	}
	
	//////// Scrolling

	@Override
	public Dimension getPreferredSize() {
		if(img2 == null) {
			return new Dimension(getParent().getBounds().width, getParent().getBounds().height);
		}
		else {
			return new Dimension(img2.getWidth(), img2.getHeight());
		}
	}

}
