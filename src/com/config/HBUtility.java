package com.config;

/**
 * @author Thusitha
 *
 */
import java.util.Map;

import javax.imageio.spi.ServiceRegistry;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class HBUtility extends org.hibernate.cfg.Configuration {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Transaction tx = null;
	private static SessionFactory sessionFactory = null;
	private static final ThreadLocal session = new ThreadLocal();

	public HBUtility() {

	}

//	static {
//		try {
//			// Create the SessionFactory from standard (hibernate.cfg.xml)
//			Configuration myConfiguration = new Configuration();
//			myConfiguration.configure("com/config/hibernate.cfg.xml");
//			sessionFactory = myConfiguration.buildSessionFactory();
//
//		} catch (Exception ee) {
//			System.out.println("<<<<< HIBERNATE MAPPING FILE ERROR START >>>>");
//			System.out.println(ee.toString());
//			ee.printStackTrace();
//			System.out.println("<<<<< HIBERNATE MAPPING FILE ERROR END >>>>");
//		}
//	}
	
	public static SessionFactory getSessionFactory(){
		 return sessionFactory; 
	}

	public final void createHbSessionFactory() throws Exception {
		try {
			System.out.println("<<<<< start Create the SessionFactory from standard (hibernate.cfg.xml) >>>>");
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			
			/**Normal Hibernate XML file mapping is using Configuration()*/
			//Configuration configuration = new Configuration();
			
			/**For Hibernate annotation, you have to change it to “AnnotationConfiguration”*/
			AnnotationConfiguration configuration = new AnnotationConfiguration();
			
			configuration.configure("com/config/hibernate.cfg.xml");
			//ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			System.out.println("Hibernate Configuration loaded");
			
			//ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	//System.out.println("Hibernate serviceRegistry created");        	
        	//sessionFactory = configuration.buildSessionFactory(serviceRegistry);
						
			sessionFactory = configuration.buildSessionFactory();
			System.out.println("<<<<< start Create the SessionFactory from standard (hibernate.cfg.xml) >>>>");
			
		} catch (Exception ee) {
			System.out.println("<<<<< HIBERNATE MAPPING FILE ERROR START >>>>");
			System.out.println(ee.toString());
			ee.printStackTrace();
			System.out.println("<<<<< HIBERNATE MAPPING FILE ERROR END >>>>");
		}
	}

	public void close(Session _session) throws Exception {
		if (_session != null) {
			session.set(null);
			_session.clear();
			_session.close();
		}
	}

	public Session getSession() throws Exception {

		Session _session = null;
		try {
			if (sessionFactory != null) {
				_session = currentSession(sessionFactory);
			} else {
				System.out.println("<<<< ERROR.. ERROR.. Hibernate SessionFactory NOT available in sessionFactoryMap >>>>");
			}
			tx = _session.beginTransaction();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return _session;
	}

	public boolean save(Session _session) throws Exception {
		boolean transaction = false;
		try {
			tx.commit();
			transaction = true;
		} catch (Exception er) {
			if (tx != null) {
				tx.rollback();
			}
			transaction = false;
			throw er;
		} finally {
			// No matter what, close the session
			try {
				closeSession();
			} catch (Exception err) {
				_session.close();
			}
		}
		return transaction;
	}

	public boolean save(Map<String, Session> savedSessionList) throws Exception {
		boolean transaction = false;
		Transaction trx = null;
		try {
			for (Map.Entry<String, Session> entry : savedSessionList.entrySet()) {
				trx = entry.getValue().beginTransaction();
				trx.commit();
			}
			transaction = true;
		} catch (Exception er) {
			if (trx != null) {
				trx.rollback();
			}
			transaction = false;
			throw er;
		} finally {
			for (Map.Entry<String, Session> entry : savedSessionList.entrySet()) {
				entry.getValue().close();
			}
		}
		return transaction;
	}

	private static Session currentSession(SessionFactory sessionFactorySession) throws HibernateException {
		Session s;// = (Session) session.get();
		s = sessionFactorySession.openSession();
		session.set(s);
		return s;
	}

	private static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null) {
			s.close();
		}
	}
}
