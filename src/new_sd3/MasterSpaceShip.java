package new_sd3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MasterSpaceShip extends Ship implements IObservable {
	private List<EnemyShip> observers;
	private IShipModeStrategy shipmode;
	public static int score = 0;

	public MasterSpaceShip() {
		Random rnd = new Random();
		int x = 0;
		int y = 0;
		// choose a random starting position except top left corner
		do {
			x = rnd.nextInt(4);
			y = rnd.nextInt(4);
		} while (x == 0 && y == 0);

		setXpos(x);
		setYpos(y);
		setIcon("mastership2.png");
		observers = new ArrayList<>();
		shipmode = new DefensiveMode(); // default mode
	}

	@Override
	public void Move() {
		super.Move();

		// notify the observers
		notifyObservers();

		if (EnemyFactory.randomNumber(3) == 0) {
			CommandManager.getInstance().executeCommand(new CommandAddEnemy(EnemyFactory.createEnemy()));
			System.out.println("Created enemy [From MasterShip]");
		}

		// check for conflict after every move
		shipmode.conflict(this, observers);

	}

	@Override
	public void addObserver(EnemyShip o) {
		if (!observers.contains(o)) {
			observers.add(o);
			System.out.println("MS added observer");
		}

	}

	@Override
	public void removeObserver(EnemyShip o) {
		observers.remove(o);
		System.out.println("MS removed observer");
	}

	@Override
	public void notifyObservers() {
		for (IObserver o : observers) {
			o.update(this);
		}
	}

	public List<EnemyShip> getObservers() {
		return this.observers;
	}

	public IShipModeStrategy getShipMode() {
		return this.shipmode;
	}

	// Set the ship mode strategy
	public void setShipMode(IShipModeStrategy strategy) {
		shipmode = strategy;
		System.out.println("Mode is " + strategy);
	}

	public String getScore() {
		return String.valueOf(score);
	}

	public void clearObservers() {
		observers.clear();
	}

}
