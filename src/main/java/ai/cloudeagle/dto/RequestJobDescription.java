package ai.cloudeagle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RequestJobDescription {

	private String type;
	private Credentials credentials;
	private OnReceive onReceive;
	private InputSettings inputSettings;
	private OutputSettings outputSettings;
	private String fileName;
	private String fileSystem;

	public RequestJobDescription() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public OnReceive getOnReceive() {
		return onReceive;
	}

	public void setOnReceive(OnReceive onReceive) {
		this.onReceive = onReceive;
	}

	public InputSettings getInputSettings() {
		return inputSettings;
	}

	public void setInputSettings(InputSettings inputSettings) {
		this.inputSettings = inputSettings;
	}

	public OutputSettings getOutputSettings() {
		return outputSettings;
	}

	public void setOutputSettings(OutputSettings outputSettings) {
		this.outputSettings = outputSettings;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(String fileSystem) {
		this.fileSystem = fileSystem;
	}

}
