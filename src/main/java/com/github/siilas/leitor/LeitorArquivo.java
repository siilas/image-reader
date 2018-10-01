package com.github.siilas.leitor;

import java.io.File;

import com.github.siilas.arquivo.RenomeadorDeArquivo;

public class LeitorArquivo {

	public void ler(String caminho, String nomeCidade) {
		File arquivo = new File(caminho);
		new RenomeadorDeArquivo().renomear(arquivo, nomeCidade);
	}

}
