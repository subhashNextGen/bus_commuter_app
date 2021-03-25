package com.example.buscustomerapplicationv2.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Model_CreateSession 
{	
	@NotNull(message="Empty clientID Field")
	String clientID;
	
	@NotNull(message="Empty mobile Field")
	@Pattern(regexp="^[0-9]{10}$",message="Invalid mobile Field")
	String mobile;

	@NotNull(message="Empty entrycode Field")
	String entrycode;

	String imei;
	

	public String getClientID()
	{
		return clientID;
	}
	public void setClientID(String clientID) 
	{
		this.clientID = clientID;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEntrycode() {
		return entrycode;
	}
	public void setEntrycode(String entrycode) {
		this.entrycode = entrycode;
	}


	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}


	public String logValue() {
		return "clientID::"+clientID+"\n"+
				"mobile::"+mobile+"\n"+
				"imei::"+imei;
	}
	
}
