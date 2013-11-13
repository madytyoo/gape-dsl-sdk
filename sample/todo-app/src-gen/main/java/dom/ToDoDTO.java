package dom;
import java.util.*;
import java.math.*;
/** Class ToDoDTO. */
@SuppressWarnings("all")
public class ToDoDTO {
	/** description property. */
	private String description;
	/** category property. */
	private String category;
	/** dueBy property. */
	private Date dueBy;
	/** notes property. */
	private String notes;
	/** Returns the description property. @return description */
	public final String getDescription() {
		return this.description;
	}
	/** Set the description property. @param param description */
	public final void setDescription(final String param) {
		this.description = param;
	}
	/** Returns the category property. @return category */
	public final String getCategory() {
		return this.category;
	}
	/** Set the category property. @param param category */
	public final void setCategory(final String param) {
		this.category = param;
	}
	/** Returns the dueBy property. @return dueBy */
	public final Date getDueBy() {
		return this.dueBy;
	}
	/** Set the dueBy property. @param param dueBy */
	public final void setDueBy(final Date param) {
		this.dueBy = param;
	}
	/** Returns the notes property. @return notes */
	public final String getNotes() {
		return this.notes;
	}
	/** Set the notes property. @param param notes */
	public final void setNotes(final String param) {
		this.notes = param;
	}
}
