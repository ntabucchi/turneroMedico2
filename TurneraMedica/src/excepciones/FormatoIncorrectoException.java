package excepciones;

public class FormatoIncorrectoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatoIncorrectoException(String mensaje) {
        super(mensaje);
    }
	
	@Override
    public String toString() {
        return getMessage();
    }
}
