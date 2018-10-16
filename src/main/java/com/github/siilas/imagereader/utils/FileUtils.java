package com.github.siilas.imagereader.utils;

import java.io.File;

public final class FileUtils {

	private FileUtils() {
	}
	
	public static boolean diretorioValido(String caminho) {
		File diretorio = new File(caminho);
		return diretorio.exists()
				&& diretorio.isDirectory() 
				&& diretorio.canRead();
	}
	
	public static boolean arquivoValido(String caminho) {
		File arquivo = new File(caminho);
		return arquivo.exists()
				&& !arquivo.isDirectory()
				&& arquivo.canRead(); 
	}
	
	public static boolean isImagem(String nome) {
	    return nome.endsWith(".jpg") || nome.endsWith(".jpeg");
	}
	
}
