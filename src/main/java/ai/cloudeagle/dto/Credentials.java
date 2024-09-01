package ai.cloudeagle.dto;

public class Credentials {

	private String partnerUserID;
	
	private String partnerUserSecret;

	public Credentials() {
	}

	public String getPartnerUserID() {
		return partnerUserID;
	}

	public void setPartnerUserID(String partnerUserID) {
		this.partnerUserID = partnerUserID;
	}

	public String getPartnerUserSecret() {
		return partnerUserSecret;
	}

	public void setPartnerUserSecret(String partnerUserSecret) {
		this.partnerUserSecret = partnerUserSecret;
	}

}
