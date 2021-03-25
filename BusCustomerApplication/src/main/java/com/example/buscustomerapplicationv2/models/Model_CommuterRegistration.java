package com.example.buscustomerapplicationv2.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Model_CommuterRegistration
{
	@NotNull(message="Empty clientID Field")
	String clientID;
	
	@NotNull(message="Empty first_name Field")
	String first_name;

	@NotNull(message="Empty last_name Field")
	String last_name;

	@NotNull(message="Empty mobile Field")
	@Pattern(regexp="^[0-9]{10}$",message="Invalid mobile Field")
	String mobile;

	@NotNull(message="Empty emailId Field")
	@Pattern(regexp="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message="Invalid emailId Field")
	String emailId;

	@NotNull(message="Empty entrycode Field")
	String entrycode;

	@NotNull(message="Empty address Field")
	String address;

	@NotNull(message="Empty city Field")
	String city;

	@NotNull(message="Empty state Field")
	String state;

	@NotNull(message="Empty pincode Field")
	@Pattern(regexp="^[0-9]{6}$",message="Invalid pincode Field")
	String pincode;

	@NotNull(message="Empty imei Field")
	String imei;

	public Model_CommuterRegistration( String first_name, String last_name, String mobile, String emailId, String entrycode, String address, String city, String state, String pincode) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.mobile = mobile;
		this.emailId = emailId;
		this.entrycode = entrycode;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;

	}

	public String getClientID()
	{
		return clientID;
	}
	public void setClientID(String clientID) 
	{
		this.clientID = clientID;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEntrycode() {
		return entrycode;
	}
	public void setEntrycode(String entrycode) {
		this.entrycode = entrycode;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}


	public String logValue() {
		return "clientID::"+clientID+"\n"+
				"first_name::"+first_name+"\n"+
				"last_name::"+last_name+"\n"+
				"mobile::"+mobile+"\n"+
				"emailId::"+emailId+"\n"+
				"entrycode::"+entrycode+"\n"+
				"address::"+address+"\n"+
				"city::"+city+"\n"+
				"state::"+state+"\n"+
				"pincode::"+pincode+"\n"+
				"imei::"+imei;
	}
	
}
