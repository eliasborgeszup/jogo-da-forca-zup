import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {
	// Verifica se nomeDocumentoTexto foi criado ou não
	public static void verificarExistenciaArquivo(String nomeDocumentoTexto) throws IOException {
		String nomeArquivoTexto = "palavras.txt";
		File file = new File(nomeArquivoTexto);

		if (!file.exists() || buscarQuantidadeLinhasArquivo(nomeDocumentoTexto) == 0) {
			criarArquivo(nomeDocumentoTexto);
		}
	}

	// Cria nomeDocumentoTexto com algumas palavras inseridas
	public static void criarArquivo(String nomeDocumentoTexto) throws IOException {
		String nomeArquivoTexto = "palavras.txt";
		FileWriter writer = new FileWriter(nomeArquivoTexto);

		String palavras = "Estrelas\n" + "Fora da Caixa\n" + "João\n" + "Raphaela\n" + "Maga Alonso\n" + "Eliseu\n"
				+ "Dani\n" + "Gilmar\n" + "Alexandre\n" + "Zupper\n" + "Stackoverflow\n" + "Zupper ajuda Zupper\n"
				+ "Java\n" + "Algoritmo\n" + "Função\n" + "Vetor\n" + "Regex\n" + "Collator";

		writer.write(palavras);

		System.out.printf("%s criado com sucesso!\n", nomeDocumentoTexto);
		writer.close();
	}

	/*
	 * Ler arquivo de palavras, chamando outros metodos para pegar quantidade de
	 * linhas existentes no arquivo e sortear esta linha e retornar ela
	 */
	public static String buscarPalavraSorteada(String nomeDocumentoTexto) throws IOException {
		int quantidadeLinhasArquivo = buscarQuantidadeLinhasArquivo(nomeDocumentoTexto);
		int contadorLinhaSorteada = 0, linhaSorteada = sortearLinhaDoArquivo(quantidadeLinhasArquivo);

		FileReader selecionaArquivo = new FileReader(nomeDocumentoTexto);
		BufferedReader leitorArquivo = new BufferedReader(selecionaArquivo);

		String linha;
		while ((linha = leitorArquivo.readLine()) != null) {
			if (linhaSorteada == contadorLinhaSorteada) {
				selecionaArquivo.close();
				leitorArquivo.close();
				return linha;
			}
			contadorLinhaSorteada += 1;
		}

		selecionaArquivo.close();
		leitorArquivo.close();
		return linha;
	}

	// Metodo para buscar a quantidade de linhas existentes em um arquivo txt
	public static int buscarQuantidadeLinhasArquivo(String nomeDocumentoTexto) throws IOException {
		FileReader selecionaArquivo = new FileReader(nomeDocumentoTexto);
		BufferedReader leitorArquivo = new BufferedReader(selecionaArquivo);

		int quantidadeLinhas = 0;
		while (leitorArquivo.readLine() != null) {
			quantidadeLinhas += 1;
		}
		selecionaArquivo.close();
		leitorArquivo.close();

		return quantidadeLinhas;
	}

	// Metodo para sortear o numero de linha utilizando o Random
	public static int sortearLinhaDoArquivo(int quantidadeLinhas) {
		Random random = new Random();
		return random.nextInt(quantidadeLinhas);
	}

	// Metodo para criação de estrutura da forca de acordo com a palavra alvo
	public static void estruturaForca(String palavraAlvo, char[] controladorPalavra) {
		for (int i = 0; i < palavraAlvo.length(); i++) {
			if (palavraAlvo.charAt(i) == ' ') {
				controladorPalavra[i] = 2;
			}

			if (controladorPalavra[i] == 0) {
				System.out.print("_ ");
			} else if (controladorPalavra[i] == 1) {
				System.out.print(" " + palavraAlvo.charAt(i) + " ");
			} else if (controladorPalavra[i] == 2) {
				System.out.println(" ");
			}
		}
	}

	/*
	 * Faz uma verificação da letra digitada, compara com letras acentuadas,
	 * minusculas e maiusculas e retorna uma variavel que ajuda em outra função
	 * (calculaQuantidadeTentativas) na contagem de erros, caso
	 */
	public static int verificaLetraDigitada(String palavraSorteada, char letraDigitada, char[] controladorPalavra) {
		Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		int contadorQuantidadeErros = 0;

		for (int i = 0; i < palavraSorteada.length(); i++) {
			String letraConvertidaString = Character.toString(letraDigitada);
			String letraPalavraSorteadaConvertidadeString = Character.toString(palavraSorteada.charAt(i));

			if (collator.compare(letraConvertidaString, letraPalavraSorteadaConvertidadeString) == 0) {
				controladorPalavra[i] = 1;
				contadorQuantidadeErros++;
			}
			if (Character.isWhitespace(palavraSorteada.charAt(i))) {
				controladorPalavra[i] = 2;
			}
		}
		return contadorQuantidadeErros;
	}

	/*
	 * Verifica se a letra digitada foi errada ou verdadeira e retorna a quantidade
	 * de chances que ainda possui
	 */
	public static int calculaQuantidadeTentativas(int quantidadeErros, int contadorQuantidadeErros) {
		int quantidadeTentativas = 6;

		if (contadorQuantidadeErros == 0 && quantidadeErros < quantidadeTentativas) {
			quantidadeErros++;
			System.out.printf("Ops.. Letra errada, %d / %d tentiva(s)\n", quantidadeTentativas,
					quantidadeTentativas - quantidadeErros);
		}

		return quantidadeErros;
	}

	// Verifica se letra digitada já foi digitada anteriormente
	public static boolean verificarLetraJaDigitada(char[] letrasDigitadas, char letraDigitada) {
		Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		for (int i = 0; i < letrasDigitadas.length; i++) {

			String letrasDigitadasString = Character.toString(letrasDigitadas[i]);
			String letraDigitadaString = Character.toString(letraDigitada);
			if (collator.compare(letrasDigitadasString, letraDigitadaString) == 0) {
				return true;
			}
		}
		return false;
	}

	// Faz impressao do array guardarLetrasDigitadas
	public static void imprimirLetrasDigitadas(char[] guardarLetrasDigitadas, char letraDigitada) {
		System.out.print("\n\nLetras digitadas:");
		for (int i = 0; i < guardarLetrasDigitadas.length; i++) {
			System.out.print(" " + guardarLetrasDigitadas[i]);
		}
	}

	// Verifica se a palavra já foi completada
	public static boolean verificaConclusaoPalavra(String palavraAlvo, char[] controladorPalavra) {
		int controlador = 0;

		for (int i = 0; i < palavraAlvo.length(); i++) {

			if (controladorPalavra[i] == 1 || controladorPalavra[i] == 2) {
				controlador++;
			}
			if (controlador == palavraAlvo.length()) {
				return false;
			}
		}
		return true;
	}

	// Adiciona um texto em nomeDocumentoTexto caso consiga completar a palavra
	public static void adicionarTexto(String nomeDocumentoTexto) throws IOException {
		Scanner teclado = new Scanner(System.in);

		FileWriter writer = new FileWriter(nomeDocumentoTexto, true);

		System.out.println("   _________________________");
		System.out.println("  |                         |");
		System.out.println("  |  Parabêns você ganhou   |");
		System.out.println("  |            :)           |");
		System.out.println("  |_________________________|\n\n");

		System.out.println("Como bonus você pode adicionar uma palavra no quebra cabeça");
		System.out.print("Digite a palavra que deseja adicionar: ");
		String palavraDigitada = teclado.nextLine();

		while (verificarExistenciaDaPalavraNoDocumento(palavraDigitada, nomeDocumentoTexto)) {
			System.out.println("Ops.. a palavra digitada já existe, ou possui caracteres especiais invalidos");
			System.out.print("Digite novamente: ");
			palavraDigitada = teclado.nextLine();
		}
		writer.append("\n" + palavraDigitada);

		System.out.println("\n\nObrigado volte sempre :D");

		writer.close();
		teclado.close();
	}

	/*
	 * Verifica se possui aquela palavra no documento, caso possui pede para digitar
	 * outra
	 */
	public static boolean verificarExistenciaDaPalavraNoDocumento(String palavraDigitada, String nomeDocumentoTexto)
			throws IOException {

		if (!verificarCaractereEspecialInvalido(palavraDigitada)) {
			return true;
		}

		FileReader selecionaArquivo = new FileReader(nomeDocumentoTexto);
		BufferedReader leitorArquivo = new BufferedReader(selecionaArquivo);

		Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		int quantidadeLinhasArquivo = buscarQuantidadeLinhasArquivo(nomeDocumentoTexto);

		String[] linha = new String[quantidadeLinhasArquivo += 2];

		int contadorLinha = 0;
		String auxLinha;
		while ((auxLinha = leitorArquivo.readLine()) != null) {
			linha[contadorLinha] = auxLinha;
			if (collator.compare(linha[contadorLinha], palavraDigitada) == 0) {
				selecionaArquivo.close();
				leitorArquivo.close();
				return true;
			}
			contadorLinha++;
		}

		selecionaArquivo.close();
		leitorArquivo.close();
		return false;
	}

	// Verifica se letra ou palavra digitada é apenas caractere especial
	public static boolean verificarCaractereEspecialInvalido(String letraDigitada) {
		if (!letraDigitada.matches("[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*")) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		Scanner teclado = new Scanner(System.in);

		String nomeDocumentoTexto = "palavras.txt";

		verificarExistenciaArquivo(nomeDocumentoTexto);

		String palavraAlvo = buscarPalavraSorteada(nomeDocumentoTexto);

		char[] guardarLetrasDigitadas = new char[palavraAlvo.length() + 6];
		char[] controladorPalavra = new char[palavraAlvo.length()];
		char letraDigitada = 0;

		int quantidadeErros = 0, posicaoLetrasDigitadas = 0;

		System.out.println(" _______________________________________________________");
		System.out.println("|                                                       |");
		System.out.println("|            SEJA BEM VINDO AO JOGO DA FORCA            |");
		System.out.println("|             TEMATICO AO PROJETO ESTRELAS              |");
		System.out.println("|_______________________________________________________|\n\n");

		do {
			if (!verificaConclusaoPalavra(palavraAlvo, controladorPalavra)) {
				System.out.printf("\n\n         [ %s ]\n\n", palavraAlvo);
				adicionarTexto(nomeDocumentoTexto);
				teclado.close();
				return;
			}

			System.out.println(" __________________________");
			System.out.println("|                          |");
			System.out.println("| JOGO DA FORCA [ESTRELAS] |");
			System.out.println("|__________________________|\n\n");

			estruturaForca(palavraAlvo, controladorPalavra);

			if (!verificarLetraJaDigitada(guardarLetrasDigitadas, letraDigitada)) {
				if (verificarCaractereEspecialInvalido(Character.toString(letraDigitada))) {
					guardarLetrasDigitadas[posicaoLetrasDigitadas] = letraDigitada;
					posicaoLetrasDigitadas++;
				}
			}

			imprimirLetrasDigitadas(guardarLetrasDigitadas, letraDigitada);

			System.out.print("\nDigite uma letra:");
			letraDigitada = teclado.next().charAt(0);

			int contadorQuantidadeErros = verificaLetraDigitada(palavraAlvo, letraDigitada, controladorPalavra);

			if (!verificarLetraJaDigitada(guardarLetrasDigitadas, letraDigitada)) {
				if (verificarCaractereEspecialInvalido(Character.toString(letraDigitada))) {
					quantidadeErros = calculaQuantidadeTentativas(quantidadeErros, contadorQuantidadeErros);
				} else {
					System.out.println("\nLetra invalida :(\n");
				}
			}

		} while (quantidadeErros < 6);

		System.out.printf("\nPalavra correta: %s\n\n", palavraAlvo);
		System.out.println(
				"Infelizmente não foi desta vez :/\nMas não se preocupe estaremos sempre aqui, volte sempre :)");
		teclado.close();
	}

}
