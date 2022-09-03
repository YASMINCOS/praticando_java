package agenda.agerndacontatos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Representa a agenda de contatos
 */
public class Agenda {

	/**
	 * Map de contatos. A chave � o nome do contato e o valor � o pr�prio objeto Contato.
	 */
	private Map<String, Contato> contatosMap = new TreeMap<String, Contato>();
	
	/**
	 * Map que organiza os contatos por letras do alfabeto (para facilitar a busca).
	 * A chave � a letra e o valor � uma lista de contatos, cujo nome come�a com a letra especificada
	 * na chave do map.
	 */
	private Map<Character, List<Contato>> contatosPorLetrasMap = new TreeMap<Character, List<Contato>>();
	
	/**
	 * Gerenciador do arquivo que cont�m os registros da agenda
	 */
	private ArquivoAgenda arquivo = new ArquivoAgenda();
	
	/**
	 * Construtor. Ao criar o objeto, os registros cadastrados no arquivo s�o lidos e adicionados � 
	 * agenda (caso existam)
	 * @throws IOException
	 */
	public Agenda() throws IOException {
		// Obt�m os registros cadastrados
		List<Contato> contatos = arquivo.ler();
		
		for (Contato contato : contatos) {
			try {
				// Insere cada contato lido do arquivo na agenda
				inserir(contato);
			} catch (AgendaException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Insere um contato na agenda.
	 * @param contato Contato a ser inserido
	 * @throws AgendaException
	 * @throws IOException
	 */
	public void inserir(Contato contato) throws AgendaException, IOException {
		// Valida os dados do contato
		contato.validarDados();
		
		// Verifica se o nome do contato j� n�o existe
		String nome = contato.getNome();
		if (contatosMap.containsKey(nome)) {
			throw new AgendaException("O contato " + " nome ja existe");
		}
		
		// Adiciona o contato ao map
		contatosMap.put(nome, contato);
		
		// Obt�m a letra inicial do nome do contato e adiciona o contato adequadamente
		// na cole��o que organiza os contatos por letra inicial do nome
		char letraInicial = Character.toUpperCase(nome.charAt(0));
		List<Contato> contatosLetra = contatosPorLetrasMap.get(letraInicial);
		if (contatosLetra == null) {
			contatosLetra = new ArrayList<Contato>();
			contatosPorLetrasMap.put(letraInicial, contatosLetra);
		}
		contatosLetra.add(contato);
		
		// Depois de adicionar o contato nas cole��es, atualiza o arquivo
		arquivo.gravar(contatosMap.values());
	}
	
	/**
	 * Exclui um contato da agenda.
	 * @param nome Nome do contato a ser exclu�do.
	 * @throws AgendaException
	 * @throws IOException
	 */
	public void excluir(String nome) throws AgendaException, IOException {
		// Verifica se o contato realmente existe
		verificarExistenciaContato(nome);
		
		// Obt�m o contato associado ao nome
		Contato contato = obterContato(nome);
		
		// Remove o contato do map
		contatosMap.remove(nome);
		
		// Remove o objeto da lista que est� dentro do Map de contatos por letras
		List<Contato> contatos = contatosPorLetrasMap.get(nome.toUpperCase().charAt(0));
		contatos.remove(contato);
		
		// Se depois da remo��o n�o sobrar nenhum contato com a letra do nome no Map, exclui o registro do Map
		if (contatos.size() == 0) {
			contatosPorLetrasMap.remove(nome.toUpperCase().charAt(0));
		}
		
		// Atualiza o arquivo de contatos
		arquivo.gravar(contatosMap.values());
	}
	
	/**
	 * Altera um contato da agenda.
	 * @param contato Contato a ser alterado.
	 * @throws AgendaException
	 * @throws IOException
	 */
	public void alterar(Contato contato) throws AgendaException, IOException {
		// Valida os dados do contato
		contato.validarDados();
		
		// Verifica se o contato sendo alterado existe realmente (apenas o telefone do contato
		// pode mudar)
		verificarExistenciaContato(contato.getNome());
		
		// Atualiza o arquivo de contatos
		arquivo.gravar(contatosMap.values());
	}
	
	/**
	 * Verifica se um determinado contato j� existe.
	 * @param nome Contato a ser verificado.
	 * @throws AgendaException Lan�ada se o contato n�o existir
	 */
	private void verificarExistenciaContato(String nome) throws AgendaException {
		// O m�todo containsKey() checa se determinada chave existe no map
		if (!contatosMap.containsKey(nome)) {
			throw new AgendaException("Contato " + nome + " nao encontrado");
		}
	}
	
	/**
	 * Obt�m uma lista de contatos que iniciam com determinada letra.
	 * @param letra Letra a ser usada na busca pelos contatos.
	 * @return Lista de contatos que atendem ao crit�rio.
	 */
	public List<Contato> listarContatosPorLetra(char letra) {
		List<Contato> contatos = contatosPorLetrasMap.get(Character.toUpperCase(letra));
		if (contatos == null) {
			return new ArrayList<Contato>();
		}
		return contatos;
	}
	
	/**
	 * Obt�m uma lista de contatos com base em parte do nome
	 * @param parteNome Parte do nome a ser procurada
	 * @return Lista de contatos que atendem ao crit�rio
	 */
	public List<Contato> listarContatosPorParteNome(String parteNome) {
		// Procura por contatos que iniciam ou terminam por 0 ou mais caracteres e possuem, no meio,
		// a string fornecida como par�metro
		String regex = "\\w*" + parteNome + "\\w*";
		
		// A flag CASE_INSENSITIVE garante que a compara��o � feita desconsiderando mai�sculas e
		// min�sculas
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		
		// Lista para armazenar os contatos que atendem aos crit�rios
		List<Contato> contatosEncontrados = new ArrayList<Contato>();
		
		// Lista dos contatos existentes atualmente. O m�todo values() retorna os valores do map
		// em forma de uma collection
		Collection<Contato> contatosCadastrados = contatosMap.values();
		
		// Itera sobre os contatos cadastrados procurando pelo padr�o fornecido e os adiciona na 
		// lista que ser� retornada
		for (Contato contato : contatosCadastrados) {
			Matcher m = p.matcher(contato.getNome());
			if (m.matches()) {
				contatosEncontrados.add(contato);
			}
		}
		
		return contatosEncontrados;
	}
	
	/**
	 * Obt�m o objeto do contato cadastrado a partir de um nome
	 * @param nome Nome do contato
	 * @return Objeto que representa o contato
	 */
	public Contato obterContato(String nome) {
		return contatosMap.get(nome);
	}
}
