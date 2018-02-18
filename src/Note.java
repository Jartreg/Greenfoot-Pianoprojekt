/**
 * Stellt eine Note dar
 */
public class Note {
	private static final String[] NAMES = {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};

	private final String name;
	private final int midiNumber;

	public Note(int midiNumber) {
		this.midiNumber = midiNumber;
		this.name = NAMES[midiNumber % 12];
	}

	/**
	 * Gibt zurück, ob zu dieser Note eine schwarze Taste gehört
	 */
	public boolean isBlack() {
		return name.endsWith("#");
	}

	/**
	 * Gibt die Nummer der Note zurück
	 *
	 * @return die Nummer der Note, wie sie für Midi verwendet werden kann
	 */
	public int getMidiNumber() {
		return midiNumber;
	}

	/**
	 * Gibt den Namen der Note zurück.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die Oktave der Note zurück
	 */
	public int getOctave() {
		return (midiNumber / 12 - 1);
	}
}
