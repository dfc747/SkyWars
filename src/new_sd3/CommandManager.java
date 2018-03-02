package new_sd3;

import java.util.Stack;

public class CommandManager {
	private static Stack<ICommand> undos = new Stack<ICommand>();
	private static Stack<ICommand> redos = new Stack<ICommand>();
	private static CommandManager instance;

	private CommandManager() {
	};

	public static synchronized CommandManager getInstance() {

		if (instance == null) {
			return new CommandManager();
		}

		return instance;
	}

	// Executes a command instance and push it to the undos stack
	public static void executeCommand(ICommand c) {
		c.execute();
		undos.push(c);

		// clear the redos
		redos.clear();

	}

	// Pops a command from the undos stack and undoes it.
	// The stack should not be empty.
	public void undo() {
		if (isUndoable()) {
			// when undoing a RemoveEnemy command set the enemy alive
			// and then undo the Move command
			if (undos.peek() instanceof CommandRemoveEnemy) {
				System.out.println("Undo command " + undos.peek().toString());
				redos.push(undos.peek());
				undos.pop().undo(); // Undo the Remove Command
				undo(); // call undo() again, next command may be RemoveEnemy
						// again
			}

			if (isUndoable()) {
				// undo Move or Add Enemy command
				System.out.println("Undo command " + undos.peek().toString());
				redos.push(undos.peek());
				undos.pop().undo();
			}

		}

	}

	public void redo() {
		if (isRedoable()) {

			if (redos.peek() instanceof CommandAddEnemy) {
				System.out.println("Redo command " + redos.peek().toString());
				undos.push(redos.peek());
				redos.pop().redo(); // Redo the Remove Command
				redo(); // call redo() again, next command may be RemoveEnemy
						// again
			}						

			if (isRedoable()) {
				// undo Move or Add Enemy command
				System.out.println("Redo command " + redos.peek().toString());
				undos.push(redos.peek());
				redos.pop().redo();
			}

		}
	}

	// Checks that the undos stack is empty.
	// If it's empty there's no command to undo...
	public boolean isUndoable() {
		return !undos.empty();
	}

	public boolean isRedoable() {
		return !redos.isEmpty();
	}

	public void clearCommands() {
		undos.clear();
		redos.clear();
	}

}
