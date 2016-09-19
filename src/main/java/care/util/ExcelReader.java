package care.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelReader {
	
	/*
	 * File properties
	 */
	
	public String path;
	public String sheetName;
	public FileInputStream fis 		= null;
	public FileOutputStream fileOut = null;
	private HSSFWorkbook workbook 	= null;
	private HSSFSheet worksheet		= null;
	private HSSFRow row 			= null;
	private HSSFCell cell 			= null;
	
	/**
	 * Read Sheet from the Excel Work Book
	 * @param filePath
	 * @param sheetName
	 */
	public ExcelReader(String filePath, String sheetName){
		
		this.path		= filePath;
		this.sheetName 	= sheetName;
		
		try {
			fis			= new FileInputStream(path);
			workbook 	= new HSSFWorkbook(fis);
			worksheet 	= workbook.getSheet(sheetName.trim());
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
