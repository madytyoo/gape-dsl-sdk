package dom;
import java.util.*;
import java.math.*;
import javax.jdo.PersistenceManager;
/* Class ToDoIncomplete */
@SuppressWarnings("all")
public class ToDoIncompleteImpl {
	protected ToDo$Incomplete self; 
	private PersistenceManager persitenceManager; 
	public void setSelf(ToDo$Incomplete self) {
		this.self = self;
	} 
	public void setPersitenceManager(PersistenceManager persitenceManager) {
			this.persitenceManager = persitenceManager;
	} 
	public PersistenceManager  getPersitenceManager() {
			return this.persitenceManager;
	} 
}
