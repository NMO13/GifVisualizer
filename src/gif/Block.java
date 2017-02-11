package gif;

public class Block {

	private byte[] block;
	private int length;
	
	public byte[] getBlock() {
		return block;
	}

	public int getLength() {
		return length;
	}

	public Block(byte[] block, int length) {
		this.block = block;
		this.length = length;
	}
}
