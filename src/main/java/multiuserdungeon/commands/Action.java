package multiuserdungeon.commands;

public interface Action<T> {
	public abstract T execute();
	public abstract boolean canExecute();
}
