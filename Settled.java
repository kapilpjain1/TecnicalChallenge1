package com.bet.application;

import java.util.ArrayList;
import java.util.Iterator;

import com.lib.commonFunctions.CommonFunctions;

public class Settled extends CommonFunctions {

	/*
	 * 
	 * this function has major business logic to find which customer is well
	 * settled
	 */

	public void FindSettledCustomers() {

		
		//Get Excel file name 
		String testDataFile = getValueFromPropertiesFile(
				"Configuration.properties", "baseLocation")
				+ getValueFromPropertiesFile("Configuration.properties",
						"setteledataFile");
		String sheetName = "Settled";

		//Get all data from excel
		ArrayList<ArrayList<String>> output = readAllDataFromExcel(
				testDataFile, sheetName);

		
		//Sort data from Excel
		output = sortExcelData(output);

		Iterator<ArrayList<String>> it = output.listIterator();

		// Group customer by customer id and find customer is settled or not
		int winCount = 0;
		ArrayList<String> lastPulledRow = it.next();
		String lastPulledRowCustomerID = lastPulledRow.get(0);
		double lastPulledRowWinValue = Double.parseDouble(lastPulledRow.get(4));
		if (lastPulledRowWinValue > 0.0)

		{

			winCount++;
		}
		int countForCustomerId = 1;

		while (it.hasNext()) {
			ArrayList<String> Row = it.next();
			String RowCustomerId = Row.get(0);
			double RowWin = Double.parseDouble(Row.get(4));
			if (lastPulledRowCustomerID.equals(RowCustomerId)) {

				countForCustomerId++;
				if (RowWin > 0.0)

				{

					winCount++;
				}
			}

			else

			{

				if (findPercentage(countForCustomerId, winCount) >= 60.0) {
					System.out.println("Customer with Customer ID  "
							+ lastPulledRowCustomerID + "  is well Setteled");

				}

				winCount = 0;

				if (RowWin > 0.0)

				{

					winCount++;
				}
				lastPulledRowCustomerID = RowCustomerId;
				countForCustomerId = 1;
			}

		} 
		
		
		
		if (findPercentage(countForCustomerId, winCount) >= 60.0) {
			System.out.println("Customer with Customer ID  "
					+ lastPulledRowCustomerID + "  is well Setteled");

		}

	}

	/*
	 * find percentage of win for given customer bet count and returns value in
	 * double data type
	 */

	public double findPercentage(int countForCustomerId, int winCount) {

		return winCount * 100 / countForCustomerId;

	}

	public static void main(String[] args) {
		
		
		Settled set = new Settled();
		set.FindSettledCustomers();

	}

}
