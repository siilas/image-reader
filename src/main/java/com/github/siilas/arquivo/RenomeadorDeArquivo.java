package com.github.siilas.arquivo;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class RenomeadorDeArquivo {

	private static final String DATAPATH = "/";
	private static final String LANGUAGE = "por";

	private Tesseract tesseract;

	public RenomeadorDeArquivo() {
		tesseract = new Tesseract();
		tesseract.setDatapath(getClass().getResource(DATAPATH).getPath());
		tesseract.setLanguage(LANGUAGE);
	}

	public void renomear(File arquivo, String nomeCidade) {
		try {
			String texto = tesseract.doOCR(arquivo);
			String poste = getPoste(texto);
			String newName = getNovoNome(arquivo, poste, nomeCidade);
			arquivo.renameTo(new File(newName));
		} catch (TesseractException e) {
			throw new RuntimeException("Erro ao renomear o arquivo");
		}
	}

	private String getPoste(String texto) {
		try {
			String busca = "POSTE: ";
			int index = texto.indexOf(busca);
			String trecho = texto.substring(index).replace(busca, "");
			String novoNome = "";
			for (int i = 0; i < trecho.length(); i++) {
				char caracter = trecho.charAt(i);
				if (Character.isDigit(caracter)) {
					novoNome = novoNome + caracter;
				}
			}
			return novoNome;
		} catch (Exception e) {
			return texto;
		}
	}
	
	private String getNovoNome(File arquivo, String poste, String nomeCidade) {
		String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf("."));
		String novoNome = nomeCidade + "_" + poste + extensao;
		return arquivo
				.getAbsolutePath()
				.replace(arquivo.getName(), novoNome);
	}

}
