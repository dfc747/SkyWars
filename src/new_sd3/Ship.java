package new_sd3;

import java.awt.Image;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Ship {
	private int xpos;
	private int ypos;
	private boolean isAlive = true;
	private ImageIcon icon;

	public Ship() {

	}

	public void Move() {
		Random rnd = new Random();

		int x = rnd.nextInt(3) - 1; // generates x number between -1 and 1
		int y = rnd.nextInt(3) - 1; // generates y number between -1 and 1

		while (x == 0 && y == 0) {
			x = rnd.nextInt(3) - 1;
			y = rnd.nextInt(3) - 1;
		}

		setXpos(getXpos() + x);
		setYpos(getYpos() + y);

	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void setAlive(boolean b) {
		this.isAlive = b;
	}

	public int getXpos() {
		return this.xpos;
	}

	public void setXpos(int xpos) {
		// avoid moving out of sky
		if (xpos > 3) {
			xpos = 2;
		}
		// avoid moving out of sky
		if (xpos < 0) {
			xpos = 1;
		}

		this.xpos = xpos;
	}

	public int getYpos() {
		return this.ypos;
	}

	public void setYpos(int ypos) {
		// avoid moving out of sky
		if (ypos > 3) {
			ypos = 2;
		}
		// avoid moving out of sky
		if (ypos < 0) {
			ypos = 1;
		}

		this.ypos = ypos;
	}

	public void setIcon(String url) {
		URL iconURL = ClassLoader.getSystemResource(url);
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon(iconURL).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
		this.icon = imageIcon;
	}

	public ImageIcon getIcon() {
		return this.icon;
	}

}
