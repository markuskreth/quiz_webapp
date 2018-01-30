package de.kreth.quiz.storage;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ReadWriteSource {

	Reader getReader() throws IOException;
	Writer getWriter() throws IOException;
	
}
