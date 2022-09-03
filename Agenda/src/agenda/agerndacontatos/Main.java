package agenda.agerndacontatos;

import java.io.IOException;

/**
 * Classe que lan�a a aplica��o
 */
public class Main {

	/**
	 * M�todo main
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// Instancia a aplica��o e a inicia
		Aplicacao aplicacao = new Aplicacao();
		aplicacao.iniciar();
	}
}

