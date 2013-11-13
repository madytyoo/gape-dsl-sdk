package dom;

import java.util.*;
import com.mylaensys.dsl.gape.PMF;
import com.mylaensys.dsl.gape.RegistryObject;
import com.mylaensys.dsl.gape.SubmitResponse;
import dom.ToDoService;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.listener.InstanceLifecycleEvent;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.jdo.listener.LoadLifecycleListener;
import javax.jdo.listener.StoreLifecycleListener;
import javax.jdo.listener.CreateLifecycleListener;
import javax.jdo.listener.DeleteLifecycleListener;

/** ToDo. */
@javax.ws.rs.Path("/1.0/todos")
public class ToDoService extends com.mylaensys.dsl.gape.AbstractService 
		implements 	LoadLifecycleListener, CreateLifecycleListener, 
					StoreLifecycleListener, DeleteLifecycleListener {
	/** Logger. */
	private static final Logger LOG = Logger.getLogger(ToDoService.class.getName());

	/** 
	* Returns the Registry.
	* @param name object name 
	* @return registry 
	*/
	@Override
	public final RegistryObject getRegistryObject(final String name) {
		return  RegistryImpl.getInstance().getObject(name);
	}
	/**
	 * Returns all instances. 
	 * @param offset collection offset
	 * @param limit number of instances
	 * @return JSON collection of ToDo
	 */
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/")
	@javax.ws.rs.Produces("application/json")
	public final Response todoAll(@QueryParam("offset") final Long offset, @QueryParam("limit") final Long limit) {
		Response.Status status = Response.Status.BAD_REQUEST; 
		String message = "Bad Request";
		Long start = offset == null ? Long.valueOf(0L) : offset;
		Long end = limit == null ? Long.MAX_VALUE : start + limit;
		
		LOG.info("Received a READ ALL request for object ToDo offset=" + start + " limit=" + end);
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);
		try {
			Query query = pm.newQuery(ToDo.class);
			/* Adjust range */
			query.setRange(start, end);						
			List<ToDo> all = (List<ToDo>) query.execute(); 
			return Response.status(Response.Status.CREATED).entity(all).build();
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();
	}		
	/** 
	 * Returns a instance. 
	 * @param id key of the instance
	 * @return a ToDo instance 
	 */
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/{id}")
	@javax.ws.rs.Produces("application/json")
	public final Response todoRead(@PathParam("id") final Long id) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a READ request for object ToDo(" + id + ")");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);			
		try {
			ToDo object = pm.getObjectById(ToDo.class, id);
			
			return Response.status(Response.Status.OK).entity(object).build();
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();			
	}
	/** 
	 * Creates a ToDo instance. 
	 * @param dto ToDo DTO
	 * @return a ToDo instance 
	 */
	@javax.ws.rs.POST
	@javax.ws.rs.Path("/")
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Produces("application/json")
	public final Response todoCreate(final ToDoDTO dto) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a CREATE request for ToDo");
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);			
		javax.jdo.Transaction txn = pm.currentTransaction();	
		try {
			txn.begin();
			ToDo toStore = new ToDo();
			SubmitResponse response = submitForCreate(dto , toStore);
			
			HashMap<String, String> propertyErrors = response.getPropertyErrors();
			if (propertyErrors.size() == 0) {
			
				pm.makePersistent(toStore);
				
				
				txn.commit();
					
				String url = javax.ws.rs.core.UriBuilder.fromUri("todo").path("/{id}").build(toStore.getId()).toString();
				java.net.URI location = new java.net.URI(url);
				return Response.created(location).status(Response.Status.CREATED).entity(toStore).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity(propertyErrors).build();
			}
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}				
			pm.close();
		}
		return Response.status(status).entity(message).build();				
	}		
	/** 
	 * Updates a ToDo instance.
	 * @param id key of the instance
	 * @param dto ToDo DTO
	 * @return a ToDo instance 
	 */
	
	@javax.ws.rs.POST
	@javax.ws.rs.Path("/{id}")
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Produces("application/json")
	public final Response todoUpdate(@PathParam("id") final Long id, final ToDoDTO dto) {		
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received an UPDATE request for object ToDo(" + id + ")");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);			
		javax.jdo.Transaction txn = pm.currentTransaction();	
		try {
			txn.begin();
			ToDo toUpdate = pm.getObjectById(ToDo.class, id);
			SubmitResponse response = submitForUpdate(dto , toUpdate);
			
			HashMap<String, String> propertyErrors = response.getPropertyErrors();
			if (propertyErrors.size() == 0) {
				
			
				pm.makePersistent(toUpdate);
				
			
				txn.commit();
				return Response.status(Response.Status.OK).entity(toUpdate).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity(propertyErrors).build();
			}
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}				
			pm.close();
		}
		return Response.status(status).entity(message).build();				
	}		
	/* Virtual collections are read only*/ 
	
	
	/**
	 * Returns all instances of the dependencies collection. 
	 * @param offset collection offset
	 * @param limit number of instances
	 * @return JSON collection of ToDo
	 */
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/{id}/dependencies")
	@javax.ws.rs.Produces("application/json")
	public final Response dependenciesGet(@PathParam("id") final Long id, @javax.ws.rs.QueryParam("offset") final Long offset, final @javax.ws.rs.QueryParam("limit") Long limit) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a READ request for collection dependencies object ToDo");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);
		try {
			ToDo todo = pm.getObjectById(ToDo.class, id);				
			List<ToDo> collection = new ArrayList<ToDo>();
			for (ToDo o : todo.getDependencies()) {
				collection.add(o);
			} 
	
			return Response.status(Response.Status.OK).entity(collection).build();			
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();			
		
	}
	
	/**
	* Add a ToDo.
	* @param id key of the object
	* @param other key of the object to add
	* @return a JSON representation of the object
	*/
	@javax.ws.rs.POST
	@javax.ws.rs.Path("/{id}/dependencies/{other}")
	@javax.ws.rs.Produces("application/json")
	public final Response dependenciesAdd(@PathParam("id") final Long id, @PathParam("other") final Long other) {			
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		
		LOG.info("Received an ADD request for element (" + other + ") of collection dependencies object ToDo(" + id + ")");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);			
		javax.jdo.Transaction txn = pm.currentTransaction();
		try {
			txn.begin();
							
			ToDo todo = pm.getObjectById(ToDo.class, id);
			ToDo toAdd = pm.getObjectById(ToDo.class, other);
			
			todo.addToDependencies(toAdd);
			
			
			Set collection = todo.getDependencies();
			
			txn.commit();
			return Response.status(Response.Status.OK).entity(collection).build();
		} catch (RuntimeException e) {
				return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}				
			pm.close();
		}
		return Response.status(status).entity(message).build();			
		
	}
	
	/**
	 * Creates and add a ToDo. 
	 * @param id key of the object
	 * @param dto DTO 
	 * @return an JSON instance of the added object
	 */
	@javax.ws.rs.POST
	@javax.ws.rs.Path("/{id}/dependencies")
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Produces("application/json")
	public final Response  dependenciesCreateAndAdd(@PathParam("id") final Long id, final ToDoDTO dto) {			
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);
		javax.jdo.Transaction txn = pm.currentTransaction();
		try {
			txn.begin();
			ToDo todo0 = pm.getObjectById(ToDo.class, id);
			ToDo toAdd = new ToDo();
			
			SubmitResponse response = submitForCreate(dto, toAdd);
			
			HashMap<String, String> propertyErrors = response.getPropertyErrors();
			if (propertyErrors.size() == 0) {
			
				pm.makePersistent(toAdd);
				
				
				
				todo0.addToDependencies(toAdd);
									
				
				pm.makePersistent(todo0);
				
				txn.commit();
				
				String url = javax.ws.rs.core.UriBuilder.fromUri("todo").path("/{id}").build(toAdd.getId()).toString();
				java.net.URI location = new java.net.URI(url);
				return Response.created(location).status(Response.Status.CREATED).entity(toAdd).build();
				
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity(propertyErrors).build();
			}
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}				
			pm.close();
		}
		return Response.status(status).entity(message).build();			
		
	}		
	
	
		/**
		 * Delete an object. 
		 * @param id key of object
		 * @param other not specified
		 * @return not specified
		 */
		@javax.ws.rs.DELETE
		@javax.ws.rs.Path("/{id}/dependencies/{other}")
		@javax.ws.rs.Produces("application/json")
		public final Response dependenciesRemove(@PathParam("id") final Long id, @PathParam("other") final Long other) {			
			Response.Status status = Response.Status.BAD_REQUEST;
			String message = "Bad Request";
			
			LOG.info("Received an DELETE request for element (" + other + ") of collection dependencies object ToDo(" + id + ")");
			
			/* Security check */
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			/* add the callback listener */
			pm.addInstanceLifecycleListener(this, null);			
			javax.jdo.Transaction txn = pm.currentTransaction();
			try {
				txn.begin();
								
				ToDo todo = pm.getObjectById(ToDo.class, id);
				ToDo toRemove = pm.getObjectById(ToDo.class, other);
				
				todo.removeFromDependencies(toRemove);
				
				
				Set collection = todo.getDependencies();
				
				txn.commit();
				return Response.status(Response.Status.OK).entity(collection).build();
			} catch (Exception e) {
				status = Response.Status.INTERNAL_SERVER_ERROR;
				message = e.getMessage();
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}				
				pm.close();
			}
			return Response.status(status).entity(message).build();			
			
		}		
	/* Virtual collections are read only*/ 
	
	/**
	 * Returns all instances of the similarTo collection. 
	 * @param offset collection offset
	 * @param limit number of instances
	 * @return JSON collection of ToDo
	 */
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/{id}/similarTo")
	@javax.ws.rs.Produces("application/json")
	public final Response similarToGet(@PathParam("id") final Long id, @javax.ws.rs.QueryParam("offset") final Long offset, final @javax.ws.rs.QueryParam("limit") Long limit) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a READ request for collection similarTo object ToDo");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);
		try {
			ToDo todo = pm.getObjectById(ToDo.class, id);				
			List<ToDo> collection = new ArrayList<ToDo>();
			for (ToDo o : todo.getSimilarTo()) {
				collection.add(o);
			} 
	
			return Response.status(Response.Status.OK).entity(collection).build();			
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();			
		
	}
	/* Virtual collections are read only*/ 
	
	/**
	 * Returns all instances of the eligible collection. 
	 * @param offset collection offset
	 * @param limit number of instances
	 * @return JSON collection of ToDo
	 */
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/{id}/eligible")
	@javax.ws.rs.Produces("application/json")
	public final Response eligibleGet(@PathParam("id") final Long id, @javax.ws.rs.QueryParam("offset") final Long offset, final @javax.ws.rs.QueryParam("limit") Long limit) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a READ request for collection eligible object ToDo");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);
		try {
			ToDo todo = pm.getObjectById(ToDo.class, id);				
			List<ToDo> collection = new ArrayList<ToDo>();
			for (ToDo o : todo.getEligible()) {
				collection.add(o);
			} 
	
			return Response.status(Response.Status.OK).entity(collection).build();			
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();			
		
	}
	@javax.ws.rs.POST
	@javax.ws.rs.Path("/{id}/complete")
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Produces("application/json")
	public Response complete(@PathParam("id") Long id) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a Complete request for object ToDo(" + id + ")");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);			
		try {
			ToDo object = pm.getObjectById(ToDo.class, id);
			ToDoComplete interaction = new ToDoComplete();
			interaction.setPersitenceManager(pm);
			interaction.setSelf(object);
			Object response = interaction.run();
			
			return Response.status(Response.Status.OK).entity(response).build();
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();			
	}
	@javax.ws.rs.POST
	@javax.ws.rs.Path("/{id}/incomplete")
	@javax.ws.rs.Consumes("application/json")
	@javax.ws.rs.Produces("application/json")
	public Response incomplete(@PathParam("id") Long id) {
		Response.Status status = Response.Status.BAD_REQUEST;
		String message = "Bad Request";
		LOG.info("Received a Incomplete request for object ToDo(" + id + ")");
		
		/* Security check */
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		/* add the callback listener */
		pm.addInstanceLifecycleListener(this, null);			
		try {
			ToDo object = pm.getObjectById(ToDo.class, id);
			ToDoIncomplete interaction = new ToDoIncomplete();
			interaction.setPersitenceManager(pm);
			interaction.setSelf(object);
			Object response = interaction.run();
			
			return Response.status(Response.Status.OK).entity(response).build();
		} catch (Exception e) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			message = e.getMessage();
		} finally {
			pm.close();
		}
		return Response.status(status).entity(message).build();			
	}
	@Override
	public void postLoad(final InstanceLifecycleEvent instanceLifecycleEvent) { }
	@Override
	public void postCreate(final InstanceLifecycleEvent  instanceLifecycleEvent) { }
	@Override
	public void preStore(final InstanceLifecycleEvent  instanceLifecycleEvent) { }	
	@Override
	public void postStore(final InstanceLifecycleEvent  instanceLifecycleEvent) { }
	@Override
	public void preDelete(final InstanceLifecycleEvent  instanceLifecycleEvent) { }	
	@Override
	public void postDelete(final InstanceLifecycleEvent  instanceLifecycleEvent) { }	
	
}
