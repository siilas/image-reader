package com.github.siilas.imagereader.leitor;

import java.io.File;

import com.github.siilas.imagereader.arquivo.RenomeadorDeArquivo;
import com.github.siilas.imagereader.utils.FileUtils;

public class LeitorDiretorio {

	public void ler(String caminho, String nomeCidade) {
		RenomeadorDeArquivo renomeador = new RenomeadorDeArquivo();
		renomeador.init();
		File diretorio = new File(caminho);
		for (File arquivo : diretorio.listFiles()) {
		    if (!FileUtils.isImagem(arquivo.getName())) {
		        continue;
		    }
		    System.out.println("Renomeando arquivo: " + arquivo.getName());
		    renomeador.renomear(arquivo, nomeCidade);
		}
		renomeador.end();
	}

}
