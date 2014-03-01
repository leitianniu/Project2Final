//******************
//CS 342 Project Two-Minesweeper
//Authors
//Tianniu Lei
//Ryan Szymkiewicz
//scoreBoard.java
//implements the scoreboard functionality of game
//******************

import java.awt.*;

import javax.naming.InitialContext;
import javax.rmi.CORBA.Tie;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Timer;

@SuppressWarnings("serial")
public class scoreBoard implements Serializable {
	static String FILENAME = new String("highscore.bin");

	private String name = new String();
	private int timeElapsed = new Integer(0);

	private ArrayList<score> highScores = new ArrayList<score>();

	public scoreBoard() throws IOException {
		File file = new File(FILENAME);
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public void save() throws IOException {

		FileOutputStream fos = new FileOutputStream(FILENAME);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(highScores);
		oos.close();
	}

	public void load() throws IOException, ClassNotFoundException {

		FileInputStream fis = new FileInputStream(FILENAME);
		ObjectInputStream ois = new ObjectInputStream(fis);
		highScores = (ArrayList<score>) ois.readObject();
		ois.close();
	}

	private String timeToString(int currTime) {
		int minutes = currTime / 60;
		int seconds = currTime - (minutes * 60);
		String minString = String.valueOf(minutes);
		String secString = String.valueOf(seconds);
		String timeString = minString.concat(" minute(s) ");
		timeString = timeString.concat(secString);
		timeString = timeString.concat(" second(s) ");
		return timeString;
	}

	public String printHighScores(scoreBoard scoreboard) {
		String text = new String();

		for (int i = 0; i < highScores.size(); i++) {
			System.out.println((i + 1) + ". " + highScores.get(i).getName()
					+ " " + timeToString(highScores.get(i).getTimeElapsed()));
			text = text.concat(new String(highScores.get(i).getName() + " "
					+ timeToString(highScores.get(i).getTimeElapsed()))
					+ "\n");
		}
		return text;
	}

	public String printHighScores(ArrayList<score> highScores2) {
		String text = new String();
		for (int i = 0; i < highScores2.size(); i++) {
			System.out.println((i + 1) + ". " + highScores2.get(i).getName()
					+ " " + timeToString(highScores2.get(i).getTimeElapsed()));
			text = text.concat(new String(highScores2.get(i).getName()
					+ timeToString(highScores2.get(i).getTimeElapsed()))
					+ "\n");
		}
		return text;
	}

	public void addHighScore(String nameinput, int timeTaken)
			throws ClassNotFoundException, IOException {

		// load from the save file
		try {
			load();
		} catch (Exception EOFException) {
		}

		// add the new element
		highScores.add(new score(nameinput, timeTaken));
		// sort the array list
		Collections.sort(highScores);
		// keep the first 10 results and delete the rest
		// (should only happen once each time on the 11th addHighScore)
		if (highScores.size() >= 10) {
			for (int i = 10; i < highScores.size(); i++) {
				highScores.remove(i); // remove all index > 9 until end of list
			}
		}
		// print high scores list to console for debugging
		printHighScores(highScores);
		// save the new list
		save();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(int timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public int getHighScores() {
		int n = highScores.size();
		return highScores.get(n - 1).getTimeElapsed();
	}

	public String getHighestScore() {
		return highScores.get(0).getName();
	}

	public void clearArray() {
		highScores.clear();
	}

	public void clearHighScores() throws Exception {
		File newfile = new File(FILENAME);
		newfile.createNewFile();
		highScores.clear();
		save();
	}

	public int getSize() {
		return highScores.size();
	}

}