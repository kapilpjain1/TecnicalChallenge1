package com.lib.commonFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonFunctions {
	ArrayList<String> returnValues;

	public static String propertiesFilePath = "D://MediaOcean/TechChallenge/src/Configuration.properties";

	/*
	 * read values from proerties file
	 * 
	 * @propertiesFileName input file name
	 * 
	 * @keyName key to read
	 * 
	 * @return value of respective key
	 */

	public String getValueFromPropertiesFile(String propertiesFileName,
			String keyName)

	{

		
		//Read data from properties file
		propertiesFileName = propertiesFilePath;
		Properties prop = new Properties();
		InputStream input = null;
		String value = " ";
		try {

			input = new FileInputStream(propertiesFileName);

			// load a properties file
			prop.load(input);

			// get the property value and return
			value = prop.getProperty(keyName);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;

	}

	

	/*
	 * This functions read data for given file and sheet Returns us the list of
	 * list as output *
	 */

	public ArrayList<ArrayList<String>> readAllDataFromExcel(
			String testDataFile, String sheetName) {

		
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					testDataFile));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet(sheetName);
			Iterator<Row> iterator = worksheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				ArrayList<String> rowData = new ArrayList<String>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						rowData.add(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						rowData.add(Boolean.toString(cell.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_NUMERIC:
						rowData.add(Double.toString(cell.getNumericCellValue()));
						break;
					}
				}
				output.add(rowData);
			}
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;

	}

	/*
	 * This function will sort the data which comes from excel file
	 * @Input list of lists containing rows of excel
	 * @return list of list which is sorted on base of first column
	 */
	public ArrayList<ArrayList<String>> sortExcelData(
			ArrayList<ArrayList<String>> input) {

		Collections.sort(input, myComparator);

		return input;

	}

	/*
	 * Comparator to sort list of list on base of first  column
	 * 
	 * 
	 */
	Comparator<ArrayList<String>> myComparator = new Comparator<ArrayList<String>>() {
		@Override
		public int compare(ArrayList<String> o1, ArrayList<String> o2) {
			return o1.get(0).compareTo(o2.get(0));
		}

	};

}// End of Class

