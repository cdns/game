package com.game.struct;

public abstract class AbstractWork implements Runnable {
	private TasksQueue<AbstractWork> tasksQueue;

	public TasksQueue<AbstractWork> getTasksQueue() {
		return this.tasksQueue;
	}

	public void setTasksQueue(TasksQueue<AbstractWork> tasksQueue) {
		this.tasksQueue = tasksQueue;
	}
}
