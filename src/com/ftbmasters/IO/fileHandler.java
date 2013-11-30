package com.ftbmasters.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.ChatColor;

public class fileHandler {
	
	File file = new File("plugins" + File.separator + "Masters" + File.separator + "motd.cfg");
	public static String contents = null;
	
	public fileHandler() {
		createFile();
		readFile();
	}
	
	public boolean fileExists() {
        return this.file.exists();
	}
	
	public void createFile() {
		if (!fileExists()) {
			try {
				file.getParentFile().mkdirs();
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
			
			String[] tempContents = scanner.nextLine().split(">");
			contents = ChatColor.WHITE + "<" + ChatColor.GOLD + tempContents[0] + ChatColor.WHITE + ">" + ChatColor.YELLOW + tempContents[1];
			
		} else {
			contents = null;
		}
	}

}
