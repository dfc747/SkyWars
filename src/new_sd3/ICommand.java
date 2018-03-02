package new_sd3;;

public interface ICommand {
	
	public void execute();
	public void undo();
	public void redo();
}
