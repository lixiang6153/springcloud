package com.donwait.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {

	/**
	 * 连接池对象
	 */
	protected static Connection connection;
	private static final String ZK_QUORUM = "hbase.zookeeper.quorum";
	private static final String ZK_CLIENT_PORT = "hbase.zookeeper.property.clientPort";
	/**
	 * HBase位置-Hbase配置的位置-根据Hadoop配置
	 */
	private static final String HBASE_POS = "192.168.12.104";

	/**
	 * ZooKeeper位置
	 */
	private static final String ZK_POS = "192.168.12.104,192.168.12.105,192.168.12.106";

	/**
	 * zookeeper服务端口
	 */
	private final static String ZK_PORT_VALUE = "2181";

	/**
	 * 静态构造，在调用静态方法时前进行运行 初始化连接对象．
	 */
	static {
		Configuration configuration = HBaseConfiguration.create();
	//  hbase.rootdir -->  hdfs://hadoop1:9000/hbase
		configuration.set("hbase.rootdir", "hdfs://" + HBASE_POS + ":9000/hbase");
		configuration.set(ZK_QUORUM, ZK_POS);
		configuration.set(ZK_CLIENT_PORT, ZK_PORT_VALUE);
		
		//configuration.set("zookeeper.znode.parent", "/hbase");
		configuration.set("hbase.client.retries.number", "3"); 
		configuration.set("hbase.rpc.timeout", "2000"); 
		configuration.set("hbase.client.operation.timeout", "3000"); 
		configuration.set("hbase.client.scanner.timeout.period", "10000"); 
		try {
			connection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		} // 创建连接池
	}

	/**
	 * 构造函数，用于初始化内置对象
	 */
	public HBaseUtil() {
		Configuration configuration = HBaseConfiguration.create();
		//  hbase.rootdir -->  hdfs://hadoop1:9000/hbase
		configuration.set("hbase.rootdir", "hdfs://" + HBASE_POS + ":9000/hbase");
		configuration.set(ZK_QUORUM, ZK_POS);
		configuration.set(ZK_CLIENT_PORT, ZK_PORT_VALUE);
		configuration.set("zookeeper.znode.parent", "/hbase");
		configuration.set("hbase.client.retries.number", "3"); 
		configuration.set("hbase.rpc.timeout", "2000"); 
		configuration.set("hbase.client.operation.timeout", "3000"); 
		configuration.set("hbase.client.scanner.timeout.period", "10000"); 
		try {
			connection = ConnectionFactory.createConnection(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		} // 创建连接池
	}

	/**
	 * @param tableName
	 *            创建一个表 tableName 指定的表名 seriesStr
	 * @param seriesStr
	 *            以字符串的形式指定表的列族，每个列族以逗号的形式隔开,(例如：＂f1,f2＂两个列族，分别为f1和f2)
	 **/
	public boolean createTable(String tableName, String seriesStr) {
		boolean isSuccess = false;// 判断是否创建成功！初始值为false
		Admin admin = null;
		TableName table = TableName.valueOf(tableName);
		try {
			admin = connection.getAdmin();
			if (!admin.tableExists(table)) {
				System.out.println("INFO:Hbase::  " + tableName + "原数据库中表不存在！开始创建...");
				HTableDescriptor descriptor = new HTableDescriptor(table);
				String[] series = seriesStr.split(",");
				for (String s : series) {
					descriptor.addFamily(new HColumnDescriptor(s.getBytes()));
				}
				admin.createTable(descriptor);
				System.out.println("INFO:Hbase::  " + tableName + "新的" + tableName + "表创建成功！");
				isSuccess = true;
			} else {
				System.out.println("INFO:Hbase::  该表已经存在，不需要在创建！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(admin);
		}
		return isSuccess;
	}

	/**
	 * 删除指定表名的表
	 * 
	 * @param tableName
	 *            表名
	 * @throws IOException
	 */
	public boolean dropTable(String tableName) throws IOException {
		boolean isSuccess = false;// 判断是否创建成功！初始值为false
		Admin admin = null;
		TableName table = TableName.valueOf(tableName);
		try {
			admin = connection.getAdmin();
			if (admin.tableExists(table)) {
				admin.disableTable(table);
				admin.deleteTable(table);
				isSuccess = true;
			}
		} finally {
			IOUtils.closeQuietly(admin);
		}
		return isSuccess;
	}

	/**
	 * 向指定表中插入数据
	 * 
	 * @param tableName
	 *            要插入数据的表名
	 * @param rowkey
	 *            指定要插入数据的表的行键
	 * @param family
	 *            指定要插入数据的表的列族family
	 * @param qualifier
	 *            要插入数据的qualifier
	 * @param value
	 *            要插入数据的值value
	 */
	protected static void putDataH(String tableName, String rowkey, String family, String qualifier, Object value)
			throws IOException {
		Admin admin = null;
		TableName tN = TableName.valueOf(tableName);
		admin = connection.getAdmin();
		if (admin.tableExists(tN)) {
			try (Table table = connection.getTable(TableName.valueOf(tableName.getBytes()))) {
				// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值 	        
				Put put = new Put(rowkey.getBytes());
				put.addColumn(family.getBytes(), qualifier!= null ?qualifier.getBytes():null, value.toString().getBytes());
				
//				put.add("columnFamilly1".getBytes(), "col1", "aaa".getBytes());// 本行数据的第一列 
//		        put.add("columnFamilly2".getBytes(), "col2", "bbb".getBytes());// 本行数据的第三列 
//		        put.add("columnFamilly3".getBytes(), "col3", "ccc".getBytes());// 本行数据的第三列 
		        
				table.put(put);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("插入数据的表不存在，请指定正确的tableName ! ");
		}
	}

	/**
	 * 根据指定表获取指定行键rowkey和列族family的数据 并以字符串的形式返回查询到的结果
	 * 
	 * @param tableName
	 *            要获取表 tableName 的表名
	 * @param rowKey
	 *            指定要获取数据的行键
	 * @param family
	 *            指定要获取数据的列族元素
	 * @param qualifier
	 *            指定要获取数据的qualifier
	 * 
	 */
	protected static String getValueBySeriesH(String tableName, String rowKey, String family, String qualifier)
			throws IllegalArgumentException, IOException {
		Table table = null;
		String resultStr = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName.getBytes()));
			Get get = new Get(Bytes.toBytes(rowKey));
			if (!get.isCheckExistenceOnly()) {
				get.addColumn(Bytes.toBytes(family), qualifier != null?Bytes.toBytes(qualifier):null);
				Result res = table.get(get);
				byte[] result = res.getValue(Bytes.toBytes(family), qualifier != null?Bytes.toBytes(qualifier):null);
				resultStr = Bytes.toString(result);
			} else {
				resultStr = null;
			}
		} finally {
			IOUtils.closeQuietly(table);
		}
		return resultStr;
	}

	/**
	 * 根据table查询表中的所有数据 无返回值，直接在控制台打印结果
	 */
	@SuppressWarnings("deprecation")
	public void getValueByTable(String tableName) throws Exception {
		Table table = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			ResultScanner rs = table.getScanner(new Scan());
			for (Result r : rs) {
				System.out.println("获得到rowkey:" + new String(r.getRow()));
				for (KeyValue keyValue : r.raw()) {
					System.out.println("列：" + new String(keyValue.getFamily()) + ":"
							+ new String(keyValue.getQualifier()) + "====值:" + new String(keyValue.getValue()));
				}
			}
		} finally {
			IOUtils.closeQuietly(table);
		}
	}

	/**
	 * 根据指定表获取指定行键rowKey和列族family的数据 并以Map集合的形式返回查询到的结果
	 * 
	 * @param tableName
	 *            要获取表 tableName 的表名
	 * @param rowKey
	 *            指定的行键rowKey
	 * @param family
	 *            指定列族family
	 */

	protected static Map<String, String> getAllValueＨ(String tableName, String rowKey, String family)
			throws IllegalArgumentException, IOException {
		Table table = null;
		Map<String, String> resultMap = null;
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(rowKey));
			if (get.isCheckExistenceOnly()) {
				Result res = table.get(get);
				Map<byte[], byte[]> result = res.getFamilyMap(family.getBytes());
				Iterator<Entry<byte[], byte[]>> it = result.entrySet().iterator();
				resultMap = new HashMap<String, String>();
				while (it.hasNext()) {
					Entry<byte[], byte[]> entry = it.next();
					resultMap.put(Bytes.toString(entry.getKey()), Bytes.toString(entry.getValue()));
				}
			}
		} finally {
			IOUtils.closeQuietly(table);
		}
		return resultMap;
	}

	/**
	 * 根据指定表获取指定行键rowKey的所有数据 并以Map集合的形式返回查询到的结果 每条数据之间用&&&将Qualifier和Value进行区分
	 * 
	 * @param tableName
	 *            要获取表 tableName 的表名
	 * @param rowkey
	 *            指定的行键rowKey
	 */
	public ArrayList<String> getFromRowkeyValues(String tableName, String rowkey) {
		Table table = null;
		ArrayList<String> Resultlist = new ArrayList<>();
		Get get = new Get(Bytes.toBytes(rowkey));
		try {
			table = connection.getTable(TableName.valueOf(tableName));
			Result r = table.get(get);
			for (Cell cell : r.rawCells()) {
				// 每条数据之间用&&&将Qualifier和Value进行区分
				String reString = Bytes.toString(CellUtil.cloneQualifier(cell)) + "&&&"
						+ Bytes.toString(CellUtil.cloneValue(cell));
				Resultlist.add(reString);
			}
			table.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return Resultlist;
	}

	/**
	 * 根据表名获取所有的数据
	 */
	@SuppressWarnings("unused")
	private void getAllValues(String tableName) {
		try {
			Table table = connection.getTable(TableName.valueOf(tableName));
			Scan scan = new Scan();
			ResultScanner resutScanner = table.getScanner(scan);
			for (Result result : resutScanner) {
				System.out.println("scan:  " + result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getTestDate(String tableName) throws IOException {
		Table table = null;
		table = connection.getTable(TableName.valueOf(tableName));
		int count = 0;
		Scan scan = new Scan();
		scan.addFamily("f".getBytes());

		Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("112213.*"));
		scan.setFilter(filter);
		ResultScanner resultScanner = table.getScanner(scan);
		for (Result result : resultScanner) {
			System.out.println(result);
			count++;
		}
		System.out.println("INFO:Hbase::  测试结束！共有　" + count + "条数据");
	}

}