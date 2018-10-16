package com.github.siilas.imagereader.arquivo;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixReadMem;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.springframework.util.ResourceUtils;

public class RenomeadorDeArquivo {

	private static final String DATAPATH = "classpath:infos";
	private static final String LANGUAGE = "por";

	private TessBaseAPI api;
	
	public void init() {
        try {
            api = new TessBaseAPI();
            api.Init(ResourceUtils.getFile(DATAPATH).getPath(), LANGUAGE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Erro ao inicializar leitor de imagens");
        }
	}
	
	public void end() {
	    api.End();
	}

	public void renomear(File arquivo, String nomeCidade) {
	    PIX image = prepararImagem(arquivo);
	    api.SetImage(image);
        BytePointer outText = api.GetUTF8Text();
		String texto = outText.getString();
		outText.deallocate();
        pixDestroy(image);
		String poste = getPoste(texto);
		String newName = getNovoNome(arquivo, poste, nomeCidade);
		arquivo.renameTo(new File(newName));
	}
	
	private PIX prepararImagem(File arquivo) {
	    try {
    	    BufferedImage image = ImageIO.read(arquivo);
    	    /*for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    Color color = new Color(image.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getBlue();
                    int blue = color.getGreen();
                    if (red + green + blue > 180) {
                        red = green = blue = 0; // Black
                    } else {
                        red = green = blue = 255; // White
                    }
                    Color newColor = new Color(red, green, blue);
                    image.setRGB(x, y, newColor.getRGB());
                }
    	    }*/
    	    RescaleOp op = new RescaleOp(1.2f, 15, null);
    	    image = op.filter(image, image);
    	    ByteArrayOutputStream output = new ByteArrayOutputStream();
    	    ImageIO.write(image, "jpg", output);
    	    byte[] bytes = output.toByteArray();
    	    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    	    return pixReadMem(buffer, bytes.length);
	    } catch (Exception e) {
	        throw new RuntimeException("Erro ao preparar imagens");
        }
	}

	private String getPoste(String texto) {
		try {
		    String[] buscas = { "POSTE: ", "POSTE:", "POSTE" };
		    String novoNome = "";
		    for (String busca : buscas) {
		        int index = texto.indexOf(busca);
		        if (index != (-1)) {
    		        String trecho = texto.substring(index).replace(busca, "");
    	            for (int i = 0; i < trecho.length(); i++) {
    	                char caracter = trecho.charAt(i);
    	                if (Character.isDigit(caracter)) {
    	                    novoNome = novoNome + caracter;
    	                }
    	            }
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
		System.out.println("Novo nome: " + novoNome);
		return arquivo
				.getAbsolutePath()
				.replace(arquivo.getName(), novoNome);
	}
	
}
