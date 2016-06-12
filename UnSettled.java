package com.bet.application;

import java.util.ArrayList;
import java.util.Iterator;

import com.lib.commonFunctions.CommonFunctions;

public class UnSettled extends CommonFunctions {

	/*
	 * 
	 * this function has major business logic to find  
	 * unsetteled bets
	 */

	public void FindUnsettledbets() {

		
		//Get Excel file name 
		String testDataFile = getValueFromPropertiesFile(
				"Configuration.properties", "baseLocation")
				+ getValueFromPropertiesFile("Configuration.properties",
						"unSettledDataFile");
		String sheetName = "Unsettled";

		//Get all data from excel
		ArrayList<ArrayList<String>> output = readAllDataFromExcel(
				testDataFile, sheetName);

		
		//Sort data from Excel
		output = sortExcelData(output);

		Iterator<ArrayList<String>> it = output.listIterator();

		// Group customer by customer id and find customer has unsettled bets with high risk

		ArrayList<String> stakes= new ArrayList<String>();
		ArrayList<String> lastPulledRow = it.next();
		String lastPulledRowCustomerID = lastPulledRow.get(0);
		stakes.add(lastPulledRow.get(3));
		
		int countForCustomerId = 1;

		while (it.hasNext()) {
			ArrayList<String> Row = it.next();
			String RowCustomerId = Row.get(0);
			double RowWin = Double.parseDouble(Row.get(4));
			
			//Bets where the amount to be won is $1000 or more.
			if((RowWin)>(999.0))
				
			{
				System.out.println("Customer with id  " +RowCustomerId  + "is having unsettled bet with win value " + RowWin +  "  which is Highly UNUSUAL");
				
				
			}
			
			
			//double RowStakeValue= Double.parseDouble(Row.get(3));
			if (lastPulledRowCustomerID.equals(RowCustomerId)) {

				countForCustomerId++;
				stakes.add(Row.get(3));

				
			}

			else

			{
				findUnsettledOrNot(stakes,countForCustomerId,lastPulledRowCustomerID);
				

				lastPulledRowCustomerID = RowCustomerId;
				countForCustomerId = 1;
			}
			findUnsettledOrNot(stakes,countForCustomerId,RowCustomerId);

		} 
		
		


	}

	/*
	 * find percentage of win for given customer bet count and returns value in
	 * double data type
	 */

	public void findUnsettledOrNot(ArrayList<String  > stakes,int countForCustomerId, String RowCustomerId) {

		
		double avg=0;
		for(String s:stakes)
	
		{
	
			double stake=Double.parseDouble(s);
			avg=avg+stake;
	
		}
		avg= avg/countForCustomerId;
	
		
		for(String s:stakes)
			
		{
			
			//Bets where the stake is more than 10 times higher than that customer’s average
			if((Double.parseDouble(s))>(avg*10))
	
			{
				System.out.println("Customer with id  " +RowCustomerId  + "is having unsettled bet with stake value " + s +  "  which is UNUSUAL");
	
	
			}
			//Bets where the stake is more than 30 times higher than that customer’s average bet

			if((Double.parseDouble(s))>(avg*30))
	
			{
				System.out.println("Customer with id  " +RowCustomerId  + "is having unsettled bet with stake value " + s +  "  which is HIGHLY UNUSUAL");
	
	
			}
	


		}
		
	}

	public static void main(String[] args) {
		
		
		UnSettled set = new UnSettled();
		set.FindUnsettledbets();

	}

}
