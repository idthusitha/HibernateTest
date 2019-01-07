package com.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.config.PersistanceUtility;
import com.impl.CommonImpl;

import net.sf.json.JSONArray;


/**
 * 
 * @author thusitha
 */
public class CommonSupportAction extends Action {

	private static final String SUCCESS = "success";/* forward name="success" path="" */
	private static final String ERROR = "error";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		CommonSupportForm commonSupportForm = (CommonSupportForm) form;
		String actionType = commonSupportForm.getActionType();
		actionType = (actionType == null || "".equals(actionType)) ? request.getParameter("actionType") : actionType;
		
		System.out.println("actionType " + actionType);
		try {
			if (actionType == null || "".equals(actionType)) {
				return mapping.findForward(SUCCESS);

			} else if (actionType != null && actionType.equals("submit")) {
				//saveServerConfigData(request, response, commonSupportForm);
				//response.sendRedirect("ServerDetailsSupportAction.do?actionType=");
				//response.sendRedirect("RezServerDescriptionSupportAction.do?actionType=");

			} else if (actionType != null && actionType.equals("load")) {
				load(request, response, commonSupportForm);
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward(SUCCESS);
		}
		return mapping.findForward(SUCCESS);
	}

	private void load(HttpServletRequest request, HttpServletResponse response, CommonSupportForm commonSupportForm) throws Exception {
		//Hibernate
		CommonImpl commonImpl = new CommonImpl();
		JSONArray array = commonImpl.load(request, response, commonSupportForm);
		response.getWriter().write(array.toString());
		
		//Persistance
//		PersistanceUtility persistanceUtility = new PersistanceUtility();
//		JSONArray array = persistanceUtility.loadData(request, response, commonSupportForm);
//		response.getWriter().write(array.toString());
		
		
	}
}
