package decode;

import exception.FileNotValidException;
import gif.Block;
import gif.ExtensionBlock;
import gif.Format;
import gif.GraphicControlExtension;
import gif.Picture;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


import utils.Binary;

public class Decoder {

	byte[] bytes;
	
	public byte[] getFileData() {
		return bytes;
	}
	
	public Image decode(File f) throws FileNotValidException, IOException {
		bytes = new byte[(int) f.length()];
		BufferedInputStream stream = null;
		Image image = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(f));
			stream.read(bytes);
			if (bytes.length == 0)
				return null;
			Pixel[] pixels = format(bytes);
			image = setImage(pixels);
			return image;

		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private Image setImage(Pixel[] pixels) {
		BufferedImage image = new BufferedImage(format.getWidth(), format.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
		int color = 0;
		for(int h = 0; h < format.getHeight(); h++) 
			for(int w = 0; w < format.getWidth(); w++) {
				Pixel p = pixels[format.getWidth()*h + w];
				color = p.R;
				color <<= 8;
				color = p.G | color;
				color <<= 8;
				color = p.B | color;
				image.setRGB(w, h, color);
		}
		return image;
	}

	Format format = new Format();
	Picture p = null;
	private Pixel[] format(byte[] bytes) throws FileNotValidException{
		long pos = 0;
		
		///** set header bytes
		format.setVersion(Binary.copyBytes(bytes, pos, 6));

		if(!format.getVersion().equals("GIF87a") && !format.getVersion().equals("GIF89a"))
			throw new FileNotValidException("Gif file cannot be read!");
		// /** set logical screen Descriptor
		format.width(Binary.copyBytes(bytes, pos+=6, 2));
		format.height(Binary.copyBytes(bytes, pos+=2, 2));
		format.globalColorTableFlag(Binary.isBitSet(bytes[(int) (pos+=2)], 7));
		format.colorResolution(Binary.getBits(bytes[(int) pos], 4, 3));
		format.gctSorted(Binary.isBitSet(bytes[(int) pos], 3));
		format.setGCTSize(Binary.getBits(bytes[(int) pos], 0, 3));
		format.bgColor(bytes[(int) ++pos]);
		format.setPixelAspectRatio(bytes[(int) ++pos]);

		int size = 0;
		///** global color table (if available)
		if (format.isGCTAvailable()) {
			// global color table is available
			size = format.getGCTSize(); // 3: 3 colors
			Pixel[] pixels = new Pixel[size];
			++pos;
			for(int i = 0, j = 0; i < size; i++, j+=3)
			{
				pixels[i] = new Pixel();
				pixels[i].R = Binary.ByteToInt(bytes[(int) (pos+j)]);
				pixels[i].G = Binary.ByteToInt(bytes[(int) (pos+j+1)]);
				pixels[i].B = Binary.ByteToInt(bytes[(int) (pos+j+2)]);
			}
			format.setGlobalColourTable(pixels);
			pos += size*3 - 1;
		}
		
		
		// check whether a new image block or an extension block starts
		///*** 
		///**** new Image starts
		///** image descriptor
		p = new Picture();
		format.addPicture(p);
		int blockType = Binary.ByteToInt(bytes[(int) ++pos]);
		while(blockType == 0x21 || blockType == 0x2c) {
			if(blockType == 0x21) { // here a factory for control extensions would be well suited 
				int extensioncode = Binary.ByteToInt(bytes[(int) ++pos]);
				ExtensionBlock ex = null;
				if(extensioncode == 0xf9) // new graphiccontrolextension
					ex = new GraphicControlExtension(blockType);
				else if(extensioncode == 0xfe)
					System.out.println("add here");
				
				int blocksize = Binary.ByteToInt(bytes[(int) ++pos]); // hast to be 4
				pos += blocksize+2;
				/// set graphiccontrolextension
				
				p.addExtension(ex);
			}
			else if(blockType == 0x2c) { // new image block
				/// image descriptor
				pos++;
				p.setXPos(Binary.ByteToInt(bytes, pos, 2));
				pos += 2;
				p.setYPos(Binary.ByteToInt(bytes, pos, 2));
				pos += 2;
				p.setWidth(Binary.ByteToInt(bytes, pos, 2));
				pos += 2;
				p.setHeight(Binary.ByteToInt(bytes, pos, 2));
				pos += 2;
				p.localColorTableFlag(Binary.isBitSet(bytes[(int) pos], 7));
				p.setInterlaced(Binary.isBitSet(bytes[(int) pos], 6));
				p.lctSorted(Binary.isBitSet(bytes[(int) pos], 5));
				p.setLCTSize(Binary.getBits(bytes[(int) pos], 0, 3));
				
				size = 0;
				// local color map if present
				if(p.isLCTAvailable()) {
					size = p.getLCTSize(); // 3: 3 colors
					Pixel[] pixels = new Pixel[size];
					++pos;
					for(int i = 0, j = 0; i < size; i++, j+=3)
					{
						pixels[i] = new Pixel();
						pixels[i].R = Binary.ByteToInt(bytes[(int) (pos+j)]);
						pixels[i].G = Binary.ByteToInt(bytes[(int) (pos+j+1)]);
						pixels[i].B = Binary.ByteToInt(bytes[(int) (pos+j+2)]);
					}
					
					p.setLocalColourTable(pixels);
					pos += size*3 - 1;
				}
				
				
				/// DataBlock
				int codesize = Binary.ByteToInt(bytes[(int) ++pos]); // should conform to the size of the color table size
				System.out.println("Codesize: " + codesize);
				LZWDecoder decoder = new LZWDecoder();
				decoder.setCodeSize(codesize);
				decoder.InitializeDictionary();
				ArrayList<Block> blocks = new ArrayList<Block>();
				int imageLength = 0;
				int i = 0;
				int compressedDataSize = 0;
				/// set blocks
				do {
					imageLength = Binary.ByteToInt(bytes[(int) ++pos]);
					blocks.add(new Block(Binary.copyBytes(bytes, ++pos, imageLength), imageLength));
					compressedDataSize += imageLength;
					i++;
					pos += imageLength - 1;
				}while(imageLength != 0);
				Block[] b = new Block[blocks.size()];
				for(int k = 0; k < blocks.size(); k++) {
					b[k] = blocks.get(k);
				}
				format.setCompressedDataSize(compressedDataSize);
				decoder.setBlocks(b);
				/// end
				decoder.decode();
				Object[] values = decoder.getValues();
				format.setDecompressedDataSize(values.length);
				Integer[] iVals = toInts(values);
				
				Pixel[] pixels =  mapValuesToPixels(iVals);
				return pixels;
				
			}
			//else throw new InvalidFileException 
			blockType = Binary.ByteToInt(bytes[(int) pos]);
		}
		return null;
	}

	private Pixel[] mapValuesToPixels(Integer[] iVals) {
		Pixel[] colorTable;
		Pixel[] arr = new Pixel[iVals.length];
		if(p != null && p.isLCTAvailable() == true) // use local color map
			colorTable = p.getLocalColourTable();
		else
			colorTable = format.getGlobalColourTable();
		for(int i = 0; i < iVals.length; i++) {
			arr[i] = colorTable[iVals[i]];
		}
		return arr;
	}

	private Integer[] toInts(Object[] values) {
		Integer[] arr = new Integer[values.length]; 
		for(int i = 0; i < values.length; i++) {
			 arr[i] = (Integer) values[i];
		}
		return arr;
	}
	
	public String[][] getStringRepresentation() {
		return format.getStringRepresentation();
	}

	public File getFile() {
		// MARTIN Auto-generated method stub
		return null;
	}

}
