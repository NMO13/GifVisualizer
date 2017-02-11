package model;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import exception.FileNotValidException;

import model.impl.DecodeListener;

public interface VisualizerModel {

	public void addDecodeListener(final DecodeListener drawingListener);
	
	public void removeDecodeListener(final DecodeListener drawingListener);
	
	public Image getImage();
	
	public void setFile(File f) throws FileNotValidException, IOException;
	
	public File getFile() throws FileNotValidException;
	
	public void clearImage();
	
	public void setScalingFactor(int factor);

	public void setColor(Color c);

	String[][] getStringRepresentationOfFile();
	
	public byte[] getFileData();
}
