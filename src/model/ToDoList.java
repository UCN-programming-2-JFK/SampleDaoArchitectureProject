package model;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

	List<Task> tasks = new ArrayList<Task>();
	int id;
	String name;
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ToDoList(String name) {
		super();
		this.name = name;
	}	
	
	public ToDoList() {}
}