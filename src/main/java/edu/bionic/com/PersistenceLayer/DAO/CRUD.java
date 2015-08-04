package edu.bionic.com.PersistenceLayer.DAO;

public interface CRUD<T> {
	public int create(T t);
	public T find(int id);
	public void update(T t);
	public void remove(T t);
}