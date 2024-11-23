package excepciones;

public class ObjectoDuplicadoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectoDuplicadoException(String message) {
        super(message);
    }
	
	@Override
    public String toString() {
        return getMessage();
    }
}
