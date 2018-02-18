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
	 * Gibt zur�ck, ob zu dieser Note eine schwarze Taste geh�rt
	 */
	public boolean isBlack() {
		return name.endsWith("#");
	}

	/**
	 * Gibt die Nummer der Note zur�ck
	 *
	 * @return die Nummer der Note, wie sie f�r Midi verwendet werden kann
	 */
	public int getMidiNumber() {
		return midiNumber;
	}

	/**
	 * Gibt den Namen der Note zur�ck.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die Oktave der Note zur�ck
	 */
	public int getOctave() {
		return (midiNumber / 12 - 1);
	}
}
