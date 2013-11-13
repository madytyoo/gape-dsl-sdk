package dom;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mylaensys.dsl.gape.PMF;

public class InstallerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ToDoService.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		clean();
		Date now = new Date();
		createToDo("Buy milk","Domestic",false,computeDueBy(now,1));
		createToDo("Buy stamps","Domestic",true,computeDueBy(now,-1));
		createToDo("Pick up laundry","Other",false,computeDueBy(now,0));
		createToDo("Buy food","Domestic",false,computeDueBy(now,7));
		createToDo("Write blog post","Professional",false,computeDueBy(now,5));
		createToDo("Work on Gape","Professional",false,computeDueBy(now,9));
	}

	private void clean() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Transaction txn = pm.currentTransaction();	
		try {
			txn.begin();
			Query query = pm.newQuery(ToDo.class);
			List<ToDo> all = (List<ToDo>) query.execute();
			for(ToDo todo : all) {
				
				pm.deletePersistent(todo);
			}
			txn.commit();
		} catch (Exception e ) {
			log.severe(e.getMessage());
		} finally {
			if(txn.isActive()) {
				txn.rollback();
			}
			pm.close();
		}
	}

	private void createToDo(String description, String category, boolean complete, Date dueBy) {
		ToDo todo = new ToDo();
		todo.setDescription( description );
		todo.setCategory( category );
		todo.setComplete(complete);
		todo.setDueBy(dueBy);
		store(todo);
	}

	private Date computeDueBy(Date dueBy,int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime( dueBy );
		cal.add( Calendar.DATE, days);
		return cal.getTime();
	}

	private void store(ToDo todo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Transaction txn = pm.currentTransaction();	
		try {
			txn.begin();
			pm.makePersistent(todo);
			txn.commit();
		} catch (Exception e ) {
			log.severe(e.getMessage());
		} finally {
			if(txn.isActive()) {
				txn.rollback();
			}				
			pm.close();
		}
	}
}
