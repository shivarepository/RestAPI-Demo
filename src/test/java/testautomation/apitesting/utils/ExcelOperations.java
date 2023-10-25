package testautomation.apitesting.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {

	static DataFormatter formatter;
	public static void main(String[] args) throws IOException {
		
		FileInputStream inputfile = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/testautomation/apitesting/resources/bookingfile.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(inputfile);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		XSSFRow row = sheet.getRow(0);
		int rowCount = sheet.getLastRowNum();
		int colCount = row.getLastCellNum();
		Object[][] data = new Object[rowCount-1][colCount];
		for(int i=0;i<rowCount-1;i++)
		{
			row = sheet.getRow(i);
			for(int j=0;j<colCount;j++)
			{
				XSSFCell cell = row.getCell(j);
				data[i][j] = formatter.formatCellValue(cell); 
			}
		}
		System.out.println(data);
		workbook.close();
		//return data;
	}
}
