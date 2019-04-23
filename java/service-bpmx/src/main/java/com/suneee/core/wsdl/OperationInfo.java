package com.suneee.core.wsdl;

import java.util.ArrayList;
import java.util.List;


public class OperationInfo {
	private String operationName = null;
	private List<?> inputParams = new ArrayList();
	private ParameterInfo returnType = null;
	private List<?> outputParams = new ArrayList();
	//输入参数的soapAction
	private String inputAction = "api";
	
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public List<?> getInputParams() {
		return inputParams;
	}

	public void setInputParams(List<?> inputParams) {
		this.inputParams = inputParams;
	}

	public ParameterInfo getReturnType() {
		return returnType;
	}

	public void setReturnType(ParameterInfo returnType) {
		this.returnType = returnType;
	}

	public List<?> getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(List<?> outputParams) {
		this.outputParams = outputParams;
	}

	public String getInputAction() {
		return inputAction;
	}

	public void setInputAction(String inputAction) {
		this.inputAction = inputAction;
	}
}


