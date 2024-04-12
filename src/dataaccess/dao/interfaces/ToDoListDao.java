package dataaccess.dao.interfaces;

import java.util.List;
import model.ToDoList;

/*
 * This interface describes the CRUD (+ extra) operations for a ToDoList 
 */
public interface ToDoListDao {	
	
	// Retrieves all ToDoLists
	List<ToDoList> getAll() throws Exception;
	
	// Finds and returns a single ToDoList by its primary key (id)
	ToDoList findById(int listId) throws Exception;
	
	// inserts a new ToDoList and returns its assigned primary key (id)
	int insert (ToDoList toDoListToInsert) throws Exception;
	
	// updates the stored values for a ToDoList
	void update (ToDoList toDoListToUpdate) throws Exception;
	
	// removes a ToDoList from the data source
	void delete(ToDoList toDoListToDelete) throws Exception;
	
	// removes a ToDoList from the data source
	void delete(int idOfListToDelete) throws Exception;

	//Retrieves all ToDoLists with the argument "partOfListName" in their name
	List<ToDoList> findByPartOfName(String partOfListName) throws Exception;

}