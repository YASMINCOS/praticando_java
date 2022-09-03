package jogo.jogodavelha;


import jogo.jogodavelha.io.Console;
public class Jogador {


	private String nome;
	
	private char simbolo;
	
	
	
	public Jogador(String nome, char simbolo) {
		this.nome = nome;
		this.simbolo = simbolo;
	}

	public String getNome() {
		return nome;
	}

	public char getSimbolo() {
		return simbolo;
	}
	
	
	public Jogada obterJogada() throws JogadaInvalidaException {
		String str = Console.readString();
		return new Jogada(str);
	}
}
