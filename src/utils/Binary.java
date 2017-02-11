package utils;


///*** Note: Just positive values are permitted!!!
///*** Negative values won't be translated correctly because 2's complement is not implemented!!!

public class Binary {



	private static final int BYTE = 8;

	// / ** Byte methods
	public static byte[] copyBytes(byte[] b, long from, long num) {
		byte[] bytes = new byte[(int) num];
		for (int i = (int) from; i < from + num; i++) {
			bytes[(int) (i - from)] = b[(int) (i)];
		}
		return bytes;
	}

	public static boolean isBitSet(byte b, long pos)
			throws IndexOutOfBoundsException {
		if (pos < 0 || pos > BYTE)
			throw new IndexOutOfBoundsException("Bit position is not in range");
			
		b >>>= pos;

		int res = b & 0x1;
		if (res == 1)
			return true;

		return false;

	}

	public static boolean[] getBits(byte b, long from, long num)
			throws IndexOutOfBoundsException {
		if (from + num > BYTE || from + num < 1)
			throw new IndexOutOfBoundsException("Bit position is not in range");
		boolean[] bits = new boolean[(int) num];
		b = (byte) (b >>> from);

		for (int i = 0; i < num; i++) {
			int l = b & 0x1;
			bits[(int) (i)] = l == 0 ? false : true;
			b >>>= 1;
		}
		return bits;
	}

	public static int ByteToInt(byte b) {
		int num = 0;
		for (int i = 0; i < BYTE; i++) {
			num += (b & 0x01) == 1 ? 1 * Math.pow(2, i) : 0;
			b >>>= 1;
		}
		return num;
	}

	public static int BitToInt(boolean[] b) {
		int num = 0;
		for (int i = 0; i < b.length; i++) {
			num += (b[(int) i] == true) ? Math.pow(2, i) : 0;
		}
		return num;
	}

	public static int ByteToInt(byte[] b, long from, long num) {
		int val = 0;
		for(long i = from; i < num+from; i++)  { 
			for(long j = (i - from)*BYTE; j < (i-from+1)*BYTE; j++) {
				val += (b[(int) (i)] & 0x01) == 1 ? 1 * Math.pow(2, j) : 0;
				b[(int) (i)] >>>= 1; 
			}
		}
		return val;
	}

	public static boolean equals(byte b, byte i) {
		int res = b^i;
		
		boolean bresult = (res == 0) ? true : false;
		return bresult;
	}

	public static int getFirstByte(int valNext) {
		boolean[] arr = new boolean[BYTE];
		int i = 0;
		while(valNext > 0) {
			valNext = valNext / 2;
			arr[i++] = valNext % 2 == 1 ? true : false;
		}
		return BitToInt(arr);
	}
}
