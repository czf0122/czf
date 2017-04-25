package Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by SuperMan on 2017/3/31.
 */
public class ReadExcel {
    public String getExcelData(String filepath,String sheetName,int caseID) throws FileNotFoundException{
        /*
        poi步骤
            1、将Excel文件转换成文件流FileInputStream
            2、将文件流转换成工作空间WorkSpace
            3、读取Excel文件中的sheet
            4、获取Excel文件中第一行（表头）
            5、根据表头获取总列数row.getPhysicNunte()
            6、获取表头中的数据给到表头数组
            7、根据行（参数）和列（参数）以及表头数组来获取所需的数据 getRow().getCell()
         */
        //将Excel文件转换成文件流FileInputStream
        FileInputStream fileInputStream = new FileInputStream(filepath+".xls");
        //将文件流转换成工作空间WorkSpace
        Workbook workbook = null;
        try{
            workbook = new HSSFWorkbook(fileInputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        //读取Excel文件中的sheet
        Sheet sheet = workbook.getSheet(sheetName);
        //获取Excel文件中第一行（表头）
        Row titles = sheet.getRow(0);
        //根据表头获取总列数row.getPhysicNunte()(有效单元格)
        int colNum = titles.getPhysicalNumberOfCells();
        //获取表头中的数据给到表头数组
        String[] colNames = new String[colNum];
        //遍历
        Iterator<Cell> itr = titles.cellIterator();
        int count=0;
        while (itr.hasNext()){
            Cell cell = itr.next();
            //设置单元格类型（格式）
            cell.setCellType(CellType.STRING);
            //获取单元格的value
            String value = cell.getStringCellValue().toString();
            colNames[count] = value;
            count++;
        }
        int col = 0;
        boolean flag = false;
        for(int i = 0;i < colNames.length; i++){
            String value = colNames[i];
            if(value.equals(colNames)) {
                col = i;
                flag = true;
                break;
            }
        }
        if(flag == false){
            System.out.println("未匹配到列");
            return null;
        }
        //根据行（参数）和列（参数）以及表头数组来获取所需的数据 getRow().getCell()
        Cell cellValue = sheet.getRow(caseID).getCell(col);
        cellValue.setCellType(CellType.STRING);
        String value = cellValue.getStringCellValue().toString();
        System.out.println("value"+value);
        return value;
    }
    public Map<String,String> get(String filepath,String sheetName,int caseID) throws FileNotFoundException{
        //将Excel文件转换成文件流FileInputStream
        FileInputStream fileInputStream = new FileInputStream(filepath+".xls");
        //将文件流转换成工作空间WorkSpace
        Workbook workbook = null;
        try{
            workbook = new HSSFWorkbook(fileInputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        //读取Excel文件中的sheet
        Sheet sheet = workbook.getSheet(sheetName);
        //获取Excel文件中第一行（表头）
        Row titles = sheet.getRow(0);
        //根据表头获取总列数row.getPhysicNunte()
        int colNum = titles.getPhysicalNumberOfCells();
        //获取表头中的数据给到表头数组
        String[] colName = new String[colNum];
        Iterator<Cell> itr = titles.cellIterator();
        int count=0;
        while (itr.hasNext()){
            Cell cell = itr.next();
            cell.setCellType(CellType.STRING);
            String title = cell.getStringCellValue().toString();
            colName[count] = title;
            count++;
        }
        //获取一行值
        Map<String,String> map = new HashMap<String,String>();
//        Iterator<Cell> cellItr = sheet.getRow(caseID).cellIterator();
        for(int i=0;i<colNum;i++){
            Cell cell = sheet.getRow(caseID).getCell(i);
            cell.setCellType(CellType.STRING);
            map.put(colName[i],cell.getStringCellValue());
        }
        return map;
    }
}
