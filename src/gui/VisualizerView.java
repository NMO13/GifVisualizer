package gui;

import exception.FileNotValidException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import model.VisualizerModel;
import model.impl.ColorEvent;
import model.impl.DecodeEvent;
import model.impl.DecodeListener;
import model.impl.ScalingEvent;

public class VisualizerView extends JFrame implements DecodeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VisualizerModel m_model;
	private JMenuBar bar;
	private PicturePanel picturePanel;
	private Color chooserColor = Color.WHITE;
	private JSlider slider;
	private JLabel label_red, label_green, label_blue;
	private JPanel colorChooserPanel;
	
	private void initComponents(JPanel panel, JPanel colorPanel, JPanel colorChooserPanel) {

			JLabel colorLabel = new javax.swing.JLabel("Color");
			JButton colorChooserButton = new javax.swing.JButton();
			JLabel colorChooserLabel = new javax.swing.JLabel("Choose Color");
			label_red = new javax.swing.JLabel("Red Value: ");
			label_green = new javax.swing.JLabel("Green Value: ");
			label_blue = new javax.swing.JLabel("Blue Value: ");
			JLabel sizingFactorLabel = new javax.swing.JLabel("Sizing Factor");
			slider = new JSlider(1, 4, 1);
			slider.setMajorTickSpacing(1);
			slider.setSnapToTicks(true);
			slider.setPaintTicks(true);
			slider.addChangeListener(sliderListener);
			colorChooserButton.addActionListener(colorButtonListener);

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(colorPanel);
	        colorPanel.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 47, Short.MAX_VALUE)
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 22, Short.MAX_VALUE)
	        );


	        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(colorChooserPanel);
	        colorChooserPanel.setLayout(jPanel2Layout);
	        jPanel2Layout.setHorizontalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 47, Short.MAX_VALUE)
	        );
	        jPanel2Layout.setVerticalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 23, Short.MAX_VALUE)
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
	        panel.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                        .addGap(20, 20, 20)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(label_red)
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(colorLabel)
	                                .addGap(18, 18, 18)
	                                .addComponent(colorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                            .addComponent(label_green)
	                            .addComponent(label_blue)
	                            .addComponent(sizingFactorLabel)
	                            .addComponent(colorChooserLabel)
	                            .addComponent(colorChooserButton)
	                            .addComponent(slider, 0, 0, Short.MAX_VALUE))
	                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                            .addComponent(colorChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addGap(130, 130, 130))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(colorLabel)
	                        .addGap(36, 36, 36)
	                        .addComponent(label_red)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(label_green)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(label_blue))
	                    .addComponent(colorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(27, 27, 27)
	                .addComponent(sizingFactorLabel)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(18, 18, 18)
	                .addComponent(colorChooserLabel)
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                        .addComponent(colorChooserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(colorChooserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                    .addContainerGap(104, Short.MAX_VALUE)));
    }

	/*private void initComponents(JPanel panel, JPanel colorPanel, JPanel colorChooserPanel) {

		JLabel colorLabel = new javax.swing.JLabel("Color");
		JButton colorChooserButton = new javax.swing.JButton();
		JLabel colorChooserLabel = new javax.swing.JLabel("Choose Color");
		label_red = new javax.swing.JLabel("Red Value: ");
		label_green = new javax.swing.JLabel("Green Value: ");
		label_blue = new javax.swing.JLabel("Blue Value: ");
		JLabel sizingFactorLabel = new javax.swing.JLabel("Sizing Factor");
		slider = new JSlider(1, 4, 1);
		slider.setMajorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.addChangeListener(sliderListener);


		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				colorPanel);
		colorPanel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 65,
				Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 20,
				Short.MAX_VALUE));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
		panel.setLayout(layout);
		layout.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(27, 27, 27)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				10,
																				10,
																				10)
																		.addComponent(
																				slider,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				150,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(label_red)
														.addComponent(label_green)
														.addComponent(label_blue)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				colorLabel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				54,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				colorPanel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																sizingFactorLabel,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																100,
																javax.swing.GroupLayout.PREFERRED_SIZE))
													    .addGroup(layout.createSequentialGroup()
													    .addContainerGap()
													    .addComponent(colorChooserLabel))
													    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(colorChooserButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(colorChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(190, Short.MAX_VALUE)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(24, 24, 24)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																colorPanel,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(colorLabel))
										.addGap(21, 21, 21)
										.addComponent(label_red)
										.addGap(18, 18, 18)
										.addComponent(label_green)
										.addGap(18, 18, 18)
										.addComponent(label_blue)
										.addGap(27, 27, 27)
										.addComponent(sizingFactorLabel)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												slider,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										//.addContainerGap(82, Short.MAX_VALUE)));
		 .addGap(18, 18, 18)
         .addComponent(colorChooserLabel)
         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
         .addGroup(layout.createSequentialGroup()
             .addComponent(colorChooserButton)
             .addComponent(colorChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
         .addContainerGap(82, Short.MAX_VALUE)));
	}*/

	public VisualizerView(VisualizerModel model) {
		label_red = new JLabel();
		label_green = new JLabel();
		label_blue = new JLabel();

		m_model = model;
		model.addDecodeListener(this);
		JPanel colorPanel = new JPanel();
		colorChooserPanel = new JPanel();
		
		picturePanel = new PicturePanel(model, colorPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		bar = createMenuBar();
		this.setJMenuBar(bar);
		JPanel p = new JPanel();
		initComponents(p, colorPanel, colorChooserPanel);
		p.setPreferredSize(new Dimension(200, 10));
		this.getContentPane().add(p, BorderLayout.EAST);
		this.getContentPane().add(new JScrollPane(picturePanel),
				BorderLayout.CENTER);
		// / slider
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 600);
	}

	ActionListener colorButtonListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Color c = JColorChooser.showDialog(VisualizerView.this,
					"Choose Background Color", chooserColor);
			if (c != null) {
				chooserColor = c;
				colorChooserPanel.setBackground(c);
			}
		}
	};

	ChangeListener sliderListener = new ChangeListener() {

		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				m_model.setScalingFactor((int) source.getValue());
			}
		}

	};

	public VisualizerModel getModel() {
		return m_model;
	}


	public void update(DecodeEvent event) {
		picturePanel.repaint();
		picturePanel.pictureHasChanged = true;

	}

	public void showGui() {
		this.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu infoMenu = new JMenu("File Info");
		fileMenu.setMnemonic(KeyEvent.VK_M);
		fileMenu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(fileMenu);
		menuBar.add(infoMenu);
		JMenuItem openItem = new JMenuItem("Open"); 
		final JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem exitItem = new JMenuItem("Exit");
		openItem.getAccessibleContext().setAccessibleDescription(
				"Opens a gif-file");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		JMenuItem infoItem = new JMenuItem("Info");
		infoMenu.add(infoItem);

		infoItem.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				InfoDialog dialog = new InfoDialog(VisualizerView.this, "Info",
						VisualizerView.this.m_model
								.getStringRepresentationOfFile());
				dialog.setVisible(true);

			}

		});

		exitItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				VisualizerView.this.dispose();
			}

		});
		
		saveItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fchooser = null;
				try {
					File f = m_model.getFile();
					fchooser = new JFileChooser(f.getPath());
					fchooser.setSelectedFile(f);
				} catch (FileNotValidException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),
							"Error", JOptionPane.OK_CANCEL_OPTION);
				}
				int returnVal = fchooser.showSaveDialog(VisualizerView.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fchooser.getSelectedFile();
					BufferedOutputStream stream = null;
					 try {
						stream = new BufferedOutputStream(new FileOutputStream(file));
						stream.write(m_model.getFileData(), 0, m_model.getFileData().length);
					} catch (FileNotFoundException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Error", JOptionPane.OK_CANCEL_OPTION);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Error", JOptionPane.OK_CANCEL_OPTION);
					}
					finally {
						if(stream != null) {
							try {
								stream.flush();
								stream.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				} else 
					System.out.println("save canceled");
			}
			
		});
		
		openItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fchooser = new JFileChooser();
				int returnVal = fchooser.showOpenDialog(VisualizerView.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fchooser.getSelectedFile();

					try {
						m_model.setFile(file);
					} catch (FileNotValidException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Error", JOptionPane.OK_CANCEL_OPTION);
					}
					catch(IOException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Error", JOptionPane.OK_CANCEL_OPTION);
					}
				} else {
					System.out.println("open canceled");
				}

			}

		});
		
		fileMenu.addMenuListener(new MenuListener() {

			
			public void menuCanceled(MenuEvent e) {
				// MARTIN Auto-generated method stub
				
			}

			
			public void menuDeselected(MenuEvent e) {
				// MARTIN Auto-generated method stub
				
			}

			
			public void menuSelected(MenuEvent e) {
				try {
					if(m_model.getFile() == null)
						saveItem.setEnabled(false);
					else
						saveItem.setEnabled(true);
				} catch (FileNotValidException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});

		return menuBar;

	}

	public void updateScaling(ScalingEvent event) {
		picturePanel.pictureHasChanged = true;
		picturePanel.setScalingFactor(event.getScalingFactor());
		picturePanel.repaint();
		picturePanel.revalidate();
	}

	public void updateColor(ColorEvent event) {
		label_red.setText("Red Value: " + new Integer(event.getColor().getRed()).toString());
		label_green
				.setText("Green Value: " + new Integer(event.getColor().getGreen()).toString());
		label_blue.setText("Blue Value: " + new Integer(event.getColor().getBlue()).toString());

	}

}
