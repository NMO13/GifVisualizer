package gif;

import java.util.ArrayList;

import utils.Binary;
import decode.Pixel;

public class Format {
	private String version;
	private int aspectRatio;
	private ArrayList<Picture> pictures = new ArrayList<Picture>();
	private Pixel[] globalColourTable;
	private int width;
	private int height;
	private boolean isGCTAvailable;
	@SuppressWarnings("unused")
	private int colorRes;
	private boolean isGCTSorted;
	private int GCTSize;
	@SuppressWarnings("unused")
	private int backgroundColor;
	@SuppressWarnings("unused")
	private ArrayList<ExtensionBlock> eBlocks = new ArrayList<ExtensionBlock>();
	private int compressedDataSize;
	private int decompressedDataSize;
	
	///*** Version
	public void setVersion(byte[] ver) {
		version = new String(ver);
	}
	
	public String getVersion() {
		return version;
	}
	
	///*** Width and Height of Picture
	public void width(byte[] w) {
		width = Binary.ByteToInt(w, 0, w.length);
	}

	public void height(byte[] h) {
		height = Binary.ByteToInt(h, 0, h.length);
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	///*** Global Color Table
	public void setGlobalColourTable(Pixel[] gct) {
		globalColourTable = gct;
	}
	
	public void globalColorTableFlag(boolean gctAv) {
		isGCTAvailable = gctAv;	
	}
	
	public boolean isGCTAvailable() {
		return isGCTAvailable;
	}
	
	public Pixel[] getGlobalColourTable() {
		return globalColourTable;
	}
	
	public void gctSorted(boolean sorted) {
		isGCTSorted = sorted;
		
	}

	public void setGCTSize(boolean[] size) {
		if(isGCTAvailable)
			GCTSize = (int) Math.pow(2, Binary.BitToInt(size)+1);
		else
			GCTSize = 0;
		
	}
	
	public int getGCTSize() {
		return GCTSize;
	}
	
	///*** Pixel Aspect Ratio
	public void setPixelAspectRatio(byte copyBytes) {
		int ratio = Binary.ByteToInt(copyBytes);
		if(ratio != 0)
			aspectRatio = (ratio + 15) / 64;
		else
			aspectRatio = 0;
		
	}
	
	public int getPixelAspectRatio() {
		return aspectRatio;
	}
	
	///*** Picture
	public void addPicture(Picture p) {
		if(p != null) {
			pictures.add(p);
		}
	}
	
	public Picture getPicture() {
		if(pictures.isEmpty())
			return null;
		return pictures.get(0);
	}

	public void colorResolution(boolean[] res) {
		colorRes = Binary.BitToInt(res) + 1;
	}

	public void bgColor(byte bgColor) {
		backgroundColor = Binary.ByteToInt(bgColor);
		
	}

	public String[][] getStringRepresentation() {
		String[][] s = new String[14][2];
		s[0][0] = "Version";
		s[0][1] = this.version;
		s[1][0] = "Width";
		s[1][1] = new Integer(this.width).toString();
		s[2][0] = "Height";
		s[2][1] = new Integer(this.height).toString();
		s[3][0] = "Global Color Table Available";
		if(isGCTAvailable)
			s[3][1] = "Yes";
		else
			s[3][1] = "No";
		s[4][0] = "Global Color Table sorted";
		if(isGCTAvailable) {
			if(isGCTSorted)
				s[4][1] = "Yes";
			else
				s[4][1] = "No";
		}
		else
			s[4][1] = "-";
		s[5][0] = "Global Color Table Size";
		if(isGCTAvailable) {
			s[5][1] = new Integer(GCTSize).toString();
		}
		else
			s[5][1] = "-";
		s[6][0] = "Pixel Aspect Ratio";
		s[6][1] = new Integer(aspectRatio).toString();
		Picture p = pictures.get(0);
		s[7][0] = "X Position";
		s[7][1] = new Integer(p.getXPos()).toString();
		s[8][0] = "Y Position";
		s[8][1] = new Integer(p.getYPos()).toString();
		s[9][0] = "Is Interlaced";
		if(p.isInterlaced())
			s[9][1] = "Yes";
		else
			s[9][1] = "No";
		s[10][0] = "Color Table Available";
		
		if(p.isLCTAvailable()) {
			s[10][1] = "Yes";
		}
		else
			s[10][1] = "No";
		s[11][0] = "Local Color Table sorted";
		if(p.isLCTAvailable()) {
			
			if(p.isLCTSorted())
				s[11][1] = "Yes";
			else
				s[11][1] = "No";
		}
		else
			s[11][1] = "-";
		s[12][0] = "Local Color Table Size";
		if(p.isLCTAvailable()) {
			s[12][1] = new Integer(GCTSize).toString();
		}
		else
			s[12][1] = "-";
		
		s[13][0] = "Compression Size (% of decompr. Picture)";
		s[13][1] = new Float(getCompressionPercentage()).toString();
		
		return s;
		
	}

	public void setCompressedDataSize(int compressedDataSize) {
		this.compressedDataSize = compressedDataSize;
		
	}

	public void setDecompressedDataSize(int decompressedDataSize) {
		this.decompressedDataSize = decompressedDataSize;
		
	}
	
	public float getCompressionPercentage() {
		return (float) (compressedDataSize / (float) decompressedDataSize) * 100;
	}
}
