package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class AppTest {
	
	/**
     * 测试建表，写表操作
     */
    @Test
    public void testExportExcel()
    {
        //测试数据
     String[] headers = new String[]{"A","B","C","D","E"};
         List<List<Object>> valueList = new LinkedList<>();
        for (char i = 'A'; i <= 'E' ; i++) {
            List<Object> rowList = new LinkedList<>();
            for (int j = 0; j <= 4; j++) {
                rowList.add(i+String.valueOf(j));
            }
            valueList.add(rowList);
        }
    AbstractExcel excel = new ExcelBuilder("报名表")
            .header(headers)
            .content(valueList)
            .autoColumnWidth()
            .build(true);
        try {
            File file = new File("F:\\excel\\test.xls");
            FileOutputStream op = new FileOutputStream(file);
            excel.write(op);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试读取表数据操作
     */
    //有头
/*    @Test
    public void testImportExcel(){
       AbstractExcel excel = new NoHeaderExcel(null,"F:/excel/t.xls");
       // AbstractExcel excel = new HeaderExcel(null,"F:/excel/t.xls");
       //List<Map<String,String>> values = excel.getPayload();
       List<List<String>> values = excel.getPayload();
       System.out.println("CREATE TABLE");
       values.forEach(stringStringMap -> {
           stringStringMap.entrySet().forEach(stringStringEntry -> {
               System.out.println(stringStringEntry.getKey()+"---->"+stringStringEntry.getValue());
           });
 
       });
    }*/
    //无头
    @Test
    public void testImportExcel(){
       AbstractExcel excel = new NoHeaderExcel(null,"F:/excel/t.xls");
       // AbstractExcel excel = new HeaderExcel(null,"F:/excel/t.xls");
       //List<Map<String,String>> values = excel.getPayload();
       List<List<String>> values = excel.getPayload();
       System.out.println("CREATE TABLE  IF NOT EXISTS T_PRM_company(");
     
       for(List<String> a:values) {
    	   System.out.println(a.get(1)+" "+a.get(3)+" COMMENT "+a.get(2)+",)");
       }
       System.out.println("ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' ");
    }


}
