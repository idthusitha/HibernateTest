package com.config;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.action.CommonSupportForm;
import com.common.CommonUtil;
import com.pojo.ReportLabelMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PersistanceUtility {
	private static final String PERSISTENCE_UNIT_NAME = "sample";
	private static EntityManagerFactory factory;

	public JSONArray loadData(HttpServletRequest request, HttpServletResponse response, CommonSupportForm commonSupportForm) throws Exception {
		JSONArray array = new JSONArray();

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT u FROM ReportLabelMapping u");

		List<ReportLabelMapping> mappingList = q.getResultList();
		for (ReportLabelMapping mapping : mappingList) {
			JSONObject json = CommonUtil.getInstance().getJsonData(mapping, response);
			array.add(json);
		}
		System.out.println("Size: " + mappingList.size());

		// // Create new user
		// em.getTransaction().begin();
		// User user = new User();
		// user.setName("Tom Johnson");
		// user.setLogin("tomj");
		// user.setPassword("pass");

		// em.persist(user);
		em.getTransaction().commit();

		em.close();

		return array;
	}
}
