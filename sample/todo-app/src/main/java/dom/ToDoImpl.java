
package dom;		
import dom.ToDo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mylaensys.dsl.gape.PMF;


/* Class ToDoImpl */
@SuppressWarnings("all")
public abstract class ToDoImpl implements ToDo$Incomplete,ToDo$Complete {
	protected ToDoInterface self;
	/* Default values */
	protected void initialize(ToDo todo) {
		todo.description = "";
	}
	
	@org.codehaus.jackson.annotate.JsonProperty	
	/* Virtual property title */
	public String getTitle() {
		int index = self.getDescription().indexOf(' ');
		index = index != -1 ? index : 4; 
		String title = self.getDescription().substring(0,index) + "...";
		return title;
	}
	
	@org.codehaus.jackson.annotate.JsonIgnore
	/* Virtual property similarTo */
	public List<ToDo> getSimilarTo() {
		List<ToDo> similarTo = new ArrayList<ToDo>();
		javax.jdo.PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	javax.jdo.Query query = pm.newQuery(ToDo.class);
	    	query.setFilter( "category == param1 && id != param2" );
	        query.declareParameters("String param1,Long param2");
	        similarTo.addAll( (List<ToDo>) query.execute(self.getCategory(),self.getId()) );
	       
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	pm.close();
	    }
		return similarTo;
	}

	@org.codehaus.jackson.annotate.JsonIgnore
	/* Virtual property eligible */
	public List<ToDo> getEligible() {
		List<ToDo> similarTo = new ArrayList<ToDo>();
		javax.jdo.PersistenceManager pm = PMF.get().getPersistenceManager();
	    try {
	    	javax.jdo.Query query = pm.newQuery(ToDo.class);
	    	query.setFilter( "id != param1" );
	        query.declareParameters("Long param1");
	        similarTo.addAll( (Collection<? extends ToDo>) query.execute(self.getId()) );
	       
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	pm.close();
	    }
		return similarTo;
	}
	/* Method  changeStatus */
	@Override
	public Boolean changeStatus(Boolean status) {
		self.setComplete( status );
		return self.getComplete();
	}
	
	/* Convenient method to add with validation */
	public void addToDependencies(ToDo  toAdd) throws Exception {
		self.getDependencies().add( toAdd );
	}
	/* Convenient method to remove with validation */
	public void removeFromDependencies(ToDo  toRemove) throws Exception {
		self.getDependencies().remove( toRemove );
	}					
}	
