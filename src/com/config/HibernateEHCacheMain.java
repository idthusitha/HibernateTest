package com.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import com.pojo.ReportLabelMapping;

//import com.journaldev.hibernate.model.ReportLabelMapping;




public class HibernateEHCacheMain {

	public static void testHiberbante() {
		// TODO Auto-generated method stub
		System.out.println("Temp Dir:" + System.getProperty("java.io.tmpdir"));

		// Initialize Sessions
		SessionFactory sessionFactory = HBUtility.getSessionFactory();
		Statistics stats = sessionFactory.getStatistics();
		System.out.println("Stats enabled=" + stats.isStatisticsEnabled());
		
		stats.setStatisticsEnabled(true);
		System.out.println("Stats enabled=" + stats.isStatisticsEnabled());

		Session session = sessionFactory.openSession();
		Session otherSession = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Transaction otherTransaction = otherSession.beginTransaction();

		printStats(stats, 0);

		ReportLabelMapping ReportLabelMapping = (ReportLabelMapping) session.load(ReportLabelMapping.class, 1L);
		printData(ReportLabelMapping, stats, 1);

		ReportLabelMapping = (ReportLabelMapping) session.load(ReportLabelMapping.class, 1L);
		printData(ReportLabelMapping, stats, 2);

		// clear first level cache, so that second level cache is used
		session.evict(ReportLabelMapping);
		ReportLabelMapping = (ReportLabelMapping) session.load(ReportLabelMapping.class, 1L);
		printData(ReportLabelMapping, stats, 3);

		ReportLabelMapping = (ReportLabelMapping) session.load(ReportLabelMapping.class, 3L);
		printData(ReportLabelMapping, stats, 4);

		ReportLabelMapping = (ReportLabelMapping) otherSession.load(ReportLabelMapping.class, 1L);
		printData(ReportLabelMapping, stats, 5);

		// Release resources
		transaction.commit();
		otherTransaction.commit();
		sessionFactory.close();
	}

	private static void printStats(Statistics stats, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count=" + stats.getEntityFetchCount());
		System.out.println("Second Level Hit Count=" + stats.getSecondLevelCacheHitCount());
		System.out.println("Second Level Miss Count=" + stats.getSecondLevelCacheMissCount());
		System.out.println("Second Level Put Count=" + stats.getSecondLevelCachePutCount());
	}

	private static void printData(ReportLabelMapping ReportLabelMapping, Statistics stats, int count) {
		System.out.println(count + ":: LabelKey=" + ReportLabelMapping.getLabelValue() + ", status=" + ReportLabelMapping.getStatus());
		printStats(stats, count);
	}

}
