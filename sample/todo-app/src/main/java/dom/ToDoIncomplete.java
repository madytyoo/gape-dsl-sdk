package dom;		

import javax.jdo.PersistenceManager;

/* Class ToDoIncomplete */
@SuppressWarnings("all")
public class ToDoIncomplete extends ToDoIncompleteImpl implements com.mylaensys.dsl.gape.Interaction {

	public Object run() {
		PersistenceManager pm = this.getPersitenceManager();
		pm.currentTransaction().begin();
		
		self.changeStatus(false);
		
		pm.makePersistent( self );
		pm.currentTransaction().commit();
		return self; 
	}
}
