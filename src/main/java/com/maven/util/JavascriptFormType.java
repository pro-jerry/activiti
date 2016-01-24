package com.maven.util;

import org.activiti.engine.form.AbstractFormType;

@SuppressWarnings("serial")
public class JavascriptFormType extends AbstractFormType{

	 	@Override
	    public String getName() {
	        return "javascript";
	    }

	    @Override
	    public Object convertFormValueToModelValue(String propertyValue) {
	        return propertyValue;
	    }

	    @Override
	    public String convertModelValueToFormValue(Object modelValue) {
	        return (String) modelValue;
	    }
	
}
