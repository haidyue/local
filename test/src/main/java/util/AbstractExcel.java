package util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;

public abstract class AbstractExcel {
	
	private final HSSFSheet sheet;
	 
    public AbstractExcel() {
        HSSFWorkbook wb = new HSSFWorkbook();
        sheet = wb.createSheet();
    }
 
    public AbstractExcel(String sheetName){
        HSSFWorkbook wb = new HSSFWorkbook();
        sheet = wb.createSheet(sheetName);
    }
 
    public AbstractExcel(HSSFSheet sheet) {
        this.sheet = sheet;
    }
 
 
 
    public abstract List<List<String>> getPayload();
 
 
    public void write(OutputStream op) throws IOException {
        sheet.getWorkbook().write(op);
        sheet.getWorkbook().close();
    }
 
    public String getStringFormatCellValue(HSSFCell cell) {
        String cellVal = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellVal = cell.getStringCellValue();
                break;
            case NUMERIC:
                String dataFormat = cell.getCellStyle().getDataFormatString();
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellVal = df.format(cell.getDateCellValue());
                } else if ("@".equals(dataFormat)) {
                    cellVal = df.format(cell.getNumericCellValue());
                } else {
                    cellVal = String.valueOf(cell.getNumericCellValue());
                    df = new DecimalFormat("#.#########");
                    cellVal = df.format(Double.valueOf(cellVal));
                }
                break;
            case BOOLEAN:
                cellVal = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellVal = String.valueOf(cell.getCellFormula());
                break;
            default:
                cellVal = "";
        }
        return cellVal;
    }


}
