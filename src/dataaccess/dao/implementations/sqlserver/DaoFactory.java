package dataaccess.dao.implementations.sqlserver;

import dataaccess.dao.interfaces.*;

public class DaoFactory {

	private DaoFactory() {}
	
	public static ToDoListDao getToDoListDao() throws Exception {
		return new ConcreteToDoListDao();
	}
	
	public static TaskDao getTaskDao() throws Exception {
		return new ConcreteTaskDao();
	}
}