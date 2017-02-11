package utils;

public class MyBitSet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean set[];
	private int size;
	
	public MyBitSet(int size)
	{
		set = new boolean[size];
		this.size = size;
	}
	
	public MyBitSet(int num, boolean number) {
		//Binary.
	}
	
	public void setBit(boolean value, int pos) throws IndexOutOfBoundsException {
		if(pos < 0 || pos >= size)
			throw new IndexOutOfBoundsException();
		set[pos] = value;
	}
	
	public int toInteger() {
		return Binary.BitToInt(set);
	}

}
