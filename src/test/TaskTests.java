package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataaccess.dao.implementations.sqlserver.DaoFactory;
import dataaccess.dao.implementations.sqlserver.ConcreteToDoListDao;
import dataaccess.dao.interfaces.TaskDao;
import dataaccess.dao.interfaces.ToDoListDao;
import junit.framework.Assert;
import model.Task;
import model.ToDoList;


class TaskTests {

	static ToDoListDao listDao; 
	static TaskDao taskDao;
	static int idOfListToDelete;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
		listDao = DaoFactory.getToDoListDao();
		taskDao = DaoFactory.getTaskDao();
		
		idOfListToDelete = listDao.insert(new ToDoList("a _testlist _DELETE_ 1"));
		listDao.insert(new ToDoList("the _testlist 2"));
		int newListId = listDao.insert(new ToDoList("_testlist 3"));
		Task task  = new Task("Do the dishes!");
		task.setToDoListId(newListId);
		taskDao.insert(task);
		
		task = new Task("Mow the lawn!");
		task.setToDoListId(newListId);
		
		taskDao.insert(task);
		listDao.insert(new ToDoList("_testlist 4"));
		listDao.insert(new ToDoList("yet another _testlist 5"));
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		List<ToDoList> testLists = listDao.findByPartOfName("_test");
		for(ToDoList list : testLists) {
			listDao.delete(list);
		}
	}

	@Test
	void testCreationOfToDoList() throws Exception {
	String newListName = "_CREATION_";
		ToDoList newList = new ToDoList(newListName);
		listDao.insert(newList);
		List<ToDoList> testLists = listDao.findByPartOfName(newListName);
		assertEquals (1,testLists.size());
	}
	
	@Test
	void testDeletionOfToDoList() throws Exception {
		List<ToDoList> testLists = listDao.findByPartOfName("_DELETE_");
		listDao.delete(idOfListToDelete);
		ToDoList deleteList = listDao.findById(idOfListToDelete);
		assertEquals (null,deleteList);
	}

}
