package model.impl;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import decode.Decoder;
import exception.FileNotValidException;


import model.VisualizerModel;

public class VisualizerModelImpl implements VisualizerModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<DecodeListener> listeners = new ArrayList<DecodeListener>();
	@SuppressWarnings("unused")
	private File file;
	private Image image;
	private Decoder decoder;

	
	public void addDecodeListener(final DecodeListener drawingListener) {
		listeners.add(drawingListener);
	}

	
	public void removeDecodeListener(final DecodeListener drawingListener) {
		listeners.remove(drawingListener);
	}

	
	public void setFile(File f) throws FileNotValidException, IOException {
		file = f;
		decoder = new Decoder();
		image = decoder.decode(f);
		fireConverterEvent();
	}
	
	public File getFile() throws FileNotValidException {
		return file;
	}
	
	public byte[] getFileData() {
		return decoder.getFileData();
	}


	public void clearImage() {
		image = null;
	}


	public Image getImage() {
		return image;
	}
	
	private void fireConverterEvent() {
		final DecodeEvent event = new DecodeEvent(this);
		for (DecodeListener listener : listeners) {
			listener.update(event);
		}
	}


	public void setScalingFactor(int factor) {
		fireScalingEvent(factor);
		
	}

	private void fireScalingEvent(int factor) {
		final ScalingEvent event = new ScalingEvent(this, factor);
		for(DecodeListener listener : listeners) {
			listener.updateScaling(event);
		}
		
	}


	public void setColor(Color color) {
		final ColorEvent event = new ColorEvent(this, color);
		for(DecodeListener listener : listeners) {
			listener.updateColor(event);
		}
	}
	

	public String[][] getStringRepresentationOfFile() {
		if(decoder != null)
			return decoder.getStringRepresentation();
		return null;
	}

}
