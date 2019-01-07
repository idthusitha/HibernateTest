package com.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author thusitha
 */
public class CommonSupportForm extends org.apache.struts.action.ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String actionType = "";

	public CommonSupportForm() {
		super();
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		/*
		 * if (getName() == null || getName().length() < 1) { errors.add("name", new ActionMessage("error.name.required")); // TODO: add 'error.name.required' key to your resources }
		 */
		return errors;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
