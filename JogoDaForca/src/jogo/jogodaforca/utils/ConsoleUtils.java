package jogo.jogodaforca.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jogo.jogodaforca.CaractereInvalidoException;
import jogo.jogodaforca.JogoDaForcaException;

/**
 * M�todos utilit�rios de console
 */
public class ConsoleUtils {

	/**
	 * L� uma letra do console
	 * 
	 * @return Letra lida
	 * @throws CaractereInvalidoException
	 *             Pode ser lan�ada em tr�s casos: se nada for digitado, se mais
	 *             de uma letra for digitada e se o caractere digitado n�o for
	 *             uma letra de A a Z
	 * @throws {@link IOException} Se houver algum problema de I/O relacionado �
	 *         leitura do console
	 */
	public static char lerLetra() throws JogoDaForcaException {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			
			// L� a linha do teclado
			String linha = reader.readLine();

			// A linha n�o pode ser vazia
			if (linha.trim().isEmpty()) {
				throw new CaractereInvalidoException("Nenhuma letra foi digitada");
			}

			// A linha n�o pode conter mais de um caractere
			if (linha.length() > 1) {
				throw new CaractereInvalidoException("Apenas uma letra deve ser digitada");
			}

			// Extrai o caractere digitado, convertida para mai�scula
			char letra = linha.toUpperCase().charAt(0);

			// O caractere deve ser uma letra
			if (!Character.isLetter(letra)) {
				throw new CaractereInvalidoException("Apenas letras devem ser digitadas");
			}

			return letra;

		} catch (IOException e) {
			throw new JogoDaForcaException("Problema ao ler caractere do teclado", e);
		}
	}
}
