package benchmark.file;

import java.io.IOException;

public interface InsertScriptGenerator {
	void generateScript(String sql) throws IOException;
}
