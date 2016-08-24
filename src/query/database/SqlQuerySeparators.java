package query.database;

public enum SqlQuerySeparators {
	COMMA(","), DOUBLEQUOTE("\"");
	
	private final String separator ;
	
	private SqlQuerySeparators(String separator) {
		this.separator = separator;
	}
	
	public String getValue() {
        return separator;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
