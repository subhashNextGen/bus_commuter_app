package com.example.buscustomerapplicationv2.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Model_ChangeEntryCode 
{	
	@NotNull(message="Empty clientID Field")
	String clientID;
	
	@NotNull(message="Empty backendKey_user Field")
	@Pattern(regexp="^[\\S]{58}$",message="Invalid backendKey_user Field")
	String backendKey_user;

	@NotNull(message="Empty entrycode Field")
	String new_entrycode;

	String imei;


	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getBackendKey_user() {
		return backendKey_user;
	}
	public void setBackendKey_user(String backendKey_user) {
		this.backendKey_user = backendKey_user;
	}
	public String getNew_entrycode() {
		return new_entrycode;
	}
	public void setNew_entrycode(String new_entrycode) {
		this.new_entrycode = new_entrycode;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}


	public String logValue() {
		return "clientID::"+clientID+"\n"+
				"backendKey_user::"+backendKey_user+"\n"+
				"imei::"+imei;
	}
	
}
