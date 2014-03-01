//******************
//CS 342 Project Two-Minesweeper
//Authors
//Tianniu Lei
//Ryan Szymkiewicz
//score.java
//this class stores a single player and game-clear time
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
public class score implements Serializable, Comparable {
	private String name = new String();
	private int timeElapsed = new Integer(0);

	public score(String nameinput, int timeTaken) {
		this.name = nameinput;
		this.timeElapsed = timeTaken;
	}

	// this overrides the compare function in Comparable library
	// helps sort the hand when callling Collections.sort();
	@Override
	public int compareTo(Object o) {
		score s = (score) o;

		int result = compare(s.timeElapsed, timeElapsed);

		return result;
	}

	private static int compare(int a, int b) {
		return b - a;
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

}