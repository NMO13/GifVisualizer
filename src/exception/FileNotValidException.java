package exception;

public class FileNotValidException extends Exception {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotValidException(String msg){
	      super(msg);
	    }

	    public FileNotValidException(String msg, Throwable t){
	      super(msg,t);
	    } 
}
