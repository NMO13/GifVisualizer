package gif;

import utils.Binary;
import decode.Pixel;

public class Picture {

	private int XPos;
	private int YPos;
	private int Width;
	private int Height;
	private boolean isLCTAvailable;
	private boolean IsInterlaced;
	private boolean isLCTSorted;
	
	public boolean isInterlaced() {
		return IsInterlaced;
	}


	public void setInterlaced(boolean isInterlaced) {
		IsInterlaced = isInterlaced;
	}


	public void setLCTSize(int size) {
		LCTSize = size;
	}


	private int LCTSize;
	private Pixel[] localColourTable;
	public byte m_lzwMinCodeSize;

	public DataBlock block;
	
	public class DataBlock {
		public int size = 0;
		public byte[] data;
	}

	public void addExtension(ExtensionBlock ex) {
		// MARTIN Auto-generated method stub	
	}
	
	
	public int getXPos() {
		return XPos;
	}


	public void setXPos(int xPos) {
		XPos = xPos;
	}


	public int getYPos() {
		return YPos;
	}


	public void setYPos(int yPos) {
		YPos = yPos;
	}


	///*** Global Color Table
	public void setLocalColourTable(Pixel[] gct) {
		localColourTable = gct;
	}
	
	public void localColorTableFlag(boolean gctAv) {
		isLCTAvailable = gctAv;	
	}
	
	public boolean isLCTAvailable() {
		return isLCTAvailable;
	}
	
	public Pixel[] getLocalColourTable() {
		return localColourTable;
	}
	
	public void lctSorted(boolean sorted) {
		isLCTSorted = sorted;
	}
	
	public boolean isLCTSorted() {
		return isLCTSorted;
	}

	public void setLCTSize(boolean[] size) {
		if(isLCTAvailable)
			LCTSize = (int) Math.pow(2, Binary.BitToInt(size)+1);
		else
			LCTSize = 0;
		
	}
	
	public int getLCTSize() {
		return LCTSize;
	}
	public int getWidth() {
		return Width;
	}


	public void setWidth(int width) {
		Width = width;
	}


	public int getHeight() {
		return Height;
	}


	public void setHeight(int height) {
		Height = height;
	}
	
}
