package com.github.siilas.imagereader.arquivo;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.github.siilas.imagereader.utils.FileUtils;
import com.github.siilas.imagereader.utils.StringUtils;

public class GeradorDePDF {

	private static final long FILE_SIZE = 9500000;

	public void gerar(String caminho, String nomeCidade) {
		try {
			PDDocument document = new PDDocument();
			File diretorio = new File(caminho);
			long size = 0L;
			String initial = "";
			int count = 0;
			File[] arquivos = diretorio.listFiles();
			for (File arquivo : arquivos) {
				if (!FileUtils.isImagem(arquivo.getName())) {
					continue;
				}
				size += arquivo.length();
				if ("".equals(initial)) {
					initial = arquivo.getName();
				}
				PDPage page = new PDPage();
				document.addPage(page);
				PDPageContentStream contentStream = new PDPageContentStream(document, page);
				PDImageXObject image = PDImageXObject.createFromFile(arquivo.getAbsolutePath(), document);
				contentStream.drawImage(image, 0, 0);
				contentStream.close();
				count++;
				if (size > FILE_SIZE || count == (arquivos.length - 1)) {
					initial = StringUtils.between(initial, nomeCidade + "_", ".");
					String end = StringUtils.between(arquivo.getName(), nomeCidade + "_", ".");
					document.save(caminho + "/" + nomeCidade + "_" 
							+ initial + "_" + end + ".pdf");
					document.close();
					document = new PDDocument();
					size = 0L;
					initial = "";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao gerar PDF");
		}
	}

}
