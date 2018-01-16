package com.automobiles.test;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.automobiles.bo.BillBo;
import com.automobiles.entities.Address;
import com.automobiles.entities.Customer;
import com.automobiles.entities.Dealer;
import com.automobiles.entities.Invoice;
import com.automobiles.entities.Jobsheet;
import com.automobiles.entities.Mechanic;
import com.automobiles.entities.RepairsConducted;
import com.automobiles.entities.ServiceCenter;
import com.automobiles.entities.Sparepart;
import com.automobiles.entities.Vehicle;
import com.automobiles.util.SessionFactoryResgistry;

public class Test {
	public static void main(String[] args) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction transaction = null;
		Boolean flag = false;
		try {
			sessionFactory = SessionFactoryResgistry.getSessionFactory();
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			//showAddress(session);
			//findAddress(session, "h");
			// findJobsheetByTypeAndEstimatedAmount(session, "repair", 10000);
			// findJobSheetsWithStatusAndEta(session, "comlete", new Date());
			// findCountOfMechanicsByExperiences(session, 2);
			// findMaxInvoiceAmountOfTheDay(session);
			// findMaxKiloByJobSheetType(session);
			// findBillsByPaymentType(session, "Cash");
			// findSecondHighestPriceSparepart(session);
			 //findNoOfSheetsByAdvisor(session);
			// findMaxColorVehicleModel(session);
			//findJobSheetByMechanic(session, 100);
			// findJobSheetsByCustomerMobile(session, 765897451);
			// findCustomersByArea(session, Arrays.asList(new String[] { "S r Nagar", "Road
			// No. 10" }));
			// getRepairsConductedByJobsheetNo(session, 1);
			// findMechanicsByBillNo(session, 2);
			// findServiceCentersByDelearCode(session, 2);
			
			// getVehicles(session);
			

			// showAdressC(session);
			// findAddressC(session, "n");
			// findJobsheetByTypeAndEstimatedAmountC(session, "Paid", 2000);
			// findJobSheetsWithStatusAndEtaC(session, "Processing", new Date());
			// findCountOfMechanicsByExperiencesC(session, 2);
			// findMaxInvoiceAmountOfTheDayC(session);
			// getPartialDealerByFirstName(session, "a");
			// findMaxKiloByJobSheetTypeC(session);
			// findNoOfSheetsByAdvisorC(session);
			// findNoOfSheetsByForAllAdvisor(session);
			// findJobSheetByMechanicC(session, 1);
			// findJobSheetsByCustomerMobileC(session,765897451);
			// findCustomersByAreaC(session, Arrays.asList(new String[] { "S r Nagar",
			// "RoadNo.10" }));
			// getRepairsConductedByJobsheetNoC(session, 1);
			// findMechanicsByBillNoC(session, 1);
			// getDealerByMobile(session, 989897255);
			//findDealerByEmailOrMobile(session, null, 989897255);
		} finally {
			if (transaction != null) {
				if (flag) {
					transaction.commit();
				} else {
					transaction.rollback();
				}
			}
			SessionFactoryResgistry.closeSessionFactory();
		}
		
	}
	private static void showAddress(Session session)
	{
		Query query=session.createQuery("from Address a");
		List<Address> addresses=query.list();
		for(Address address:addresses)
		{
			System.out.println(address.getCity());
		}
	}
	private static void findAddress(Session session,String add)
	{
		Query query=session.createQuery("from Address a where a.addressLine1 like ?");
		query.setParameter(0,add+"%");
		List<Address> addresses=query.list();
		for(Address address:addresses)
		{
			System.out.println(address);
		}
	}
	private static void findJobsheetByTypeAndEstimatedAmount(Session session,String jobSheetType,int estAmt)
	{
		Query query=session.createQuery("from JobSheet js where js.jobSheetType = ? and js.estimatedAmount >= ?");
		query.setParameter(0,jobSheetType);
		query.setParameter(1,estAmt);
		List<Jobsheet> jobsheets=query.list();
		for (Jobsheet jobsheet : jobsheets) {
			System.out.println(jobsheet);
		}
		
	}
	private static void findJobSheetsWithStatusAndEta(Session session,String status,Date eta)
	{	
		
		Query query=session.createQuery("from Jobsheet js where js.status = ? and js.deliveryEta = current_date()");
		query.setParameter(0,status);
		//query.setParameter(1,eta);
		List<Jobsheet> jobsheets=query.list();
		for (Jobsheet jobsheet : jobsheets) {
			System.out.println(jobsheet);
		}
		
	}
	private static void findCountOfMechanicsByExperiences(Session session,int exp)
	{
		Query query=session.createQuery("select count(1) from Mechanic m where m.experience<=? ");
		query.setParameter(0,exp);
		List<Mechanic> mechanics = query.list();
			System.out.println(mechanics.get(0));
	}
	private static void findMaxInvoiceAmountOfTheDay(Session session)
	{	//select * from invoice where invoice_amount=(select max(invoice_amount) from invoice where invoice_dt='2017-05-21')
		Query query=null;
		query = session.createQuery(
				"from Invoice iv where iv.invoiceAmount = (select max(siv.invoiceAmount) from Invoice siv where siv.invoiceDt = current_date())");
		List<Invoice> invoices=query.list();
		
		for (Invoice invoice : invoices) {
			System.out.println(invoice.getInvoiceAmount()+"-"+invoice.getInvoiceDt());
		}
	}
	private static void findMaxKiloByJobSheetType(Session session)
	{
		Query query = session
				.createQuery("select js.jobSheetType, max(js.kilometers) from Jobsheet js group by js.jobSheetType");
		List<Object[]> jobSheets = query.list();
		for (Object[] row : jobSheets) {
			System.out.println("Type : " + row[0] + " kilo : " + row[1]);
		}
		
	}
	
	private static void findJobSheetByMechanic(Session session,int mNo)
	{
		Query query = null;

		query = session.createQuery(
				"select js from Jobsheet js inner join js.jobsheetMechanics jsm where jsm.mechanic.mechanicNo = ?");
		query.setParameter(0,mNo);
		List<Jobsheet> jobSheets = query.list();
		for (Jobsheet js : jobSheets) {
			System.out.println(js.getJobsheetNo());
		}
		
	}

}











