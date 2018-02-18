import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;

/**
 * Zeigt einen Hilfetext unter der Klaviatur an und entfernt sich danach selbst
 */
public class InfoText extends Actor {
	private static final String[] HINTS = {
			"Klaviatur von <A> bis <Enter>",
			"Pfeiltasten zum wechseln der Oktave",
			"<Leertaste> für Instrumente"
	};

	private int i = 0;
	private int counter = 0;

	@Override
	protected void addedToWorld(World world) {
		updateImage();
		setLocation(world.getWidth() / 2, world.getHeight() - 30);
	}

	@Override
	public void act() {
		counter++;

		if (counter >= 200) {
			counter = 0; // zurücksetzen
			i++;

			if (i < HINTS.length) {
				updateImage(); // nächster Tipp
			} else {
				getWorld().removeObject(this); // fertig
			}
		}
	}

	private void updateImage() {
		GreenfootImage text = new GreenfootImage(HINTS[i], 30, Color.WHITE, new Color(0, 0, 0, 0));
		setImage(text);
	}
}
