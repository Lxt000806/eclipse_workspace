package com.house.framework.commons.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.house.framework.commons.utils.CommonUtils;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * 导入EXCEL导入工具, 产出指定pojo 列表
 * @param <T>
 */
public class ExcelImportUtils<T> {
	private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtils.class);
	

	public ExcelImportUtils() {
	}

	/**
	 * 解析excel文档
	 * @param in 文件流
	 * @param titleList 必须包含的标题列表，null则无限制
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<T> importExcel(InputStream in, Class<T> clazz,List<String> titleList) throws Exception {
		List<T> dist = new ArrayList<T>();
		
		
			Field filed[] = clazz.getDeclaredFields();
			
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中   
			Map<String, Object[]> fieldmap = new HashMap<String, Object[]>();
			
			// 循环读取所有字段   
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				if (exa != null) {
					
					// 构造设置了Annotation的字段的Setter方法   
					String fieldname = f.getName();
					String setMethodName = "set"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					
					// 构造调用的method，   
					Method setMethod = clazz.getMethod(setMethodName, new Class[] { f.getType() });
					String pattern = exa.pattern();
					
					// 将这个method以Annotaion的名字为key来存入。   
					fieldmap.put(exa.exportName(), new Object[]{ setMethod, pattern });
				}
			}

			//FileInputStream in = new FileInputStream(file);
			Workbook book = WorkbookFactory.create(in);  
			Sheet sheet = book.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			Iterator<Row> rows = sheet.rowIterator();
			Row title = rows.next(); //取得标题头行
			
			Iterator<Cell> cellTitle = title.cellIterator(); // 得到第一行的所有列  
			Map<Integer, String> titlemap = new HashMap<Integer, String>();// 将标题的文字内容放入到一个map中。   
			Map<String,String>  verifyMap=new HashMap<String, String>();
			
			// 循环标题所有的列   
			for (int i=0; cellTitle.hasNext(); i++) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				titlemap.put(i, value);
				verifyMap.put(value.trim(), value.trim());
			}
			if(titleList!=null){
				//验证是否包含必须的标题
				for(String str:titleList){
					if(!verifyMap.containsKey(str)){
						T tObject = clazz.newInstance();
						Method setMethod = (Method) fieldmap.get("错误信息")[0];
						setMethod.invoke(tObject,"Excel文件不完整,第一行缺少以下字段:"+str+",不能导入!");
						dist.add(tObject);
						return dist;
					} 
				}
			}

			//解析内容行 
			for (int i=0; rows.hasNext(); i++) {
				Row rown = rows.next();
				
				Iterator<Cell> cellbody = rown.cellIterator();// 行的所有列   
				T tObject = clazz.newInstance(); // 行的所有列   
				boolean cellbodyHasContent = false;
				
				// 遍历一行的列   
				for (int j=0; cellbody.hasNext() && j<titlemap.size(); j++) {
					Cell cell = cellbody.next();
					
					if(StringUtils.isNotBlank(this.getCellValue(cell))){
						cellbodyHasContent = true;
					}
					if(cell.getCellType()!=Cell.CELL_TYPE_STRING){
						if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
						    if (DateUtil.isCellDateFormatted(cell)){// 判断单元格是否属于日期格式  
						    	cell.setCellValue(formatDate(cell.getDateCellValue()));
						    }
						  
						} 
						cell.setCellType(Cell.CELL_TYPE_STRING);
					}
					String titleString = (String) titlemap.get(j).trim();// 这里得到此列的对应的标题
										
					//如果最后一行是统计行直接返回
					if(i==(lastRowNum-1)&&j==0&&"合计".equals(this.getCellValue(cell))){
						return dist;
					}
					// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值   
					if (fieldmap.containsKey(titleString)) {
						Method setMethod = (Method) fieldmap.get(titleString)[0];
						Type[] ts = setMethod.getGenericParameterTypes();//得到setter方法的参数
						String xclass = ts[0].toString(); //只要一个参数   
						
						//判断参数类型   
						if (xclass.equals("class java.lang.String")) {
							setMethod.invoke(tObject, this.getCellValue(cell));
						}
						
						else if (xclass.equals("class java.util.Date")) {
							String pattern = (String) fieldmap.get(titleString)[1];
							if(StringUtils.isBlank(pattern))pattern = "yyyy-MM-dd";
							
							setMethod.invoke(tObject, CommonUtils.parseString2Date(this.getCellValue(cell), pattern));
						} 
						
						else if (xclass.equals("class java.lang.Boolean")) {
							Boolean boolname = true;
							if (this.getCellValue(cell).equals("否")) {
								boolname = false;
							}
							setMethod.invoke(tObject, boolname);
						}
						
						else if (xclass.equals("class java.lang.Integer")) {
							if(StringUtils.isNotBlank(this.getCellValue(cell))){
								setMethod.invoke(tObject, Integer.parseInt(this.getCellValue(cell)));
							}else{
								setMethod.invoke(tObject, Integer.parseInt("0"));
							}
							
						}

						else if (xclass.equals("class java.lang.Long")) {
							if(StringUtils.isNotBlank(this.getCellValue(cell))){
								setMethod.invoke(tObject, new Long(this.getCellValue(cell)));
							}else{
								setMethod.invoke(tObject, new Long("0"));
							}
							
						}else if (xclass.equals("class java.lang.Double")) {
							if(StringUtils.isNotBlank(this.getCellValue(cell))){
								setMethod.invoke(tObject, new Double(this.getCellValue(cell)));
							}else{
								setMethod.invoke(tObject, new Double("0"));
							}
							
						}

					
					}
				}
				
				if(cellbodyHasContent){
					dist.add(tObject);
				}
			}

		return dist;
	}
	
	/**
	 * 解析Excel文档，修复之前列错乱Bug
	 * 
	 * @param in 
	 * @param clazz 要封装的实体类
	 * @param titleList 必须包含的标题列表，传null时无限制
	 * @return 封装后的实体类列表
	 */
    public List<T> importExcelNew(InputStream in, Class<T> clazz,
            List<String> titleList) throws Exception {
        
        List<T> entities = new ArrayList<T>();
        Field[] fileds = clazz.getDeclaredFields();

        // 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
        Map<String, Object[]> fieldMap = new HashMap<String, Object[]>();

        // 循环读取所有字段
        for (int i = 0; i < fileds.length; i++) {
            Field field = fileds[i];

            ExcelAnnotation excelAnnotation = field.getAnnotation(ExcelAnnotation.class);
            if (excelAnnotation != null) {

                // 构造设置了Annotation的字段的Setter方法
                String fieldName = field.getName();
                String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);

                // 构造调用的method
                Method setMethod = clazz.getMethod(setMethodName, new Class[] {field.getType()});
                String pattern = excelAnnotation.pattern();

                // 将这个method以Annotaion的名字为key来存入。
                fieldMap.put(excelAnnotation.exportName(), new Object[] {setMethod, pattern});
            }
        }

        Sheet sheet = WorkbookFactory.create(in).getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        Iterator<Row> rows = sheet.rowIterator();
        Row tableHead = rows.next(); // 取得标题头行

        Iterator<Cell> heads = tableHead.cellIterator(); // 得到第一行的所有列
        Map<Integer, String> headMap = new HashMap<Integer, String>(); // 将标题的文字内容放入到一个map中。
        Map<String, String> verificationMap = new HashMap<String, String>();

        // 循环标题所有的列
        for (int i = 0; heads.hasNext(); i++) {
            Cell cell = heads.next();
            String value = cell.getStringCellValue();
            headMap.put(i, value);
            verificationMap.put(value.trim(), value.trim());
        }
        
        if (titleList != null) {
            for (String title : titleList) {
                if (!verificationMap.containsKey(title)) {
                    T entity = clazz.newInstance();
                    Method setMethod = (Method) fieldMap.get("错误信息")[0];
                    setMethod.invoke(entity, "Excel文件不完整, 第一行缺少以下字段: " + title + ",不能导入!");
                    entities.add(entity);
                    return entities;
                }
            }
        }

        // 遍历表格每一行
        for (int i = 0; rows.hasNext(); i++) {
            Row currentRow = rows.next();
            T entity = clazz.newInstance();
            boolean emptyRow = true;
            
            // 遍历行的每一个单元格
            for (int j = 0; j < headMap.size(); j++) {
                Cell cell = currentRow.getCell(j);
                
                if (StringUtils.isNotBlank(getCellValue(cell))) {
                    emptyRow = false;
                    
                    if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
                        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cell.setCellValue(formatDate(cell.getDateCellValue()));
                            }
                        }
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                }
                
                // 如果最后一行是统计行直接返回
                if (i == (lastRowNum - 1) && j == 0 && "合计".equals(getCellValue(cell))) {
                    return entities;
                }
                
                // 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
                if (fieldMap.containsKey(headMap.get(j))) {
                    
                    Method setter = (Method) fieldMap.get(headMap.get(j))[0];
                    Type[] paramTypes = setter.getGenericParameterTypes();
                    String firstParamType = paramTypes[0].toString();

                    if (firstParamType.equals("class java.lang.String")) {
                        setter.invoke(entity, getCellValue(cell));
                    } else if (firstParamType.equals("class java.util.Date")) {
                        String pattern = (String) fieldMap.get(headMap.get(j))[1];
                        
                        if (StringUtils.isBlank(pattern)) pattern = "yyyy-MM-dd";

                        setter.invoke(entity, CommonUtils.parseString2Date(getCellValue(cell), pattern));
                    } else if (firstParamType.equals("class java.lang.Boolean")) {
                        Boolean bool = true;
                        if (getCellValue(cell).equals("否")) bool = false;
                        setter.invoke(entity, bool);
                    } else if (firstParamType.equals("class java.lang.Integer")) {
                        if (StringUtils.isNotBlank(getCellValue(cell))) {
                            setter.invoke(entity, Integer.parseInt(getCellValue(cell)));
                        } else {
                            setter.invoke(entity, Integer.parseInt("0"));
                        }
                    } else if (firstParamType.equals("class java.lang.Long")) {
                        if (StringUtils.isNotBlank(getCellValue(cell))) {
                            setter.invoke(entity, new Long(this.getCellValue(cell)));
                        } else {
                            setter.invoke(entity, new Long("0"));
                        }
                    } else if (firstParamType.equals("class java.lang.Double")) {
                        if (StringUtils.isNotBlank(getCellValue(cell))) {
                            setter.invoke(entity, new Double(getCellValue(cell)));
                        } else {
                            setter.invoke(entity, new Double("0"));
                        }
                    }
                }
            }
            
            if (!emptyRow) entities.add(entity);
        }

        return entities;
    }
	
	public List<Map<String,Object>> importExcel(InputStream in) throws Exception {
			List<Map<String,Object>> dist = new ArrayList<Map<String,Object>>();
			//FileInputStream in = new FileInputStream(file);
			Workbook book = WorkbookFactory.create(in);  
			Sheet sheet = book.getSheetAt(0);
			int lastRowNum = sheet.getLastRowNum();
			Iterator<Row> rows = sheet.rowIterator();
			Row title = rows.next(); //取得标题头行
			
			Iterator<Cell> cellTitle = title.cellIterator(); // 得到第一行的所有列  
			Map<Integer, String> titlemap = new HashMap<Integer, String>();// 将标题的文字内容放入到一个map中。   
			
			// 循环标题所有的列
			for (int i=0; cellTitle.hasNext(); i++) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				titlemap.put(i, value);
			}

			//解析内容行 
			for (int i=0; rows.hasNext(); i++) {
				Row rown = rows.next();
				Iterator<Cell> cellbody = rown.cellIterator();// 行的所有列   
				Map<String,Object> tObject = new HashMap<String,Object>(); // 行的所有列
				// 遍历一行的列   
				for (int j=0; cellbody.hasNext(); j++) {
					Cell cell = cellbody.next();
					//如果最后一行是统计行直接返回
					String str = this.getCellValue(cell);
					if(i==(lastRowNum-1) && j==0 && "合计".equals(str)){
						return dist;
					}
					tObject.put(titlemap.get(j), str);
				}
				dist.add(tObject);
			}
		

		return dist;
	}
	
    public String getCellValue(Cell cell) { 
    	DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
    public static  String formatDate(Date date)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sdf.format(date);
   }

//	public static void main(String[] args) {
//		ExcelImportUtils<Testpojo> test = new ExcelImportUtils<Testpojo>();   
//        File file = new File(PathUtil.CLASSES + "com/framework/commons/excel/testPojo.xls");   
//        Long befor = System.currentTimeMillis();   
//        List<Testpojo> result = (ArrayList<Testpojo>) test.importExcel(file, Testpojo.class);   
//  
//        Long after = System.currentTimeMillis();   
//        System.out.println("此次操作共耗时：" + (after - befor) + "毫秒");   
//         for (int i = 0; i < result.size(); i++) {   
//             Testpojo testpojo=result.get(i);   
//             System.out.println("导入的信息为："+testpojo.getLoginname()+  "---" +testpojo.getUsername()+ "----"+
//                 "----"+testpojo.getAge()+"---"+testpojo.getMoney()+"-----"+testpojo.getCreatetime());   
//         }   
//  
//        System.out.println("共转化为List的行数为：" + result.size());   
//	}
}
