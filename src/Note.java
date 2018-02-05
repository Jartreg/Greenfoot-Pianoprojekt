public class Note {
	private static final String[] NAMES = {"c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"};

	private final String name;
	private final int midiNumber;

	public Note(int midiNumber) {
		this.midiNumber = midiNumber;
		this.name = NAMES[midiNumber % 12];
	}

	public boolean isBlack() {
		return name.endsWith("#");
	}

	public int getMidiNumber() {
		return midiNumber;
	}

	public String getName() {
		return name;
	}

	public int getOctave() {
		return (midiNumber / 12 - 1);
	}
}
