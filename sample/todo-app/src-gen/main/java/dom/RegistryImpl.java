package dom;		
import com.mylaensys.dsl.gape.RegistryObject;
import com.mylaensys.dsl.gape.RegistryProperty;
import java.util.HashMap;

/** Registry Singleton. */
public final class RegistryImpl implements com.mylaensys.dsl.gape.Registry {
	/** Entries. */
	private HashMap<String, RegistryObject> objects;
	/** Default constructor. */
	private RegistryImpl() {
		this.objects = new HashMap();
		/* ToDo */
		RegistryObject todo = new RegistryObject();
		/* description */
		RegistryProperty toDoDescription = new RegistryProperty(false);
		todo.getProperties().put("description", toDoDescription);
		/* category */
		RegistryProperty toDoCategory = new RegistryProperty(false);
		todo.getProperties().put("category", toDoCategory);
		/* dueBy */
		RegistryProperty toDoDueBy = new RegistryProperty(false);
		todo.getProperties().put("dueBy", toDoDueBy);
		/* notes */
		RegistryProperty toDoNotes = new RegistryProperty(false);
		todo.getProperties().put("notes", toDoNotes);
		this.objects.put("dom.ToDo", todo);
	}
	/** Lazy Holder. */
	private static class LazyHolder {
		private static final com.mylaensys.dsl.gape.Registry INSTANCE = new RegistryImpl();
	}
	/** Registry Singleton. @return unique Registry instance */
	public static com.mylaensys.dsl.gape.Registry getInstance() {
		return LazyHolder.INSTANCE;
	}
	/** Returns an object. 
	 * @param name object name
	 * @return unique Registry instance */
	@Override
	public RegistryObject getObject(final String name) {
		return this.objects.get(name);
	}
	
}
