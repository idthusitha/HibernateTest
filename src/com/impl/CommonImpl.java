package com.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.action.CommonSupportForm;
import com.common.CommonUtil;
import com.config.HBUtility;
import com.pojo.ReportLabelMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class CommonImpl {

	public JSONArray load(HttpServletRequest request, HttpServletResponse response, CommonSupportForm commonSupportForm){

		HBUtility hbUtility = new HBUtility();
		JSONArray array = new JSONArray();
		Session _session = null;
		try {
			_session = hbUtility.getSession();
			
			Query query = _session.createQuery("from ReportLabelMapping where status='Y'");
			List<ReportLabelMapping> list = query.list();
			
			

			
			if (list != null && list.size() > 0) {
				for (ReportLabelMapping mapping : list) {
					//_session.load(ReportLabelMapping.class,new Integer(1));
					
					
					//_session.evict(mapping);
					//_session.clear();
					
					JSONObject json = CommonUtil.getInstance().getJsonData(mapping, response);
					array.add(json);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hbUtility.close(_session);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return array;

	}

}
