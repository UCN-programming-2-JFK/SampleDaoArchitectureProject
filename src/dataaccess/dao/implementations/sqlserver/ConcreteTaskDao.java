package dataaccess.dao.implementations.sqlserver;

import java.sql.*;
import java.util.*;
import dataaccess.DataContext;
import dataaccess.dao.interfaces.TaskDao;
import model.Task;

public class ConcreteTaskDao implements TaskDao {

	@Override
	public List<Task> getAll() throws Exception {

		List<Task> result = new ArrayList<Task>();
		try {
			final String getAllSql = "SELECT id, name, isfinished, todolist_id_FK FROM Task";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			ResultSet resultSet = selectStatement.executeQuery();
			result = tasksFromResultSet(resultSet);
		} catch (Exception e) {
			throw new Exception("Error retrieving all tasks. Error was: '" + e.getMessage() + "'", e);
		}
		return result;
	}

	@Override
	public Task findById(int taskId) throws Exception {

		Task result = null;
		try {
			final String getAllSql = "SELECT id, name, isfinished, todolist_id_FK FROM Task WHERE Id=?";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			selectStatement.setInt(1, taskId);
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next()) {
				result = taskFromResultSet(resultSet);
			}	
		} catch (Exception e) {
			throw new Exception("Error finding task with id " + taskId + ". Error was: '" + e.getMessage() + "'", e);
		}
		return result;
	}
	
	public List<Task> findByToDoListId(int parentToDoListId) throws Exception {

		List<Task> result = null;
		try {
			final String getAllSql = "SELECT id, name, isfinished, todolist_id_FK FROM Task WHERE ToDoList_Id_FK=?";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			selectStatement.setInt(1, parentToDoListId);
			ResultSet resultSet = selectStatement.executeQuery();
			result = tasksFromResultSet(resultSet);
		} catch (Exception e) {
			throw new Exception("Error retrieving tasks belonging to parent todo list with id "+ parentToDoListId + ". Error was: '" + e.getMessage() + "'", e);
		}
		return result;
	}

	@Override
	public int insert(Task taskToInsert) throws Exception {
		int newListId = 0;
		try {
			final String insertSql = "INSERT INTO Task (name, isFinished, toDoList_id_FK) VALUES (?, ?, ?)";
			PreparedStatement insertListStatement = DataContext.getInstance().getConnection().prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);

			insertListStatement.setString(1, taskToInsert.getName());
			insertListStatement.setBoolean(2, taskToInsert.getIsFinished());
			insertListStatement.setInt(3, taskToInsert.getToDoListId());
			
			int numberOfListsCreated = insertListStatement.executeUpdate();

			if (numberOfListsCreated == 0)
				throw new Exception("Task was not saved to the database. Unknown error.");

			ResultSet listIds = insertListStatement.getGeneratedKeys();
			if (listIds.next()) {
				newListId = listIds.getInt(1);
				taskToInsert.setId(newListId);
			} else {
				throw new Exception("Task was not saved to the database. Unknown error.");
			}
		} catch (Exception e) {
			throw new Exception("Error inserting todo list. Error was: '" + e.getMessage() + "'", e);
		}
		return newListId;
	}

	@Override
	public void update(Task taskToUpdate) throws Exception {
		final String updateSQL = "UPDATE Task SET name=? WHERE id=?";

		try {
			PreparedStatement insertListStatement = DataContext.getInstance().getConnection().prepareStatement(updateSQL);

			insertListStatement.setString(1, taskToUpdate.getName());
			insertListStatement.setInt(2, taskToUpdate.getId());
			int numberOfListsUpdated = insertListStatement.executeUpdate();

			if (numberOfListsUpdated == 0)
				throw new Exception("Task could not be updated");
		} catch (SQLException e) {
			throw new Exception("Error updating task. Error was: '" + e.getMessage() + "'", e);
		}
	}

	@Override
	public void delete(Task taskToDelete) throws Exception {
		delete(taskToDelete.getId());
	}
	
	@Override
	public void delete(int idOfTaskToDelete) throws Exception {
		final String deleteSQL = "DELETE FROM Task WHERE id=?";
		try {
			PreparedStatement deleteListStatement = DataContext.getInstance().getConnection().prepareStatement(deleteSQL);
			deleteListStatement.setInt(1, idOfTaskToDelete);
			int numberOfListsDeleted = deleteListStatement.executeUpdate();

			if (numberOfListsDeleted == 0)
				throw new Exception("Task could not be updated");
		} catch (SQLException e) {
			throw new Exception("Error updating task. Error was: '" + e.getMessage() + "'", e);
		}
		
	}

	@Override
	public List<Task> findByPartOfName(String partOfTaskName) throws Exception {
		List<Task> result = null;
		try {
			
			final String getAllSql = "SELECT id, name, isfinished, todolist_id_FK FROM Task WHERE Name LIKE ?";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			selectStatement.setString(1, "%" + partOfTaskName + "%");
			ResultSet resultSet = selectStatement.executeQuery();
			result = tasksFromResultSet(resultSet);
		} catch (Exception e) {
			throw new Exception("Error finding task by part of name '" + partOfTaskName + "'. Error was: '" + e.getMessage() + "'", e);
		}
		return result;
	}

	
	//	Helper methods	////////////////////////////////////////
	
	private Task taskFromResultSet(ResultSet resultSet) throws Exception {
		Task task = new Task();
		task.setId(resultSet.getInt(1));
		task.setName(resultSet.getString(2));
		task.setIsFinished(resultSet.getBoolean(3));
		task.setToDoListId(resultSet.getInt(4));
		return task;
	}

	private List<Task> tasksFromResultSet(ResultSet resultSet) throws Exception {
		List<Task> result = new ArrayList<Task>();
		while (resultSet.next()) {
			result.add(taskFromResultSet(resultSet));
		}
		return result;
	}
}