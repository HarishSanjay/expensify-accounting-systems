package ai.cloudeagle.utils;

public enum ExpensifyType {

	FILE, REPORT,DOWNLOAD, COMBINED_REPORT_DATA;
	
	@Override
	public String toString() {
		return convertToCamelCase(name(), '_');
	}
	
	private String convertToCamelCase(String input, Character delimiter) {
		boolean capitalizeNext = false;
		
		StringBuilder camelCase = new StringBuilder();
		for (char c : input.toCharArray()) {
            if (c == delimiter) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                camelCase.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                camelCase.append(Character.toLowerCase(c));
            }
        }
		
		return camelCase.toString();
	}
}
