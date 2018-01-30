package de.kreth.quiz.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.kreth.quiz.data.Data;
import de.kreth.quiz.data.Question;
import de.ralleytn.simple.json.JSONParseException;

public enum DatabaseConnection {
	
	INSTANCE;
	
	private String fileName = "questions.json";
	
	ReadWriteSource question = new ReadWriteSource() {
		
		Lock fileLock = new ReentrantLock();
		
		@Override
		public Writer getWriter() throws IOException {
			getLock();
			return new FileWriter(fileName) {
				public void close() throws java.io.IOException {
					super.close();
					fileLock.unlock();
				};
			};
		}
		
		@Override
		public Reader getReader() throws IOException {
			getLock();
			return new FileReader(fileName) {
				public void close() throws IOException {
					super.close();
					fileLock.unlock();
				};
			};
		}

		private void getLock() throws IOException {
			if(fileLock.tryLock() == false) {
				try {
					if(fileLock.tryLock(5, TimeUnit.SECONDS) == false) {
						throw new IOException("File " + fileName + " is not closed yet!");
					}
				} catch (InterruptedException e) {
					throw new IOException("unable to get access to " + fileName);
				}
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public <T extends Data> Dao<T> getDao(Class<T> theClass) throws IOException {
		if(theClass.equals(Question.class)) {
			try {
				return (Dao<T>) new QuestionDao(question);
			} catch (JSONParseException e) {
				throw new IOException(e);
			}
		}
		return null;
	}
}
