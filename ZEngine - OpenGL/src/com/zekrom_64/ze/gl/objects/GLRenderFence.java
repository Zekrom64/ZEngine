package com.zekrom_64.ze.gl.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;

public class GLRenderFence implements ZERenderFence {

	private boolean status = false;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private Set<Condition> anyConditions = new HashSet<>();
	
	public void signal() {
		status = true;
		condition.signalAll();
		synchronized(anyConditions) {
			for(Condition c : anyConditions) c.signalAll();
			anyConditions.clear();
		}
	}
	
	@Override
	public boolean getStatus() {
		return status;
	}

	@Override
	public void reset() {
		status = false;
	}

	@Override
	public boolean waitFence(long timeout) {
		try {
			condition.awaitNanos(timeout);
		} catch (InterruptedException e) { }
		return status;
	}
	
	public static boolean waitAny(long timeout, GLRenderFence ... fences) {
		Condition cond = new ReentrantLock().newCondition();
		for(GLRenderFence f : fences) {
			synchronized(f.anyConditions) {
				f.anyConditions.add(cond);
			}
		}
		try {
			return cond.awaitNanos(timeout) <= 0;
		} catch (InterruptedException e) {
			return true;
		}
	}
	
	public static boolean waitAll(long timeout, GLRenderFence ... fences) {
		for(GLRenderFence f : fences) {
			try {
				timeout = f.condition.awaitNanos(timeout);
			} catch (InterruptedException e) { }
			if (timeout <= 0) return true;
		}
		return false;
	}

}
