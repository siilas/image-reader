package com.github.siilas.leitor;

import java.io.File;

import com.github.siilas.arquivo.RenomeadorDeArquivo;

public class LeitorDiretorio {

	public void ler(String caminho, String nomeCidade) {
		RenomeadorDeArquivo renomeador = new RenomeadorDeArquivo();
		File diretorio = new File(caminho);
		for (File arquivo : diretorio.listFiles()) {
			renomeador.renomear(arquivo, nomeCidade);
		}
	}

}
