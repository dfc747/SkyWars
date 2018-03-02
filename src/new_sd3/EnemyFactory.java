package new_sd3;

import java.util.Random;

public class EnemyFactory {

	private EnemyFactory() {
	};

	// Factory design pattern used to create enemy ships of random type
	public static EnemyShip createEnemy() {
		EnemyShip es;

		int i = randomNumber(3);

		if (i == 0) {
			es = new BattleStar(0, 0);
		} else

		if (i == 1) {
			es = new BattleCruiser(0, 0);
		} else

		if (i == 2) {
			es = new BattleShooter(0, 0);
		} else {
			return new BattleShooter(0, 0);
		}

		// register the observer
		GUI.getMasterShip().addObserver(es);
		return es;

	}

	// return a random numbers between '0' and 'number' exclusive
	public static int randomNumber(int number) {

		return new Random().nextInt(number);

	}

}
