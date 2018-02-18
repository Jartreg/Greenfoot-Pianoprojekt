import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

public class Taste extends Actor implements AWTEventListener {
	private final Note note;
	private final String key;

	private final GreenfootImage normalState;
	private final GreenfootImage pressedState;

	private boolean pressed = false;
	private boolean mouseDown;

	/**
	 * Erstellt  eine neue Taste
	 *
	 * @param note  die Note, die der Taste zugeordnet werden soll
	 * @param key   die Taste auf der Tastatur, die diese Taste auslöst
	 * @param color die farbe der taste (entweder "white" oder "black", wird für den Dateinamen verwendet)
	 */
	protected Taste(Note note, String key, String color) {
		this.note = note;
		this.key = key;

		normalState = new GreenfootImage(color + "-key.png");
		pressedState = new GreenfootImage(color + "-key-down.png");

		if (note.getName().endsWith("c")) { // Auf Cs wird der Notenname abgebildet
			drawNoteName();
		}

		setImage(normalState);
	}

	private void drawNoteName() {
		String name = "C" + note.getOctave();
		GreenfootImage text = new GreenfootImage(
				name,
				20,
				new Color(0, 0, 0, 100),
				new Color(0, 0, 0, 0)
		);

		// Text auf die Tasten malen

		normalState.drawImage(
				text,
				normalState.getWidth() / 2 - text.getWidth() / 2,
				normalState.getHeight() - text.getHeight() - 20
		);

		pressedState.drawImage(
				text,
				pressedState.getWidth() / 2 - text.getWidth() / 2,
				pressedState.getHeight() - text.getHeight() - 20
		);
	}

	@Override
	public void act() {
		// Die Taste kann mit der Maus oder der Tastatur gedrückt werden
		boolean newState = isMouseDown() || Greenfoot.isKeyDown(key);

		if (newState != pressed) { // Wenn sich der Zustand geändert hat...
			pressed = newState;

			((Piano) getWorld()).setNotePressed(note, newState);
			setImage(newState ? pressedState : normalState);
		}
	}

	/**
	 * Überprüft, ob die linke Maustaste auf dieser Taste gedrückt wird
	 *
	 * @return ob die linke Maustaste gedrückt wird
	 */
	private boolean isMouseDown() {
		if (!mouseDown && Greenfoot.mousePressed(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			mouseDown = true;

			// Benachrichtigt die Taste, wenn die Maustaste losgelassen wurde
			Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
		}

		return mouseDown;
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		if (event instanceof MouseEvent && event.getID() == MouseEvent.MOUSE_RELEASED && SwingUtilities.isLeftMouseButton((MouseEvent) event)) {
			// Die Maustaste wurde losgelassen
			mouseDown = false;
			Toolkit.getDefaultToolkit().removeAWTEventListener(this);
		}
	}
}
