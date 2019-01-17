package controllers;

import java.util.Scanner;

import models.File;

public class FileController {
	
	private File file;
	
	public FileController(File file) {
		setFile(file);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void readSubject() {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		
		do {
			System.out.print("Subject: ");
			file.setSubject(teclado.nextLine());
		} while(file.getSubject().isEmpty());
	}
	
	public void readBody() {
		@SuppressWarnings("resource")
		Scanner teclado = new Scanner(System.in);
		
		do {
			System.out.print("Body: ");
			file.setBody(teclado.nextLine());
		} while(file.getBody().isEmpty());
	}
	
	public void readAll() {
		readSubject();
		readBody();
	}

}
