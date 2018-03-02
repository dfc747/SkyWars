package new_sd3;

import java.util.List;

// used to change mode of player ship which affects what happens in a conflict
public interface IShipModeStrategy {
	public void conflict(MasterSpaceShip ms, List<EnemyShip> enemies);

}
