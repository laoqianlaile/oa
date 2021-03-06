package com.suneee.platform.webservice.impl.util;

import com.suneee.core.api.util.ContextUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import com.suneee.core.api.util.ContextUtil;

/**
 * web service 后置监听器。
 * @author RAY
 *
 */
public class AfterInterceptor extends AbstractSoapInterceptor {
	
	public AfterInterceptor(){
		super(Phase.SETUP);
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		ContextUtil.clearAll();
	}

}
