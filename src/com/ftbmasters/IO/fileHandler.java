package com.ftbmasters.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class fileHandler {
	
	File file = new File("");
	public static String contents = null;
	
	public fileHandler(File file) {
		this.file = file;
		
		createFile();
		readFile();
	}
	
	public boolean fileExists() {
		if (this.file.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void createFile() {
		if (!fileExists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void readFile() {
		if (fileExists()) {
			Scanner scanner = null;
			try {
				scanner = new Scanner(this.file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			contents = scanner.nextLine();
		} else {
			contents = null;
		}
	}

}
