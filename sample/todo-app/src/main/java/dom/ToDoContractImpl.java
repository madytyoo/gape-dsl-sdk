package dom;

import java.util.Date;

import com.mylaensys.dsl.gape.AbstractContract;
import com.mylaensys.dsl.gape.Round;

public class ToDoContractImpl extends AbstractContract implements ToDo$Contract {

	public void invariant() {}

	public void description(Round round, String description) {}

	public void category(Round round, String category) { }

	public void dueby(Round round, Date dueBy) { }

	public void complete(Round round, Boolean complete) { }

	public void notes(Round round, String notes) { }

	public void title(Round round) { }

	public void addToDependencies(Round round, ToDo toAdd) { }

	public void removeFromDependencies(Round round, ToDo toRemove) { }

	public void similarto(Round round) { }

	public void eligible(Round round) { }

	public void changeStatus(Round round, Boolean status) { }

}
