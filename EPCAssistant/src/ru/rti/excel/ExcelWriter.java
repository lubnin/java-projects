package ru.rti.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelWriter {	 
		private static Logger log = Logger.getLogger(ExcelWriter.class);
	
		public static void writeToFile(int numRows, String fileName, Map<String, Integer> columns, Map<Integer, List<String>> resultMap) throws IOException {
	        //XSSFWorkbook workbook = new XSSFWorkbook();
	    	SXSSFWorkbook wb = new SXSSFWorkbook(-1);
	        SXSSFSheet sheet = wb.createSheet("Результаты запроса");
	        sheet.trackAllColumnsForAutoSizing();
	        CellStyle cs = wb.createCellStyle();
	        Font f = wb.createFont();
	        f.setColor( (short)0xc );
	        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        cs.setBorderBottom(CellStyle.BORDER_THIN);
	        cs.setFont(f);
	        
	        if (resultMap == null) {
	        	return;
	        }
	        
	        for (int rownum = 0; rownum <= numRows; rownum++) {
	        	log.debug(String.format("Writing row %s", rownum));
	        	short columnCount = 0;
	        	Row row = sheet.createRow(rownum);
	        	
	        	
	        	//sheet.setColumnWidth((short) (rownum + 1), (short) ((50 * 8) / ((double) 1 / 20)));
	        	
	        	List<String> lst = resultMap.get(rownum);
	        	
	        	
	        	for (String s : lst) {	        		
	        		Cell cell = row.createCell(columnCount++);
	        		if (rownum == 0) {
	        			cell.setCellStyle(cs);	        			
	        		}
	        		if (s != null && !"null".equals(s)) {
	        			cell.setCellValue(s);
	        		}
	        		sheet.autoSizeColumn(columnCount);
	        	}
	        	
	        	

	            if(rownum % 100 == 0) {
	                 ((SXSSFSheet)sheet).flushRows(100); // retain 100 last rows and flush all others
	            }
	        	
	        }
	        
	        
	        File dir = new File("./output");
	        if (!dir.isDirectory() || !dir.exists()) {
	        	dir.mkdir();
	        }
        	FileOutputStream outputStream = new FileOutputStream("./output/" + fileName);
        	wb.write(outputStream);
	        outputStream.close();
	        
	        
	        // dispose of temporary files backing this workbook on disk
	        wb.dispose();
	        
	    }
	}
