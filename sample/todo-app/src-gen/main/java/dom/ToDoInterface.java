
package dom;

import java.util.*;	
import java.math.*;	

/** Interface ToDoInterface. */
@SuppressWarnings("all")
public interface ToDoInterface {
	/** Returns id property. @return id */
	Long getId();
	/** Set the id property. @param id */
	void setId(Long id);	
	
	/** Returns the description property. @return description */
	String getDescription();
	/** Set the description property. @param description */
	void setDescription(String description);
	
	/** Returns the category property. @return category */
	String getCategory();
	/** Set the category property. @param category */
	void setCategory(String category);
	
	/** Returns the dueBy property. @return dueBy */
	Date getDueBy();
	/** Set the dueBy property. @param dueBy */
	void setDueBy(Date dueBy);
	
	/** Returns the complete property. @return complete */
	Boolean getComplete();
	/** Set the complete property. @param complete */
	void setComplete(Boolean complete);
	
	/** Returns the notes property. @return notes */
	String getNotes();
	/** Set the notes property. @param notes */
	void setNotes(String notes);
	
	
	/** Returns the dependencies collection. @return dependencies */
	Set<ToDo> getDependencies();
	/** Sets the dependencies collection. @param collection Set of ToDo */
	void setDependencies(Set<ToDo> collection);
	
	
}	

