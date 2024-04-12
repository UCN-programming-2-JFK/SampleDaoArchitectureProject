package run;

import java.util.*;

import dataaccess.dao.implementations.sqlserver.ConcreteToDoListDao;
import model.*;

public class Program {

	public static void main(String[] args) {

		try {
			ConcreteToDoListDao todolistDao = new ConcreteToDoListDao();
			List<ToDoList> lists = todolistDao.getAll();
			showAll(lists);
			ToDoList newList = new ToDoList();
			newList.setName("My new list");
			todolistDao.insert(newList);
			System.out.println("New id: " + newList.getId());
			lists = todolistDao.getAll();
			showAll(lists);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void showAll(List<ToDoList> lists) {
		System.out.println();
		System.out.println("[All lists]");
		
		for(ToDoList list : lists) {
			System.out.println(" - " + list.getName());
		}
	}
}