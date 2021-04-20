package com.example.buscustomerapplicationv2.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Model_bookSJT
{
	@NotNull(message="Empty clientID Field")
	String clientID;
	
	@NotNull(message="Empty backendKey_user Field")
	@Pattern(regexp="^[\\S]{58}$",message="Invalid backendKey_user Field")
	String backendKey_user;
	
	@NotNull(message="Empty backendKey_src_stop Field")
	@Pattern(regexp="^[\\S]{58}$",message="Invalid backendKey_src_stop Field")
	String backendKey_src_stop;
	
	@NotNull(message="Empty backendKey_dest_stop Field")
	@Pattern(regexp="^[\\S]{58}$",message="Invalid backendKey_dest_stop Field")
	String backendKey_dest_stop;
	
	@NotNull(message="Empty backendKey_commuter_cat Field")
	@Pattern(regexp="^[\\S]{58}$",message="Invalid backendKey_commuter_cat Field")
	String backendKey_commuter_cat;
	
	@NotNull(message="Empty fare_value Field")
	@Pattern(regexp="^[0-9\\.]{1,7}$",message="Invalid fare_value Field")
	String fare_value;

	boolean rjt_booked;

	@NotNull(message="Empty imei Field")
	String imei;

	public boolean isRjt_booked() {
		return rjt_booked;
	}

	public void setRjt_booked(boolean rjt_booked) {
		this.rjt_booked = rjt_booked;
	}

	public String getClientID()
	{
		return clientID;
	}
	public void setClientID(String clientID) 
	{
		this.clientID = clientID;
	}
	public String getBackendKey_user() {
		return backendKey_user;
	}
	public void setBackendKey_user(String backendKey_user) {
		this.backendKey_user = backendKey_user;
	}
	public String getBackendKey_src_stop() {
		return backendKey_src_stop;
	}
	public void setBackendKey_src_stop(String backendKey_src_stop) {
		this.backendKey_src_stop = backendKey_src_stop;
	}
	public String getBackendKey_dest_stop() {
		return backendKey_dest_stop;
	}
	public void setBackendKey_dest_stop(String backendKey_dest_stop) {
		this.backendKey_dest_stop = backendKey_dest_stop;
	}
	public String getBackendKey_commuter_cat() {
		return backendKey_commuter_cat;
	}
	public void setBackendKey_commuter_cat(String backendKey_commuter_cat) {
		this.backendKey_commuter_cat = backendKey_commuter_cat;
	}
	public String getFare_value() {
		return fare_value;
	}
	public void setFare_value(String fare_value) {
		this.fare_value = fare_value;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}


	public String logValue() {
		return "clientID::"+clientID+"\n"+
				"backendKey_user"+backendKey_user+"\n"+
				"backendKey_src_stop::"+backendKey_src_stop+"\n"+
				"backendKey_dest_stop::"+backendKey_dest_stop+"\n"+
				"backendKey_commuter_cat::"+backendKey_commuter_cat+"\n"+
				"fare_value::"+fare_value+"\n"+
				"imei::"+imei;
	}
	
}
