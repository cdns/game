package com.game.struct;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TasksQueue<V> {
	private Lock lock = new ReentrantLock();

	private final ArrayDeque<V> tasksQueue = new ArrayDeque<V>();

	private boolean processingCompleted = true;

	public V poll() {
		try {
			this.lock.lock();
			return this.tasksQueue.poll();
		} finally {
			this.lock.unlock();
		}
	}

	public boolean add(V value) {
		try {
			this.lock.lock();
			return this.tasksQueue.add(value);
		} finally {
			this.lock.unlock();
		}
	}

	public void clear() {
		try {
			this.lock.lock();
			this.tasksQueue.clear();
		} finally {
			this.lock.unlock();
		}
	}

	public int size() {
		return this.tasksQueue.size();
	}

	public boolean isProcessingCompleted() {
		return this.processingCompleted;
	}

	public void setProcessingCompleted(boolean processingCompleted) {
		this.processingCompleted = processingCompleted;
	}
}
