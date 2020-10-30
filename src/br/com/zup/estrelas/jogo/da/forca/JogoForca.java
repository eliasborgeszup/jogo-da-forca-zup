package br.com.zup.estrelas.jogo.da.forca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class JogoForca {
	private static final int QUANTIDADE_TENTATIVAS = 6;

	private static final String MENSAGEM_AGRADECIMENTO = "Obrigado volte sempre :D";

	private static final String PALAVRAS_ESTRELAS = "Estrelas\n" + "Fora da Caixa\n" + "Jo„o\n" + "Raphaela\n"
			+ "Maga Alonso\n" + "Eliseu\n" + "Dani\n" + "Gilmar\n" + "Alexandre\n" + "Zupper\n" + "Stackoverflow\n"
			+ "Zupper ajuda Zupper\n" + "Java\n" + "Algoritmo\n" + "FunÁ„o\n" + "Vetor\n" + "Regex\n" + "Collator";

	private static final String NOME_DUCUMENTO_TEXTO = "palavras.txt";

	private static final String LETRA_INVALIDA = "\nLetra invalida :(\n";

	private static final String MENSAGEM_GAME_OVER = "Infelizmente n„o foi desta vez :/\nMas n„o se preocupe estaremos sempre aqui, volte sempre :)";

	private static final String REGEX_VERIFICA_LETRA_MAIUSCULA_MINISCULA = ("[A-Za-z·‡‚„ÈËÍÌÔÛÙıˆ˙ÁÒ¡¿¬√…»Õœ”‘’÷⁄«— ]*");

	private static final String MENSAGEM_BEM_VINDO = (" _______________________________________________________\n")
			+ ("|                                                       |\n")
			+ ("|            SEJA BEM VINDO AO JOGO DA FORCA            |\n")
			+ ("|             TEMATICO AO PROJETO ESTRELAS              |\n")
			+ ("|_______________________________________________________|\n\n");

	private static final String MENSAGEM_JOGO_ESTRELAS = (" __________________________\n")
			+ ("|                          |\n") + ("| JOGO DA FORCA [ESTRELAS] |\n")
			+ ("|__________________________|\n\n");

	private static final String MENSAGEM_CONGRATULATIONS = ("   _________________________\n")
			+ ("  |                         |\n") + ("  |  ParabÍns vocÍ ganhou   |\n")
			+ ("  |            :)           |\n") + ("  |_________________________|\n\n");

	public static void verificarExistenciaArquivo() {
		File file = new File(NOME_DUCUMENTO_TEXTO);

		boolean verificaExistenciaArquivo = file.exists();
		boolean buscarQuantidadeLinhasArquivoTexto = buscarQuantidadeLinhasArquivo() == 0;

		if (!verificaExistenciaArquivo || buscarQuantidadeLinhasArquivoTexto) {
			criarArquivo();
		}
	}

	public static void criarArquivo() {
		try {
			FileWriter writer = new FileWriter(NOME_DUCUMENTO_TEXTO);

			writer.write(PALAVRAS_ESTRELAS);

			System.out.printf("%s criado com sucesso!\n", NOME_DUCUMENTO_TEXTO);
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String buscarPalavraSorteada() {
		String linha = null;
		
		//TODO: Criar metodos claros para deixar try mais limpo
		try {
			int quantidadeLinhasArquivo = buscarQuantidadeLinhasArquivo();
			int contadorLinhaSorteada = 0, linhaSorteada = sortearLinhaDoArquivo(quantidadeLinhasArquivo);

			FileReader selecionaArquivo = new FileReader(NOME_DUCUMENTO_TEXTO);
			BufferedReader leitorArquivo = new BufferedReader(selecionaArquivo);

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
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return linha;
	}

	public static int buscarQuantidadeLinhasArquivo() {
		int quantidadeLinhas = 0;

		try {
			FileReader selecionaArquivo = new FileReader(NOME_DUCUMENTO_TEXTO);
			BufferedReader leitorArquivo = new BufferedReader(selecionaArquivo);

			while (leitorArquivo.readLine() != null) {
				quantidadeLinhas += 1;
			}
			selecionaArquivo.close();
			leitorArquivo.close();

			return quantidadeLinhas;
		} catch (IOException e) {
			return quantidadeLinhas;
		}

	}

	public static int sortearLinhaDoArquivo(int quantidadeLinhas) {
		return new Random().nextInt(quantidadeLinhas);
	}

	// TODO: Encontrar nomes de constantes que representa 0, 1 e 2 respectivamente
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

	public static int calculaQuantidadeTentativas(int quantidadeErros, int contadorLetraCerta) {
		if (contadorLetraCerta == 0 && quantidadeErros < QUANTIDADE_TENTATIVAS) {
			quantidadeErros++;

			int somaQuantidadeTentativasRestantes = QUANTIDADE_TENTATIVAS - quantidadeErros;

			System.out.printf("Ops.. Letra errada, %d / %d tentiva(s)\n", QUANTIDADE_TENTATIVAS,
					somaQuantidadeTentativasRestantes);
		}

		return quantidadeErros;
	}

	public static boolean verificarLetraJaDigitada(char[] letrasDigitadas, char letraDigitada) {
		Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		for (int i = 0; i < letrasDigitadas.length; i++) {
			String letrasDigitadasString = Character.toString(letrasDigitadas[i]);
			String letraDigitadaString = Character.toString(letraDigitada);

			boolean verificaLetraDigitadaIgualLetrasDigitadas = collator.compare(letrasDigitadasString,
					letraDigitadaString) == 0;

			if (verificaLetraDigitadaIgualLetrasDigitadas) {
				return true;
			}
		}
		return false;
	}

	public static void imprimirLetrasDigitadas(char[] guardarLetrasDigitadas) {
		System.out.print("\n\nLetras digitadas:");
		for (int i = 0; i < guardarLetrasDigitadas.length; i++) {
			System.out.print(" " + guardarLetrasDigitadas[i]);
		}
	}

	public static boolean verificaConclusaoPalavra(String palavraAlvo, char[] controladorPalavra) {
		int contadorLetrasAcertada = 0;

		for (int i = 0; i < palavraAlvo.length(); i++) {
			boolean letraDigitada = controladorPalavra[i] == 1;
			boolean espacoPalavra = controladorPalavra[i] == 2;
			boolean verificaLetrasCompletadas = letraDigitada || espacoPalavra;

			if (verificaLetrasCompletadas) {
				contadorLetrasAcertada++;
			}
		}

		boolean verificaPalavraAlvoConcluida = contadorLetrasAcertada == palavraAlvo.length();

		if (verificaPalavraAlvoConcluida) {
			return true;
		}

		return false;
	}

	public static void adicionarTexto() throws IOException {
		Scanner teclado = new Scanner(System.in);

		System.out.println(MENSAGEM_CONGRATULATIONS);

		System.out.println("Como bonus vocÍ pode adicionar uma palavra no quebra cabeÁa");

		System.out.print("Digite a palavra que deseja adicionar: ");
		String palavraDigitada = teclado.nextLine();

		while (!verificarExistenciaDaPalavraNoDocumento(palavraDigitada)) {
			System.out.println("Ops.. a palavra digitada j· existe, ou possui caracteres especiais invalidos");
			System.out.print("Digite novamente: ");
			palavraDigitada = teclado.nextLine();
		}

		teclado.close();

		try {
			adicionarPalavraArquivo(palavraDigitada);

			System.out.printf("\n\nPalavra %s salva com sucesso! \n\n", palavraDigitada);

			System.out.println(MENSAGEM_AGRADECIMENTO);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void adicionarPalavraArquivo(String palavraDigitada) throws IOException {
		FileWriter writer = new FileWriter(NOME_DUCUMENTO_TEXTO, true);

		writer.append("\n" + palavraDigitada);

		writer.close();
	}

	//TODO: Realizar tratamento de exces„o, alterar nome de variaveis auxiliares
	public static boolean verificarExistenciaDaPalavraNoDocumento(String palavraDigitada) throws IOException {

		if (!verificarExistenciaCaractereInvalido(palavraDigitada)) {
			return false;
		}

		FileReader selecionaArquivo = new FileReader(NOME_DUCUMENTO_TEXTO);
		BufferedReader leitorArquivo = new BufferedReader(selecionaArquivo);

		Collator collator = Collator.getInstance(new Locale("pt", "BR"));
		collator.setStrength(Collator.PRIMARY);

		int quantidadeLinhasArquivo = buscarQuantidadeLinhasArquivo();

		String[] linha = new String[quantidadeLinhasArquivo += 2];

		int contadorLinha = 0;
		String auxLinha;
		while ((auxLinha = leitorArquivo.readLine()) != null) {
			linha[contadorLinha] = auxLinha;
			boolean comparaNegativaLinhaArquivoComPalavraDigitada = collator.compare(linha[contadorLinha],
					palavraDigitada) == 0;

			if (comparaNegativaLinhaArquivoComPalavraDigitada) {
				selecionaArquivo.close();
				leitorArquivo.close();
				return false;
			}
			contadorLinha++;
		}

		selecionaArquivo.close();
		leitorArquivo.close();
		return true;
	}

	public static boolean verificarExistenciaCaractereInvalido(String letraDigitada) {
		if (!letraDigitada.matches(REGEX_VERIFICA_LETRA_MAIUSCULA_MINISCULA)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		Scanner teclado = new Scanner(System.in);

		verificarExistenciaArquivo();

		String palavraAlvo = buscarPalavraSorteada();

		char[] guardarLetrasDigitadas = new char[palavraAlvo.length() + QUANTIDADE_TENTATIVAS];
		char[] controladorPalavra = new char[palavraAlvo.length()];
		char letraDigitada = 0;

		int quantidadeErros = 0, posicaoLetrasDigitadas = 0;

		System.out.println(MENSAGEM_BEM_VINDO);

		do {
			if (verificaConclusaoPalavra(palavraAlvo, controladorPalavra)) {
				System.out.printf("\n\n         [ %s ]\n\n", palavraAlvo);
				adicionarTexto();
				teclado.close();
				return;
			}

			System.out.println(MENSAGEM_JOGO_ESTRELAS);

			estruturaForca(palavraAlvo, controladorPalavra);

			if (!verificarLetraJaDigitada(guardarLetrasDigitadas, letraDigitada)) {
				if (verificarExistenciaCaractereInvalido(Character.toString(letraDigitada))) {
					guardarLetrasDigitadas[posicaoLetrasDigitadas] = letraDigitada;
					posicaoLetrasDigitadas++;
				}
			}

			imprimirLetrasDigitadas(guardarLetrasDigitadas);

			System.out.print("\nDigite uma letra:");
			letraDigitada = teclado.next().charAt(0);

			int contadorLetraCerta = verificaLetraDigitada(palavraAlvo, letraDigitada, controladorPalavra);

			if (!verificarLetraJaDigitada(guardarLetrasDigitadas, letraDigitada)) {
				if (verificarExistenciaCaractereInvalido(Character.toString(letraDigitada))) {
					quantidadeErros = calculaQuantidadeTentativas(quantidadeErros, contadorLetraCerta);
				} else {
					System.out.println(LETRA_INVALIDA);
				}
			} else {
				System.out.printf("Letra [%s] j· foi digitada\n\n", letraDigitada);
			}

		} while (quantidadeErros < QUANTIDADE_TENTATIVAS);

		System.out.printf("\nPalavra correta: %s\n\n", palavraAlvo);
		System.out.println(MENSAGEM_GAME_OVER);

		teclado.close();
	}

}
