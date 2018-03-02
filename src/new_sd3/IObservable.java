package new_sd3;;

public interface IObservable {
	public void addObserver(EnemyShip o);
	public void removeObserver(EnemyShip o);
	public void notifyObservers();

}
