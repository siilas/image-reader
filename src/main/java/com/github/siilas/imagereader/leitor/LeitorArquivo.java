package com.github.siilas.imagereader.leitor;

import java.io.File;

import com.github.siilas.imagereader.arquivo.RenomeadorDeArquivo;

public class LeitorArquivo {

	public void ler(String caminho, String nomeCidade) {
		File arquivo = new File(caminho);
		RenomeadorDeArquivo renomeador = new RenomeadorDeArquivo();
		renomeador.init();
		renomeador.renomear(arquivo, nomeCidade);
		renomeador.end();
	}

}
