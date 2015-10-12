package com.lextech.model;

import java.io.Serializable;

/**
 * 
 * @author Hanlu  Feng
 *
 */
public class Result implements Serializable{
	
	private static final long serialVersionUID = -6977435562653463669L;
	private boolean isSuccess=false;
	private String  successMessage;
	private String failureMessage;

	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
	@Override
	public String toString() {
		return "Result [isSuccess=" + isSuccess + ", successMessage="
				+ successMessage + ", failureMessage=" + failureMessage+ "]";
	}

	

}
