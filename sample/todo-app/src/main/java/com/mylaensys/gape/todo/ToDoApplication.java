package com.mylaensys.gape.todo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import dom.ToDoService;

public class ToDoApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

    public ToDoApplication() {
        SampleDataLoader sampleDateLoader = new SampleDataLoader();
        sampleDateLoader.load();
        singletons.add(new ToDoService());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
    
    @Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(JacksonConfigurator.class);
		return classes;
	}


}
