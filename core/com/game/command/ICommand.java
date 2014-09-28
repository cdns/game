package com.game.command;

public interface ICommand {
	public abstract void action();

	public abstract Object clone() throws CloneNotSupportedException;
}
