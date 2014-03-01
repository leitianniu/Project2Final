import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class mine {

	private Integer[] locations = new Integer[100];
	public int[] bombLocs = new int[10];
	public int currentMines = 10;
	public int clearedButtons = 0;

	public void setMines(int numMines) {

		for (int i = 0; i < 100; i++) {
			locations[i] = i;

		}

		ArrayList<Integer> locationsList = new ArrayList<Integer>(
				Arrays.asList(locations));

		Collections.shuffle(locationsList);

		for (int j = 0; j < 10; j++) {
			bombLocs[j] = locationsList.get(j);

		}

	}

	public int[] getMines() {

		return bombLocs;
	}

	public void decMines() {
		currentMines = currentMines - 1;
	}

	public void incMines() {
		currentMines = currentMines + 1;
	}

	public void resetMines() {
		currentMines = 10;
	}

	public int getCurrentMines() {
		return currentMines;
	}

	public void setCurrentMinesZero() {
		currentMines = 0;
	}

	public void incCleared() {
		clearedButtons = clearedButtons + 1;
	}

	public int getCleared() {
		return clearedButtons;
	}

	public void resetCleared() {
		clearedButtons = 0;
	}

}