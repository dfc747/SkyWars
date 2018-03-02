package new_sd3;

import java.util.ArrayList;
import java.util.List;

public class DefensiveMode implements IShipModeStrategy {

	@Override
	public void conflict(MasterSpaceShip ms, List<EnemyShip> enemies) {
		// if the number of enemies is 1 then destroy the enemy ship
		// if it is more than one then player looses.
		List<EnemyShip> ships = new ArrayList<>();
		for (EnemyShip enemy : enemies) {
			if ((enemy.getXpos() == ms.getXpos()) && (enemy.getYpos() == ms.getYpos())) {
				ships.add(enemy);
			}
		}

		if (ships.size() == 1) {
			// remove enemy and observer
			CommandManager.getInstance().executeCommand(new CommandRemoveEnemy(ships.get(0)));
			ms.removeObserver(ships.get(0)); // remove observer
			System.out.println("Defensive mode: killed a ship");

			// increment score
			GUI.getMasterShip().score++;

			// play the explosion sound
			SoundEffects.EXPLOSION.run();
		} else if (ships.size() > 1) {
			// kill player
			ms.setAlive(false);
			System.out.println("Player is dead");
		}
	}
}
