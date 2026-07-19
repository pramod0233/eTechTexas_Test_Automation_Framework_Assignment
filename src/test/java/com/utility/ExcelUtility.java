package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public static Object[][] getTestData(String sheetName) throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "TestData.xlsx");

		Workbook workbook = new XSSFWorkbook(fis);

		Sheet sheet = workbook.getSheet(sheetName);

		int rows = sheet.getPhysicalNumberOfRows();
		int cols = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] data = new Object[rows - 1][cols];

		DataFormatter formatter = new DataFormatter();

		for (int i = 1; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				data[i - 1][j] = formatter.formatCellValue(sheet.getRow(i).getCell(j));
			}
		}

		workbook.close();
		fis.close();

		return data;

	}

}
