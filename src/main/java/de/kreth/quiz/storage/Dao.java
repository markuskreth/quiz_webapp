package de.kreth.quiz.storage;

import java.io.IOException;
import java.util.List;

import de.kreth.quiz.data.Data;

public interface Dao<T extends Data> {

	List<T> getAll();
	T getById(long id);
	void insert(T obj);
	void delete(T obj);
	void flush() throws IOException;
	
}
