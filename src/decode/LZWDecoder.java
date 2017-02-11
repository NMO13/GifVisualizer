package decode;

import gif.Block;

import java.util.ArrayList;

import utils.Binary;
import utils.MyBitSet;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class LZWDecoder
{
	private int initialCodeSize;
	private Hashtable dictionary;
	private Block[] dataBlocks;

	ArrayList<Integer> output = new ArrayList<Integer>();
	private int firstCodeSize;
	private int curByte = 0;
	private int curBit = 0;
	int blockCounter = 0;

	private int getCode(byte[] stream, int blockSize) {
		MyBitSet set = new MyBitSet(firstCodeSize);
		for(int i = 0; i < firstCodeSize; i++) {
			boolean val = false;
			///////////
			if(curByte < blockSize) {
				val = (stream[curByte] & 0x01) == 1 ? true : false;
				stream[curByte] >>>= 1;
			}
			else {
				blockCounter++;
				if(blockCounter + 1 < dataBlocks.length) { // fill with bits of next block's 1. byte
					stream = dataBlocks[blockCounter].getBlock();
					curByte = 0;
					//blockSize = dataBlocks[blockCounter].getLength();
					i--;
					continue;
					//val = (stream[0] & 0x01) == 1 ? true : false;
					//stream[0] >>>= 1;
				}else
					System.out.println("fill zero");
				// else fill with zeros
			}
			//////////////
			set.setBit(val, i);
			if(curBit == 7)
			{
				if(curByte == blockSize - 1 && i == firstCodeSize - 1) {
					curByte = 0;
					blockCounter++;
				}
				else
					curByte++;
				curBit = -1;
			}
			curBit++;
		}
		return set.toInteger();
		
	}
	
	private int initialize() {
		firstCodeSize = initialCodeSize + 1;
		InitializeDictionary();
		int dictPointer = (int) Math.pow(2, initialCodeSize)+2;
		return dictPointer;
	}
	
	// Code from "http://de.wikipedia.org/wiki/Lempel-Ziv-Welch-Algorithmus"
	public void decode() {
		firstCodeSize = initialCodeSize+1;
		int dictPointer = 0;
		int last = getCode(dataBlocks[0].getBlock(), dataBlocks[0].getLength());
		if(last == Math.pow(2, initialCodeSize)) { // clear code
			dictPointer = initialize();
		}
		last = getCode(dataBlocks[0].getBlock(), dataBlocks[0].getLength());
		
		byte[] valLast = (byte[]) dictionary.get(last);
		saveToOutput(valLast);
		
		while(dataBlocks[blockCounter].getLength() != 0) {
			//while(curByte < dataBlocks[blockCounter].getLength()) {
				int next = getCode(dataBlocks[blockCounter].getBlock(), dataBlocks[blockCounter].getLength());
				if(next == Math.pow(2, initialCodeSize) + 1) {
					// end of information
					System.out.println("end of info");
					return;
				}
				else if(next == Math.pow(2, initialCodeSize)) {
					System.out.println("clear code");
					dictPointer = initialize();
					last = getCode(dataBlocks[blockCounter].getBlock(), dataBlocks[blockCounter].getLength());
					valLast = (byte[]) dictionary.get(last);
					saveToOutput(valLast);
					continue;
				}
				byte[] valNext = (byte[]) dictionary.get(next);
				valLast = (byte[]) dictionary.get(last);
				if(dictionary.containsKey(next)) {
					byte[] newArr = new byte[valLast.length+1];
					for(int i = 0; i < valLast.length; i++)
						newArr[i] = valLast[i];
					newArr[valLast.length] = valNext[0];
					dictionary.put(dictPointer, newArr);
				}
				else {
					byte[] newArr = new byte[valLast.length+1];
					for(int i = 0; i < valLast.length; i++)
						newArr[i] = valLast[i];
					newArr[valLast.length] = valLast[0];
					dictionary.put(dictPointer, newArr);
					valNext = newArr;
					next = dictPointer;
				}
				saveToOutput(valNext);
				last = next;
				dictPointer++;
				if(dictPointer == Math.pow(2, firstCodeSize)) {
					firstCodeSize++;
					if(firstCodeSize == 13) {
						firstCodeSize--;
						System.out.println("Dictionary full!");
					}
				}
			
			//blockCounter++;
			//curByte = 0;
			//curBit = 0;
		}
	}
		
	public Object[] getValues() {
		return output.toArray();
	}
	
	private void saveToOutput(byte[] bytes) {
		for(int i = 0; i < bytes.length; i++) {
			int j = Binary.ByteToInt(bytes[i]);
			output.add(j);
		}
	}
	
	public void setCodeSize(int size) {
		initialCodeSize = size;
	}
	
	public void InitializeDictionary() {
		dictionary = new Hashtable();
		int size = (int) Math.pow(2, initialCodeSize);
		for(int i = 0; i < size; i++) {
			byte b = (byte) i;
			byte[] arr = new byte[1];
			arr[0] = b;
			dictionary.put(i, arr);
		}
		
		
	}

	public void setBlocks(Block[] blocks) {
		this.dataBlocks = blocks;
		
	}
}
