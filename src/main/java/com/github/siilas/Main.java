package com.github.siilas;

import com.github.siilas.controlador.AppControlador;

public class Main {

	public static void main(String[] args) {
		try {
			new AppControlador().run();
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
}
