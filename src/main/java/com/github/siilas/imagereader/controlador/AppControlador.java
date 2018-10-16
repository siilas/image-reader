package com.github.siilas.imagereader.controlador;

import java.util.Scanner;

import com.github.siilas.imagereader.leitor.LeitorArquivo;
import com.github.siilas.imagereader.leitor.LeitorDiretorio;
import com.github.siilas.imagereader.utils.FileUtils;
import com.github.siilas.imagereader.utils.StringUtils;

public class AppControlador {

	public void run() {
		Scanner input = new Scanner(System.in);

		boolean diretorio = perguntarDiretorioOuPasta(input);
		String nomeCidade = perguntarNomeCidade(input);
		String caminho = perguntarCaminho(input, diretorio);
		
		if (diretorio) {
			new LeitorDiretorio().ler(caminho, nomeCidade);
		} else {
			new LeitorArquivo().ler(caminho, nomeCidade);
		}
		
		System.out.println("Gerando PDFs...");
		//new GeradorDePDF().gerar(caminho, nomeCidade);
		
		System.out.println("Concluído!");
	}

	private boolean perguntarDiretorioOuPasta(Scanner input) {
		int modo = 0;
		System.out.println("O que você deseja fazer?");
		System.out.println("1 - Ler um diretório de arquivos");
		System.out.println("2 - Ler um único arquivo");
		do {
			modo = input.nextInt();
			if (modo != 1 && modo != 2) {
				System.out.println("Valor inválido");
			}
		} while (modo != 1 && modo != 2);
		return modo == 1;
	}

	private String perguntarNomeCidade(Scanner input) {
		String nomeCidade = "";
		System.out.println("Qual o nome da cidade que está nos arquivos?");
		do {
			nomeCidade = input.next();
			if (StringUtils.isBlank(nomeCidade)) {
				System.out.println("Valor inválido");
			}
		} while (StringUtils.isBlank(nomeCidade));
		return nomeCidade;
	}

	private String perguntarCaminho(Scanner input, boolean diretorio) {
		String caminho = "";
		if (diretorio) {
			System.out.println("Qual o diretório que você deseja ler?");
		} else {
			System.out.println("Qual o arquivo que você deseja ler?");
		}
		do {
			caminho = input.next();
			if (diretorio && !FileUtils.diretorioValido(caminho)) {
				caminho = "";
				System.out.println("Valor inválido");
			} else if (!diretorio && !FileUtils.arquivoValido(caminho)) {
				caminho = "";
				System.out.println("Valor inválido");
			}
		} while (StringUtils.isBlank(caminho));
		return caminho;
	}

}
