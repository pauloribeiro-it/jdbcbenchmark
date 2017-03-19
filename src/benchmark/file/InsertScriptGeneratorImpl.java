package benchmark.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InsertScriptGeneratorImpl implements InsertScriptGenerator{
	private static final String PATH_SCRIPT_INSERT_FILE = "inserts.sql";
	
	public void generateScript(String sql) throws IOException{
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_SCRIPT_INSERT_FILE))){
			bw.write(sql);
		}
	}
}
