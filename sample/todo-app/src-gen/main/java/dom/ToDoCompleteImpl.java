package dom;
import java.util.*;
import java.math.*;
import javax.jdo.PersistenceManager;
/* Class ToDoComplete */
@SuppressWarnings("all")
public class ToDoCompleteImpl {
	protected ToDo$Complete self; 
	private PersistenceManager persitenceManager; 
	public void setSelf(ToDo$Complete self) {
		this.self = self;
	} 
	public void setPersitenceManager(PersistenceManager persitenceManager) {
			this.persitenceManager = persitenceManager;
	} 
	public PersistenceManager  getPersitenceManager() {
			return this.persitenceManager;
	} 
}
