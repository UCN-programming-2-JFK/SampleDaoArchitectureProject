package dataaccess.dao.interfaces;

import java.util.List;
import model.Task;

public interface TaskDao {	
	// Retrieves all Tasks
	List<Task> getAll() throws Exception;
	
	// Finds and returns a single Task by its primary key (id)
	Task findById(int taskId) throws Exception;
	
	// inserts a new Task and returns its assigned primary key (id)
	int insert (Task taskToInsert) throws Exception;
	
	// updates the stored values for a Task
	void update (Task taskToUpdate) throws Exception;
	
	// removes a Task from the data source
	void delete(Task taskToDelete) throws Exception;

	// removes a Task from the data source
	void delete(int idOfTaskToDelete) throws Exception;

	//Retrieves all Tasks which belongs to the ToDoList with the listId as primary key
	List<Task> findByToDoListId(int parentListId) throws Exception;
	
	//Retrieves all Tasks with the argument "partOfTaskName" in their name
	List<Task> findByPartOfName(String partOfTaskName) throws Exception;
}