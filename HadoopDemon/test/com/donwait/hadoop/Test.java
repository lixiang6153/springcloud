package com.donwait.hadoop;

import java.io.IOException;

public class Test {
	/**
	 * 做多表存储使用【相对于关系型数据库中的表通话记录表、联系人表】
	 * @throws IOException
	 */
	@org.junit.Test
	public void testCreateCallLog() throws IOException{
		HBaseUtil hbase = new HBaseUtil();
		// 通话记录，联系人
		hbase.createTable("CallLog", "Record,Contact");
		
		// 通话记录 from 指点给 to， from的具体联系人详情Contact(类似于RDBMS中的Record表和Contact表）
		hbase.putDataH("CallLog", "row1", "Record", "from", "13556892563");
		hbase.putDataH("CallLog", "row1", "Record", "to", "13256894751");
		hbase.putDataH("CallLog", "row1", "Contact", "name", "lixx");
		hbase.putDataH("CallLog", "row1", "Contact", "addr", "test address");
	}
	
	@org.junit.Test
	public void testDropCallLog() throws IOException{
		HBaseUtil hbase = new HBaseUtil();
		// 通话记录，联系人
		hbase.dropTable("CallLog");
	}
	
	/**
	 * 做多表存储使用
	 * @throws IOException
	 */
	@org.junit.Test
	public void testGetFromCallLog() throws IllegalArgumentException, IOException{
		HBaseUtil hbase = new HBaseUtil();	
		// Record表
		System.out.println(hbase.getValueBySeriesH("CallLog", "row1", "Record", "from"));
		System.out.println(hbase.getValueBySeriesH("CallLog", "row1", "Record", "to"));
		
		// Contact表
		System.out.println(hbase.getValueBySeriesH("CallLog", "row1", "Contact", "name"));
		System.out.println(hbase.getValueBySeriesH("CallLog", "row1", "Contact", "addr"));
	}
	
	/**
	 * 做单表存储使用
	 * @throws IOException
	 */
	@org.junit.Test
	public void testCreatePerson() throws IOException{
		HBaseUtil hbase = new HBaseUtil();
		// 通话记录，联系人
		hbase.createTable("Person", "name,sex,age");
		
		hbase.putDataH("Person", "id11", "name", null, "lixx");
		hbase.putDataH("Person", "id11", "sex", null, "20");
		hbase.putDataH("Person", "id11", "age", null, "1");
	}
	
	/**
	 * 做单表存储使用
	 * @throws IOException
	 */
	@org.junit.Test
	public void testGetFromPerson() throws IOException{
		HBaseUtil hbase = new HBaseUtil();
		// 通话记录，联系人
		hbase.createTable("Person", "name,sex,age");
		
		System.out.println(hbase.getValueBySeriesH("Person", "id11", "name", null));
		System.out.println(hbase.getValueBySeriesH("Person", "id11", "sex", null));
		System.out.println(hbase.getValueBySeriesH("Person", "id11", "age", null));
	}
}
