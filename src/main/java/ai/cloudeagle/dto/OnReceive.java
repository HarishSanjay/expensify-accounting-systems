package ai.cloudeagle.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnReceive {

	private List<String> immediateResponse;

	public OnReceive() {
	}

	public List<String> getImmediateResponse() {
		return immediateResponse;
	}

	public void setImmediateResponse(List<String> immediateResponse) {
		this.immediateResponse = immediateResponse;
	}
	
	public void addResponse(String response) {
		if(!Objects.nonNull(immediateResponse)) {
			immediateResponse = new ArrayList<>();
		}
		immediateResponse.add(response);
	}

}
