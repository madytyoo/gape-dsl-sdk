package dom;

import java.util.Set;
import com.mylaensys.dsl.gape.Round;

public class ToDoContract extends ToDoContractImpl  {
	private ToDo target;
	
	public ToDoContract(ToDo target) {
		this.target = target;
	}
	
	@Override
	public void invariant() {
		requires(target.description != null,"Invalid Description: null");
		
	}

	@Override
	public void description(Round round, String description) {
		switch (round) {
			case PreConditions:
				requires(description.length() > 0,"Invalid Description: empty");
				break;
		}
	}

	
	@Override
	public void changeStatus(Round round, Boolean status) {
		switch (round) {
			case PreConditions:
				break;
				
			case PostConditions:
				ensure(target.complete == status,"Status not Changed");
				break;
		}
	}

	@Override
	public void addToDependencies(Round round, ToDo toAdd) {
		switch (round) {
			case PreConditions:
				requires(!contains(target.getDependencies(),toAdd), "Already a dependecy");
				break;
		}
	}
	
	private boolean contains(Set<ToDo> set,ToDo todo) {
		for(ToDo t : set) {
			if( t.getId().compareTo(todo.getId()) == 0 ) {
				return true;
			}
		}
		return false;
	}

}
