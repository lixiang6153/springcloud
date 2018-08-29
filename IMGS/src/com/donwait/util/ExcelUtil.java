package com.donwait.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.donwait.constant.Constants;


public class ExcelUtil<T> {
	public void exportExcel(String workbook,List<T> dataset, OutputStream out) {
		exportExcel(workbook, null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String workbook,String[] headers, List<T> dataset,
			OutputStream out) {
		exportExcel(workbook, headers, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, List<T> dataset,OutputStream out, String pattern,String workbook) {
		exportExcel(workbook, headers, dataset, out, pattern);
	}
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 *
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel(String title, String[] headers,
			List<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		initData(title, headers, dataset, pattern, workbook);
		writeAndClose(out, workbook);
	}
	/**
	 * 初始化sheet的内容
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param pattern
	 * @param workbook
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes", "unused" })
	private void initData(String title, String[] headers, List<T> dataset, String pattern,
			HSSFWorkbook workbook) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("Admin");

		//产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		//遍历集合数据，产生数据行
		if(dataset != null && dataset.size() > 0){
			Iterator<T> it = dataset.iterator();
			int index = 0;
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) it.next();
				
				//利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				Field[] fields = t.getClass().getDeclaredFields();
				int cur_column = 0;
				for (short i = 0; i < fields.length; i++) {

					Field field = fields[i];
					String fieldName = field.getName();
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					
					try {
						// 通过方法反射获取属性对应值
						Class tCls = t.getClass();
						Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						
						if(null == value){
							continue;
						}
						
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if(value instanceof List || value instanceof Map || value instanceof Set){
							// 为结合的每一元素生成对应列
							if (value instanceof List) {
								List<?> list=(List<?>) value;
								for (Object lo : list) {
									HSSFCell cell = row.createCell(cur_column);
									cell.setCellStyle(style2);	

									textValue = lo.toString();
									HSSFRichTextString richString = new HSSFRichTextString(textValue);
									HSSFFont font3 = workbook.createFont();
									font3.setColor(HSSFColor.BLUE.index);
									// 颜色判断
									Method m_color = lo.getClass().getMethod("getColor", new Class[] {});
									if(m_color != null){
										String txt_color = (String) m_color.invoke(lo, new Object[] {});
										if(txt_color != null && txt_color.equals(Constants.COLOR_BLUE)){
											font3.setColor(HSSFColor.BLUE.index);
										}else{
											font3.setColor(HSSFColor.RED.index);
										}
									}
									richString.applyFont(font3);
									cell.setCellValue(richString);
									cur_column ++;
								}
							}else if(value instanceof Map){
								//map的解析工作还没有完善
								Map<?, ?> map=(Map<?, ?>) value;
								Iterator<?> iterator = map.entrySet().iterator();
								while(iterator.hasNext()){
									Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
									Object entryKey = entry.getKey();
									Object entryValue = entry.getValue();
									
									HSSFCell cell = row.createCell(cur_column);
									cell.setCellStyle(style2);	
									
									textValue = entryValue.toString();
									HSSFRichTextString richString = new HSSFRichTextString(textValue);
									HSSFFont font3 = workbook.createFont();
									font3.setColor(HSSFColor.BLUE.index);
									// 颜色判断
									Method m_color = entryValue.getClass().getMethod("getColor", new Class[] {});
									if(m_color != null){
										String txt_color = (String) m_color.invoke(entryValue, new Object[] {});
										if(txt_color != null && txt_color.equals(Constants.COLOR_BLUE)){
											font3.setColor(HSSFColor.BLUE.index);
										}else{
											font3.setColor(HSSFColor.RED.index);
										}
									}
									
									richString.applyFont(font3);
									cell.setCellValue(richString);
									cur_column ++;
								}
							}else if(value instanceof Set){
								Set<?> set=(Set<?>) value;
								Iterator<?> iterator = set.iterator();
								while(iterator.hasNext()){
									Object so = iterator.next();
									
									HSSFCell cell = row.createCell(cur_column);
									cell.setCellStyle(style2);	
									
									textValue = so.toString();
									HSSFRichTextString richString = new HSSFRichTextString(textValue);
									HSSFFont font3 = workbook.createFont();
									font3.setColor(HSSFColor.BLUE.index);
									// 颜色判断
									Method m_color = so.getClass().getMethod("getColor", new Class[] {});
									if(m_color != null){
										String txt_color = (String) m_color.invoke(so, new Object[] {});
										if(txt_color != null && txt_color.equals(Constants.COLOR_BLUE)){
											font3.setColor(HSSFColor.BLUE.index);
										}else{
											font3.setColor(HSSFColor.RED.index);
										}
									}
									richString.applyFont(font3);
									cell.setCellValue(richString);
									cur_column ++;
								}
							}
							// 基本数据类型
						}else{
							
							HSSFCell cell = row.createCell(cur_column);
							cell.setCellStyle(style2);
							
							if (value instanceof Boolean) {

							} else if (value instanceof Date) {
								Date date = (Date) value;
								SimpleDateFormat sdf = new SimpleDateFormat(pattern);
								textValue = sdf.format(date);
							}  else if (value instanceof byte[]) {
								// 有图片时，设置行高为60px;
								row.setHeightInPoints(60);
								// 设置图片所在列宽度为80px,注意这里单位的一个换算
								sheet.setColumnWidth(i, (short) (35.7 * 80));
								// sheet.autoSizeColumn(i);
								byte[] bsValue = (byte[]) value;
								HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
										1023, 255, (short) 6, index, (short) 6, index);
								anchor.setAnchorType(2);
								patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
							}
							else{
								// 其它数据类型都当作字符串简单处理
								if(value != null){
									textValue = value.toString();
								}else{
									textValue = "";
								}							
							}
							
							// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
							if(textValue != null){
								Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
								Matcher matcher = p.matcher(textValue);
								if(matcher.matches()){
									// 是数字当作double处理
									cell.setCellValue(Double.parseDouble(textValue));
								}else{
									HSSFRichTextString richString = new HSSFRichTextString(textValue);
									HSSFFont font3 = workbook.createFont();
									font3.setColor(HSSFColor.BLUE.index);
									richString.applyFont(font3);
									cell.setCellValue(richString);
								}
							}
							
							cur_column ++;
						}
						
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						//清理资源
					}
				}

			}
		}
	}
	

	/*public static void main(String[] args) {
	      ExportExcelUtil<Att> ex = new ExportExcel<Student>();
	      String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
	      List<Student> dataset = new ArrayList<Student>();
	      dataset.add(new Student(10000001, "张三", 20, true, new Date()));
	      dataset.add(new Student(20000002, "李四", 24, false, new Date()));
	      dataset.add(new Student(30000003, "王五", 22, true, new Date()));
	      OutputStream out = new FileOutputStream("E://a.xls");
	       
	        ex.exportExcel(headers, dataset, out);

	        out.close();
	}*/
	
	/**
	 * 导出多个sheet的Excel,Map的key是sheet
	 * @param sheets sheet
	 * @param headers 每个sheet的标题
	 * @param datas 每一个sheet的数据
	 * @param out 输出流
	 * @throws FileNotFoundException
	 */
	public void exportExcelSheets(String[] sheets, Map<String,String[]> headers,Map<String, List<T>> datas,OutputStream out) throws FileNotFoundException{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		for(String sheet : sheets){
			String[] Headers = headers.get(sheet);
			List<T> datasOfsheet = datas.get(sheet);
			initData(sheet, Headers, datasOfsheet, "yyyy-MM-dd", workbook);
		}
		writeAndClose(out, workbook);		  
	}
	/**
	 * 输出及关闭流数据
	 * @param out
	 * @param workbook
	 */
	private void writeAndClose(OutputStream out, HSSFWorkbook workbook) {
		try {
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
}
