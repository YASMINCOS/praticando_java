package jogo.jogodaforca;

/**
 * Representa uma exce��o gen�rica ocorrida no jogo
 */
public class JogoDaForcaException extends Exception {

	/**
	 * @see Exception#Exception(String)
	 */
	public JogoDaForcaException(String message) {
		super(message);
	}
	
	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public JogoDaForcaException(String message, Throwable cause) {
		super(message, cause);
	}
}
