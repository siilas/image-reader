package com.github.siilas.imagereader;

import com.github.siilas.imagereader.controlador.AppControlador;

public class Main {

	public static void main(String[] args) {
		try {
			new AppControlador().run();
		} catch (Exception e) {
		    e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
}
