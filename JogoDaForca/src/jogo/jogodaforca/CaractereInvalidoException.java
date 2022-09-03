package jogo.jogodaforca;

/**
 * Exce�ao que indica que o caractere digitado � inv�lido por algu motivo
 */
public class CaractereInvalidoException extends JogoDaForcaException {

	/**
	 * @see JogoDaForcaException#JogoDaForcaException(String)
	 */
	public CaractereInvalidoException(String message) {
		super(message);
	}
}
