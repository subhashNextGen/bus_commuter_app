package com.example.buscustomerapplicationv2.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Model_VerifyOTP 
{	
	@NotNull(message="Empty clientID Field")
	String clientID;
	
	@NotNull(message="Empty mobile Field")
	@Pattern(regexp="^[0-9]{10}$",message="Invalid mobile Field")
	String mobile;
	
	@NotNull(message="Empty OTP Field")
	@Pattern(regexp="^[0-9]{6}$",message="Invalid OTP Field")
	String mOTP;

	
	
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
	
	
	public String getmOTP() {
		return mOTP;
	}
	public void setmOTP(String mOTP) {
		this.mOTP = mOTP;
	}


	
}
