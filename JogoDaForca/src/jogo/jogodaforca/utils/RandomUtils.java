package jogo.jogodaforca.utils;


/**
 * M�todos utilit�rios relacionados a n�meros rand�micos
 */
public class RandomUtils {

	/**
	 * Gera um n�mero rand�mico entre min (inclusive) e max. O valor m�ximo que pode ser 
	 * gerado ser� max-1
	 * @param min Valor m�nimo do intervalo
	 * @param max Valor m�ximo do intervalo
	 * @return N�mero rand�mico gerado
	 */
	public static int gerarNumeroRandomico(int min, int max) {
		int intervalo = max - min;
		return (int) (Math.random() * intervalo) + min;
	}
	
}
