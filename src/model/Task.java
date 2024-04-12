package model;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.StringCache;

public class Task {

	private int id;
	private String name;
	private boolean isFinished;
	private int toDoListId;
	
	public int getToDoListId() {
		return toDoListId;
	}

	public void setToDoListId(int toDoListId) {
		this.toDoListId = toDoListId;
	}

	public boolean getIsFinished() {
		return isFinished;
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

	public Boolean isFinished() {
		return isFinished;
	}

	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Task(String name) {
		super();		
		this.setName(name);
	}
	public Task() {}
	
	@Override
	public String toString() {
		return "TASK: id: " + getId() + ", name:" + getName() + ", is finished:" + isFinished() + ", todo list id:" + getToDoListId();  
	}
}