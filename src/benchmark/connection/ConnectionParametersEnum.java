package benchmark.connection;

public enum ConnectionParametersEnum {
	URL("url"),USER("user"),PASSWORD("password");
	String description;
	
	private ConnectionParametersEnum(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
