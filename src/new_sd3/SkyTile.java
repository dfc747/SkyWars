package new_sd3;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SkyTile extends JPanel {

	JLabel[][] labels = new JLabel[2][2];
	Stack<Ship> shipStack = new Stack<Ship>();

	public SkyTile() {
		super();

		// Create the 2x2 grid
		setLayout(new GridLayout(2, 2));
		setBackground(Color.WHITE);
		setOpaque(false);

		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 2; col++) {
				labels[row][col] = new JLabel();
				this.add(labels[row][col]);
			}
		}
	}

	public void addShip(Ship theShip) {
		shipStack.add(theShip);
	}

	public void fillShipLabels() {
		// First clear the labels
		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 2; c++) {
				if (labels[r][c].getIcon() != null) {
					labels[r][c].setIcon(null);
					labels[r][c].setText("");
				}
			}
		}

		// set count of enemy ships to 0
		int bstar = 0;
		int bcruiser = 0;
		int bshooter = 0;

		// count number of each type of ship
		for (Ship s : shipStack) {
			if (s instanceof BattleStar) {
				bstar++;
			}
			if (s instanceof BattleCruiser) {
				bcruiser++;
			}
			if (s instanceof BattleShooter) {
				bshooter++;
			}
		}

		int stackSize = shipStack.size();
		// Add the ships
		for (int r = 0; r < 2; r++) {
			for (int c = 0; c < 2; c++) {
				if (!shipStack.isEmpty()) {

					Ship s = shipStack.pop();
					labels[r][c].setIcon(s.getIcon());

					// display number of each kind of enemies only when
					// more that 4 ships exist on the tile
					if (stackSize > 4) {
						if (!(s instanceof MasterSpaceShip)) {
							if (s instanceof BattleShooter) {
								labels[r][c].setText(String.valueOf(bshooter));
							}

							if (s instanceof BattleStar) {
								labels[r][c].setText(String.valueOf(bstar));
							}

							if (s instanceof BattleCruiser) {
								labels[r][c].setText(String.valueOf(bcruiser));
							}
						}
					}
				}

			}
		}
		
	}

}
