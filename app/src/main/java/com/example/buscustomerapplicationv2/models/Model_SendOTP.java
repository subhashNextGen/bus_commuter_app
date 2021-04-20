package com.example.buscustomerapplicationv2.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Model_SendOTP 
{	
	@NotNull(message="Empty clientID Field")
	String clientID;

	@NotNull(message="Empty Imei Field")
	String imei;
	
	@NotNull(message="Empty mobile Field")
	@Pattern(regexp="^[0-9]{10}$",message="Invalid mobile Field")
	String mobile;
	
	@NotNull(message="Empty smsText Field")
	@Pattern(regexp="^[0-9]$",message="Invalid smsText Field")
	String smsText;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getClientID()
	{
		return clientID;
	}
	public void setClientID(String clientID) 
	{
		this.clientID = clientID;
	}
	
	public String getMobile() 
	{
		return mobile;
	}
	public void setMobile(String mobile) 
	{
		this.mobile = mobile;
	}
	
	public String getSmsText() 
	{
		return smsText;
	}
	public void setSmsText(String smsText) 
	{
		this.smsText = smsText;
	}
	
	public String logValue() {
		return "clientID::"+clientID+"\n"+
				"mobile::"+mobile+"\n"+
				"smsText::"+smsText;
	}
	
}
