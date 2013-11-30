package com.ftbmasters.IO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;

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
			BufferedWriter stream;
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
				stream = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file)));
				stream.write("1.0>in the beginning was the ale.");
				stream.close();
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

			try {
				String[] tempContents = scanner.nextLine().split(">");
				contents = ChatColor.WHITE + "<" + ChatColor.GOLD + tempContents[0] + ChatColor.WHITE + ">" + ChatColor.YELLOW + tempContents[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				Bukkit.getLogger().log(Level.SEVERE, "motd.cfg format invalid!");
			}

		} else {
			contents = null;
		}
	}

}
