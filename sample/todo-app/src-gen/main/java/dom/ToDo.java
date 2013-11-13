package dom;

import java.util.*;
import java.math.*;

import com.mylaensys.dsl.gape.Round;
import dom.ToDoContract;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdGeneratorStrategy;

/**
*	Class ToDo.
*/ 
@SuppressWarnings("all")
@PersistenceCapable
public class ToDo extends ToDoImpl implements ToDoInterface {
	/** Primary key. */
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Long id;
	/* Contract ToDo$Contract. */
	private ToDo$Contract contract;
	/** description property. */
	@Persistent
	protected String description;
	/** category property. */
	@Persistent
	protected String category;
	/** dueBy property. */
	@Persistent
	protected Date dueBy;
	/** complete property. */
	@Persistent
	protected Boolean complete;
	/** notes property. */
	@Persistent
	protected String notes;
	/** dependencies property. */
	@com.google.appengine.datanucleus.annotations.Unowned
	@Persistent		
	protected Set<ToDo> dependencies;
	/** Default Constructor. */
	public ToDo() {
		this.self =  this;
		super.initialize(this);
		this.contract = new ToDoContract(this); 
		this.contract.invariant();
	}
	/** Returns id property. @return id */
	@org.codehaus.jackson.annotate.JsonProperty
	public final Long getId() {
		return this.id;
	}
	/** Set the id property. @param param id */
	@org.codehaus.jackson.annotate.JsonProperty
	public final void setId(final Long param) {
		this.id = param;
	}
	/** Returns the description property. @return description */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final String getDescription() {
		this.contract.invariant();
		this.contract.description(Round.PostConditions,description);
		return description;
	}
	/** Set the description property. @param param description */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final void setDescription(final String param) {
		this.contract.invariant();
		this.contract.description(Round.PreConditions,param);
		this.description = param;
		this.contract.invariant();
		this.contract.description(Round.PostConditions,description);
	}
	/** Returns the category property. @return category */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final String getCategory() {
		this.contract.invariant();
		this.contract.category(Round.PostConditions,category);
		return category;
	}
	/** Set the category property. @param param category */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final void setCategory(final String param) {
		this.contract.invariant();
		this.contract.category(Round.PreConditions,param);
		this.category = param;
		this.contract.invariant();
		this.contract.category(Round.PostConditions,category);
	}
	/** Returns the dueBy property. @return dueBy */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final Date getDueBy() {
		this.contract.invariant();
		this.contract.dueby(Round.PostConditions,dueBy);
		return dueBy;
	}
	/** Set the dueBy property. @param param dueBy */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final void setDueBy(final Date param) {
		this.contract.invariant();
		this.contract.dueby(Round.PreConditions,param);
		this.dueBy = param;
		this.contract.invariant();
		this.contract.dueby(Round.PostConditions,dueBy);
	}
	/** Returns the complete property. @return complete */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final Boolean getComplete() {
		this.contract.invariant();
		this.contract.complete(Round.PostConditions,complete);
		return complete;
	}
	/** Set the complete property. @param param complete */
	@org.codehaus.jackson.annotate.JsonIgnore
	public final void setComplete(final Boolean param) {
		this.contract.invariant();
		this.contract.complete(Round.PreConditions,param);
		this.complete = param;
		this.contract.invariant();
		this.contract.complete(Round.PostConditions,complete);
	}
	/** Returns the notes property. @return notes */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final String getNotes() {
		this.contract.invariant();
		this.contract.notes(Round.PostConditions,notes);
		return notes;
	}
	/** Set the notes property. @param param notes */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final void setNotes(final String param) {
		this.contract.invariant();
		this.contract.notes(Round.PreConditions,param);
		this.notes = param;
		this.contract.invariant();
		this.contract.notes(Round.PostConditions,notes);
	}
	
	/** Returns the dependencies collection. @return dependencies */
	@org.codehaus.jackson.annotate.JsonIgnore
	public final Set<ToDo> getDependencies() {
		return this.dependencies;
	}
	/** Sets the dependencies collection. @param collection Set of ToDo */
	public final void setDependencies(final Set<ToDo> collection) {
		this.dependencies = collection;
	}
	
	
	
	
	
	
	/** Virtual Property title. 
	 *  @return a String 
	 */
	@org.codehaus.jackson.annotate.JsonProperty	
	public final String getTitle() {
		this.contract.invariant();
		this.contract.title(Round.PostConditions);
		return super.getTitle();
	}
	
	
	/** Virtual collection similarTo. 
	 *  @return a List of ToDo 
	 */
	@org.codehaus.jackson.annotate.JsonIgnore
	public final List<ToDo> getSimilarTo() {
		this.contract.invariant();
		this.contract.similarto(Round.PostConditions);
		return super.getSimilarTo();
	}
	
	/** Virtual collection eligible. 
	 *  @return a List of ToDo 
	 */
	@org.codehaus.jackson.annotate.JsonIgnore
	public final List<ToDo> getEligible() {
		this.contract.invariant();
		this.contract.eligible(Round.PostConditions);
		return super.getEligible();
	}
	/* Method  changeStatus */
	@Override
	public Boolean changeStatus(Boolean status) {
		this.contract.invariant();
		this.contract.changeStatus(Round.PreConditions,status);
		Boolean r = super.changeStatus(status);
		this.contract.invariant();
		this.contract.changeStatus(Round.PostConditions,status);
		return r;
	}
	/** Convenient method to add ToDo. @param toAdd */
	public final void addToDependencies(final ToDo toAdd) {
		this.contract.invariant();
		this.contract.addToDependencies(Round.PreConditions,toAdd);
		self.getDependencies().add(toAdd);
		this.contract.invariant();
		this.contract.addToDependencies(Round.PostConditions,toAdd);
	}
	/** Convenient method to remove ToDo. @param toRemove */
	public final void removeFromDependencies(final ToDo toRemove) {
		this.contract.invariant();
		this.contract.removeFromDependencies(Round.PreConditions,toRemove);
		self.getDependencies().remove(toRemove);
		this.contract.invariant();
		this.contract.removeFromDependencies(Round.PostConditions,toRemove);
	}
}
