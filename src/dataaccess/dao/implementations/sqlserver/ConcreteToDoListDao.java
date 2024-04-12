package dataaccess.dao.implementations.sqlserver;

import java.sql.*;
import java.util.*;

import org.junit.internal.ExactComparisonCriteria;

import dataaccess.DataContext;
import dataaccess.dao.interfaces.ToDoListDao;
import model.ToDoList;

public class ConcreteToDoListDao implements ToDoListDao {

	@Override
	public List<ToDoList> getAll() throws Exception {

		List<ToDoList> result = new ArrayList<ToDoList>();
		try {
			final String getAllSql = "SELECT id, name FROM ToDoList";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			ResultSet resultSet = selectStatement.executeQuery();
			result = ToDoListsFromResultSet(resultSet);
		} catch (Exception e) {
			throw new Exception("Error retrieving all ToDoLists. The error was: " + e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ToDoList findById(int toDoListId) throws Exception {

		ToDoList result = null;
		try {
			final String getAllSql = "SELECT id, name FROM ToDoList WHERE Id=?";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			selectStatement.setInt(1, toDoListId);
			ResultSet rs = selectStatement.executeQuery();
			if (rs.next()) {
				result = new ToDoList();
				result.setId(rs.getInt(1));
				result.setName(rs.getString(2));
			}	
		} catch (Exception e) {
			throw new Exception("Error finding ToDoList with id + " + toDoListId + ". The error was: " + e.getMessage(), e);
		}
		return result;
	}

	@Override
	public int insert(ToDoList toDoListToInsert) throws Exception {
		int newListId = 0;
		try {
			final String insertSql = "INSERT INTO ToDoList (name) VALUES (?)";
			PreparedStatement insertListStatement = DataContext.getInstance().getConnection().prepareStatement(insertSql,
					Statement.RETURN_GENERATED_KEYS);

			insertListStatement.setString(1, toDoListToInsert.getName());
			int numberOfListsCreated = insertListStatement.executeUpdate();

			if (numberOfListsCreated == 0)
				throw new Exception("Error saving todo list in database. Unknown error.");

			ResultSet listIds = insertListStatement.getGeneratedKeys();
			if (listIds.next()) {
				newListId = listIds.getInt(1);
				toDoListToInsert.setId(newListId);
			} else {
				throw new Exception("Error saving todo list in database. Unknown error.");
			}
		} catch (Exception e) {
			throw new Exception("Error saving todo list in database. Error was: '" + e.getMessage() + "'", e);
		}
		return newListId;
	}

	@Override
	public void update(ToDoList toDoListToUpdate) throws Exception {
		final String updateSQL = "UPDATE ToDoList SET name=? WHERE id=?";

		try {
			PreparedStatement insertListStatement = DataContext.getInstance().getConnection().prepareStatement(updateSQL);
			insertListStatement.setString(1, toDoListToUpdate.getName());
			insertListStatement.setInt(2, toDoListToUpdate.getId());
			int numberOfListsUpdated = insertListStatement.executeUpdate();

			if (numberOfListsUpdated == 0) {
				throw new Exception("ToDoList could not be updated. Unknown error.");
			}
		} catch (SQLException e) {
			throw new Exception("Error updating ToDoList. Error was: '" + e.getMessage() + "'", e);
		}
	}

	@Override
	public void delete(ToDoList toDoListToDelete) throws Exception {
		delete(toDoListToDelete.getId());
	}
	
	@Override
	public void delete(int idOfToDoListToDelete) throws Exception {
		final String deleteSQL = "DELETE FROM ToDoList WHERE id=?";

		try {
			PreparedStatement deleteListStatement = DataContext.getInstance().getConnection().prepareStatement(deleteSQL);
			deleteListStatement.setInt(1, idOfToDoListToDelete);
			int numberOfListsDeleted = deleteListStatement.executeUpdate();

			if (numberOfListsDeleted == 0) {
				throw new Exception("ToDoList could not be deleted. Unknown error.");
			}
		} catch (SQLException e) {
			throw new Exception("Error deleting ToDoList. Error was: '" + e.getMessage() + "'", e);
		}
	}

	@Override
	public List<ToDoList> findByPartOfName(String partOfListName) throws Exception {
		List<ToDoList> result = null;
		try {
			final String getAllSql = "SELECT id, name FROM ToDoList WHERE Name LIKE ?";
			PreparedStatement selectStatement = DataContext.getInstance().getConnection().prepareStatement(getAllSql);
			selectStatement.setString(1, "%" + partOfListName + "%");
			ResultSet resultSet = selectStatement.executeQuery();
			result = ToDoListsFromResultSet(resultSet);
		} catch (Exception e) {
			throw new Exception("Error finding todo list by part of name '" + partOfListName + "'. Error was: '" + e.getMessage() + "'", e);
		}
		return result;
	}
	
	//	Helper methods	////////////////////////////////////////
	
	private ToDoList ToDoListFromResultSet(ResultSet resultSet) throws Exception {
		ToDoList todolist = new ToDoList();
		todolist.setId(resultSet.getInt(1));
		todolist.setName(resultSet.getString(2));
		return todolist;
	}

	private List<ToDoList> ToDoListsFromResultSet(ResultSet resultSet) throws Exception {
		List<ToDoList> result = new ArrayList<ToDoList>();
		while (resultSet.next()) {
			result.add(ToDoListFromResultSet(resultSet));
		}
		return result;
	}
}