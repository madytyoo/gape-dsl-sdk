package dom;		

import javax.jdo.Transaction;
import javax.jdo.PersistenceManager;


/* Class ToDoComplete */
@SuppressWarnings("all")
public class ToDoComplete extends ToDoCompleteImpl implements com.mylaensys.dsl.gape.Interaction {
	
	
	public Object run() {
		PersistenceManager pm = this.getPersitenceManager();
		pm.currentTransaction().begin();
		
		self.changeStatus(true);
		
		pm.makePersistent( self );
		pm.currentTransaction().commit();
		return self; 
	}
}
