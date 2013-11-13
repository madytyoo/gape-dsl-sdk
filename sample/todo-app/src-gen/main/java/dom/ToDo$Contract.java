package dom;
import java.util.*;
import java.math.*;
import com.mylaensys.dsl.gape.Round;
/** Contract interface. */
public interface ToDo$Contract {
	/* Entry and exit of normal public methods; exit of constructor. */
	/** invariant. */ 
	void invariant();
	/** 
	* description contract.
	* @param round pre or post
	* @param description property
	*/
	void description(Round round, String description);
	/** 
	* category contract.
	* @param round pre or post
	* @param category property
	*/
	void category(Round round, String category);
	/** 
	* dueBy contract.
	* @param round pre or post
	* @param dueBy property
	*/
	void dueby(Round round, Date dueBy);
	/** 
	* complete contract.
	* @param round pre or post
	* @param complete property
	*/
	void complete(Round round, Boolean complete);
	/** 
	* notes contract.
	* @param round pre or post
	* @param notes property
	*/
	void notes(Round round, String notes);
	
	/**
	 * Virtual property title contract. 
	 * @param round pre or post
	 */
	void title(Round round);
	/**
	 * Add ToDo contract.
	 * @param round pre or post
	 * @param toAdd ToDo to add 
	 */
	void addToDependencies(Round round, ToDo toAdd);
	/**
	 * Remove a ToDo contract.
	 * @param round pre or post
	 * @param toRemove ToDo to remove 
	 */
	void removeFromDependencies(Round round, ToDo toRemove);
	
	/**
	 * Virtual collection similarTo contract. 
	 * @param round pre or post
	 */
	void similarto(Round round);					
	
	/**
	 * Virtual collection eligible contract. 
	 * @param round pre or post
	 */
	void eligible(Round round);					
	/* Method  changeStatus */
	public void changeStatus(Round round,Boolean status);
	
}
