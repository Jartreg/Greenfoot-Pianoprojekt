import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import javax.sound.midi.*; // Für Midi
import javax.swing.*; // Für JOptionPane

public class Piano extends World {
	private static final GreenfootImage background = new GreenfootImage("wood.jpg");

	private int currentOctave;

	private Synthesizer synthesizer;
	private MidiChannel channel;
	private Instrument instrument;

	public Piano() {
		super(background.getWidth(), background.getHeight(), 1);

		setBackground(background);
		setPaintOrder(TasteSchwarz.class, TasteWeiss.class);

		setupSynthesizer(); // Synthesizer erstellen
		currentOctave = synthesizer == null ? 2 : 4; // Oktave festlegen
		buildPiano(); // Klaviatur erstellen

		// Benutzungshinweise unter der Klaviatur
		addObject(new InfoText(), 0, 0);

		// Sarten, um tastatureingabe zu ermöglichen
		Greenfoot.setSpeed(50);
		Greenfoot.start();
	}

	/**
	 * Erstellt den Synthesizer
	 *
	 * Wenn Midi nicht verfügbar ist, ist synthesizer = null
	 */
	private void setupSynthesizer() {
		try {
			synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();

			channel = synthesizer.getChannels()[0];

			instrument = synthesizer.getLoadedInstruments()[0]; // Standardinstrument
		} catch (MidiUnavailableException e) { // Geht nicht :(
			synthesizer = null;
		}
	}

	/**
	 * Erstellt die Klaviatur
	 */
	private void buildPiano() {
		String[] keys = {
				"a", "w", "s", "e", "d", "f", "t", "g", "z", "h", "u", "j", "k", "o", "l", "p", "ö", "ä", "+", "#", "backspace", "enter"
		};

		int startNote = (currentOctave + 1) * 12;
		int keyWidth = new GreenfootImage("white-key.png").getWidth();
		int posX = 0;

		// Setzt die Tasten von links nach rechts, bis der Weltrand erreicht wurde
		// Nach jeder weißen Note wird posX erhöht, um die nächste Note daneben zu platzieren.
		// Schwarze Noten spielen für die Platzierung der weißen Noten also keine Rolle.
		// Zudem werden weiße Note um eine halbe Breite nach rechts verschoben,
		// Damit schwarze No
		// ten in der Mitte sind
		for (int i = 0; posX <= getWidth(); i++) {
			Note note = new Note(i + startNote);
			Taste taste = note.isBlack() ? new TasteSchwarz(note, keys[i]) : new TasteWeiss(note, keys[i]);

			int x = posX;
			if (!note.isBlack()) {
				posX += keyWidth;
				x += keyWidth / 2;
			}

			// Die halbe Höhe als y-Position, damit die obere Kante der Note am oberen Weltrand ist
			addObject(taste, x, taste.getImage().getHeight() / 2);
		}
	}

	@Override
	public void act() {
		if (synthesizer == null) {
			return; // Kein Midi :(
		}

		String key = Greenfoot.getKey();
		if (key != null) {
			switch (key) {
				case "left":
					if (currentOctave > 0) {
						setOctave(currentOctave - 1);
					}
					break;
				case "right":
					if (currentOctave < 7) {
						setOctave(currentOctave + 1);
					}
					break;
				case "space":
					showInstrumentSelectionDialog();
					break;
			}
		}
	}

	/**
	 * Legt die Oktave fest
	 *
	 * @param octave die Oktave
	 */
	private void setOctave(int octave) {
		// Klaviatur entfernen
		for (Actor actor : getObjects(Actor.class)) {
			removeObject(actor);
		}

		channel.allNotesOff();

		currentOctave = octave;
		buildPiano(); // neue Klaviatur erstellen
	}

	/**
	 * Zeigt den Auswahldialog für Instrumente
	 */
	private void showInstrumentSelectionDialog() {
		SwingUtilities.invokeLater(() -> {
			Instrument i = (Instrument) JOptionPane.showInputDialog(
					null,
					"Wähle ein Instrument:",
					"Instrument wählen",
					JOptionPane.QUESTION_MESSAGE,
					null,
					synthesizer.getAvailableInstruments(),
					instrument
			);

			if (i != null) {
				setInstrument(i);
			}
		});
	}

	/**
	 * Legt das Instrument fest
	 *
	 * @param instrument das neue Instrument
	 */
	private void setInstrument(Instrument instrument) {
		if (this.instrument == instrument) {
			return;
		}

		this.instrument = instrument;
		synthesizer.loadInstrument(instrument);

		Patch patch = instrument.getPatch();
		channel.programChange(patch.getBank(), patch.getProgram());
	}

	/**
	 * Legt fest, ob eine Note zurzeit gedrückt wird
	 *
	 * @param note    die Note
	 * @param pressed ob die Note gedrückt wird
	 */
	public void setNotePressed(Note note, boolean pressed) {
		if (synthesizer != null) {
			if (pressed) {
				channel.noteOn(note.getMidiNumber(), 127);
			} else {
				channel.noteOff(note.getMidiNumber());
			}
		} else if (pressed) { // Kein Midi :(
			Greenfoot.playSound(note.getOctave() + note.getName() + ".wav");
		}
	}
}
