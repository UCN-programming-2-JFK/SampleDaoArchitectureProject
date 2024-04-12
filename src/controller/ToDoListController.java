package controller;

import java.util.List;

import dataaccess.dao.interfaces.TaskDao;
import dataaccess.dao.interfaces.ToDoListDao;
import model.Task;
import model.ToDoList;

public class ToDoListController {

	private ToDoListDao toDoListDao;
	private TaskDao taskDao;
	
	public List<ToDoList> getAllToDoLists(){
//		List<ToDoList> lists = toDoListDao.getAll();
//		for(ToDoList list: lists) {
//			List<Task> tasks = taskDao.getFromListId(list.getId());
//			for(Task task : tasks) {
//				list.getTasks().add(task);
//			}
//		}
//		return lists;
		return null;
	}
}