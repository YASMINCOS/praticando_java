package jogo.jogodaforca;

/**
 * Possui o metodo main() que inicia o jogo
 */
public class Main {

	/**
	 * Metodo main
	 * @param args Argumentos de linha de comando
	 * @throws Exception Lanca excecces inesperadas
	 */
	public static void main(String[] args) throws Exception {
		
		// Instancia o jogo e inicia
		Jogo jogo = new Jogo();
		jogo.iniciar();
	}
}
