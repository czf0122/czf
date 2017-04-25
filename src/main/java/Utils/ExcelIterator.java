package Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by SuperMan on 2017/4/6.
 */
public class ExcelIterator implements Iterator<Object[]> {
    private int rowNum;//定义excel 行
    private int colNum;//定义excel 列
    private int curRowNum = 0;//当前行
    private String colNames[] = null;
    Workbook book;//创建工作簿
    Sheet sheet = null;//定义工作表

    //定义构造方法
    public ExcelIterator(String filePath, String sheetName) throws IOException{
        readExcel(filePath,sheetName);
    }
    //定义构造方法——从Excel读数据
    public void readExcel(String filePath,String sheetName) throws IOException{
        //创建一个数据流——>将excel文件转换成文件流
        FileInputStream fileInputStream = new FileInputStream(filePath+".xls");
        //创建工作簿
        book = new HSSFWorkbook(fileInputStream);
        //获取相应的数据表
        sheet = book.getSheet(sheetName);
        //获取第一行（表头）
        Row titles = sheet.getRow(0);
        //获取有效行
        rowNum = sheet.getPhysicalNumberOfRows();
        //根据表头获取有效列
        colNum = titles.getPhysicalNumberOfCells();
        //新建一个数组存放列的值
        colNames = new String[colNum];
        //新建一个游标
        int count = 0;
        //新建一个存放cell的迭代器
        Iterator<Cell> heads = titles.cellIterator();
        //遍历
        while (heads.hasNext()){
            Cell cell = heads.next();
            //设置单元格格式
            cell.setCellType(CellType.STRING);
            colNames[count] = cell.getStringCellValue();
            count++;
        }
        //当前行——递增
        this.curRowNum++;
    }

    public boolean hasNext() {
        //判断有效行是否等于0，或当前行是否大于等于有效行数
        if(rowNum == 0||curRowNum>=rowNum){
            return false;
        }else {
            return true;
        }
    }
    public Object[] next() {
        //新建一个Map数组
        Map<String,String> map = new HashMap<String, String>();
        //定义一个Object的返回值
        String value = null;
        //得到有效的行数
        Row row = sheet.getRow(curRowNum);
        for(int i=0;i<colNum;i++){
            Cell cell = row.getCell(i);
            cell.setCellType(CellType.STRING);
            value = cell.getStringCellValue();
            map.put(colNames[i],value);
        }
        this.curRowNum++;
        //定义数组长度为1
        Object object[] = new Object[1];
        //索引位置从0开始
        object[0] = map;
        return object;
    }

    public void remove() {
    }
}
