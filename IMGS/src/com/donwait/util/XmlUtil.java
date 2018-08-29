package com.donwait.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XmlUtil {
	 
    /** 
     * 解析发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();  
  
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
  
        // 释放资源  
        inputStream.close();  
        inputStream = null;  
  
        return map;  
    }  
  
	/**
	 * 获取XStream对象
	 * @return
	 */
	public static XStream getXStream() {
		//在文本前后加上<![CDATA[和]]>
		DomDriver domDriver = new DomDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					@Override
					protected void writeText(QuickWriter writer, String text) {
						/*if (text.startsWith("<![CDATA[") && text.endsWith("]]>")) {
							writer.write(text);
						} else {
							//super.writeText(writer, text);
							super.writeText(writer, "<![CDATA[" + text + "]]>");
						}*/
						writer.write(text);
					}
				};
			};
		};
		
		//去除XML属性在JavaBean中映射不到属性值的异常
		XStream xStream = new XStream(domDriver){       
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {                 
				return new MapperWrapper(next) {
					@Override
					@SuppressWarnings("rawtypes")
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {                      
						if (definedIn == Object.class) {                     
							try {                      
								return this.realClass(fieldName) != null;                      
							} catch(Exception e) {                      
								return false;                      
							}                      
						} else {                             
							return super.shouldSerializeMember(definedIn, fieldName);                         
						}                     
					}                 
				};            
			}         
		};

		return xStream;
	}

	/**
	 * 获取xml一级节点文本值，不区分元素名称大小写
	 * @param xml
	 * @param element
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getStairText(String xml, String elementName){
		elementName = elementName.toLowerCase();
		String result = null;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			for(Iterator iterTemp = root.elementIterator(); iterTemp.hasNext();) {	
				Element element = (Element) iterTemp.next();	
				if(element.getName().toLowerCase().equals(elementName)){
					result = element.getText();
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 把xml转bean对象
	 * @param xml
	 * @param map
	 * @return
	 */
	public static Object xmlToBean(String xml, Map<String, Class<?>> map) {
		XStream xStream = getXStream();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			xStream.alias(key, map.get(key));
		}
		return xStream.fromXML(xml);
	}
	
	/**
	 * bean对象转xml
	 * @param bean
	 * @param rootClass 根节点名称转换
	 * @return
	 */
	public static String beanToXml(Object bean, Class<?> rootClass){
		XStream xStream = getXStream();
		xStream.alias("xml", rootClass);
		String content = xStream.toXML(bean);
		content = content.replaceAll("&lt;", "<");// <
		content = content.replaceAll("&gt;", ">");// >
		return content;
	} 
	
	public static String textToXml(String text, String rootName){
		XStream xStream = getXStream();
		xStream.alias(rootName, String.class);
		String content = xStream.toXML(text);
		content = content.replaceAll("&lt;", "<");// <
		content = content.replaceAll("&gt;", ">");// >
		return content;
	} 
	public static void write(Document document,String filePath) throws Exception{
		//把生成的xml文档存放在硬盘上  true代表是否换行  
        OutputFormat format = new OutputFormat("    ",true);  
        format.setEncoding("UTF-8");//设置编码格式  
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filePath),format);  
        xmlWriter.write(document);  
        xmlWriter.close();  
	};
	public static Document read(String filePath) throws Exception{
		FileInputStream inputStream=new FileInputStream(filePath);
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        inputStream.close();
        return document;
	}
	public static void main(String[] args) {
		
	}
    
}
