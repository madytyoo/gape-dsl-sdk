package com.mylaensys.gape.todo;

import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.LoadLifecycleListener;

public class ToDoLifecycleListener implements LoadLifecycleListener {

	@Override
	public void postLoad(InstanceLifecycleEvent arg0) {
		System.out.println("Postload");
	}
	

}
