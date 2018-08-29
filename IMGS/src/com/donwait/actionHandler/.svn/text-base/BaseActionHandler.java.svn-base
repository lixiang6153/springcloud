package com.donwait.actionHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.donwait.service.BaseService;
import com.donwait.service.ImageService;
import com.donwait.service.UserService;
import com.donwait.util.JsonUtil;

public class BaseActionHandler {
	@Autowired
	private ImageService imageService;
	@Autowired
	private UserService userService;
	
	
	public ImageService getImageService() {
		return imageService;
	}

	public void setCompanyService(ImageService companyService) {
		this.imageService = companyService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public Boolean isNull(Object object){
		if(null != object){
			return false;
		}
		return true;
	}
	
	public Boolean isNull(String str){
		if(str != null && !str.trim().equals("")){
			return false;
		}
		return true;
	}
	public Boolean isNull(List<?> list){
		if(list !=null && list.size() >0){
			return false;
		}
		return true;
	}
	
	/**
	 * 查询
	 * @param size 
	 * @param page 
	 * @param order 
	 * @param group 
	 * @param alarmType 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	//@Cacheable(value={"PersonFlowAlarmsCache"},key="'BaseActionService_query_'+#service+'_'+#page+'_'+#size+'_'+#paramsDescription+'_'+#order+'_'+#orderProp",condition="#service=='PersonFlowAlarmService'")
	public List query(String service, Integer page, Integer size,String paramsDescription, String order,String orderProp) {
		List<?> list = new ArrayList();
		try {
			if(!isNull(service)){
				BaseService<?> serviceEntity = parseService(service);
				Criterion[] criterions=parseParamsDes(paramsDescription);
				DetachedCriteria criteria = parseCriteria(order, orderProp, serviceEntity);			
				if(criterions!=null&&criterions.length>0){
					for (Criterion criterion : criterions) {
						criteria.add(criterion);
					}
				}
				if(page!=null&&page>0&&size!=null&&size>0){
					list=serviceEntity.findAll(page, size,criteria);
				}else{
					list=serviceEntity.findAll(criteria);
				}		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 非分组或降序查询
	 * @param page
	 * @param size
	 * @param serviceEntity
	 * @param criterions
	 * @param criteria
	 * @return
	 */
	/*private List queryNotByCriteria(Integer page, Integer size, BaseService serviceEntity, Criterion[] criterions) {
		List list;
		if(criterions!=null&&criterions.length>0){
			if(page!=null&&page>0&&size!=null&&size>0){
				list=serviceEntity.findAll(page, size,criterions);
			}else{
				list=serviceEntity.findAll(criterions);
			}		
		}else{
			if(page!=null&&page>0&&size!=null&&size>0){
				list=serviceEntity.findAll(page, size);
			}else{
				list=serviceEntity.findAll();
			}
		}
		return list;
	}*/
	
	/**
	 * 根据参数取得DetachedCriteria
	 * @param groupProp
	 * @param order
	 * @param orderProp
	 * @param serviceEntity
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public DetachedCriteria parseCriteria(String order, String orderProp,
			BaseService serviceEntity) {
		DetachedCriteria criteria = DetachedCriteria.forClass(serviceEntity.getEntityClass());
		if(!isNull(order)&&!isNull(orderProp)){
			if(order.equals("desc")){
				criteria.addOrder(org.hibernate.criterion.Order.desc(orderProp));
			}else if(order.equals("asc")){
				criteria.addOrder(org.hibernate.criterion.Order.asc(orderProp));
			}
		}
		
		return criteria;
	}
	/**
	 * 取得实体类的帮助文档资料
	 * @param service
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String help(String service){
		BaseService serviceEntity = parseService(service);
		String str=null;
		if(serviceEntity!=null){
			Class entityClass = serviceEntity.getEntityClass();
			JSONObject entityClassJSON = JsonUtil.help(entityClass);
			if(entityClassJSON!=null){
				str = JsonUtil.format(entityClassJSON.toString());
			}
		}
		return str;
	}
	/**
	 * 根据service取得对应的接口
	 * @param service
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public BaseService parseService(String service){
		BaseService serviceEntity = null;
		try {
			String methodName="get"+service;
			Method method = BaseActionHandler.class.getMethod(methodName);
			serviceEntity = (BaseService) method.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceEntity;
	}
	/**
	 * 解析字符串取得查询条件
	 * @param paramsDescription
	 * @param order 
	 * @param group 
	 * @return
	 * @throws ParseException 
	 */
	public Criterion[] parseParamsDes(String paramsDescription) throws ParseException {
		//"deviceId::=1111::Integer;type::$1,2,4,8::Operator;alarmTyime::>2017-12-01::date....";
		if(paramsDescription!=null&&!paramsDescription.trim().equals("")){
			String[] params = paramsDescription.split(";");
			Criterion[] criterions=new Criterion[params.length];
			for (int i = 0;i < params.length;i++) {
				String param = params[i];
				String[] array=param.split("::");
				String field=array[0];
				Criterion expression =null;
				String value = array[1].substring(1);
				Object v = parseParamClass(array, value);
				expression = parseCriterion(array, field, expression, v);
				criterions[i]=expression;
			}
			return criterions;
		}
		return null;
	}
	/**
	 * 解析查询条件
	 * @param array
	 * @param field
	 * @param expression
	 * @param v
	 * @return
	 */
	private Criterion parseCriterion(String[] array, String field, Criterion expression, Object v) {
		if(array[1].startsWith("=")){
			expression = Restrictions.eq(field,v);
		}else if(array[1].startsWith(">")){
			expression = Restrictions.gt(field,v);
		}else if(array[1].startsWith("<")){
			expression = Restrictions.lt(field,v);
		}else if(array[1].startsWith("%") || array[1].startsWith("-")){
			expression = Restrictions.like(field,"%"+v+"%");
		}else if(array[1].startsWith("#")){
			expression = Restrictions.le(field,v);
		}else if(array[1].startsWith("^")){
			expression = Restrictions.ge(field,v);
		}else if(array[1].startsWith("!")){
			expression = Restrictions.ne(field,v); 
		}else if(array[1].startsWith("$")){
			String str = (String) v;
			String[] strs = str.split(",");
			Disjunction or = Restrictions.or();
			//Conjunction conjunction = Restrictions.conjunction();
			for (String o : strs) {
				or.add(Restrictions.eq(field,Integer.parseInt(o)));
			}
			expression  = or;
		}
		return expression;
	}
	/**
	 * 解析参数类型
	 * @param array
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	private Object parseParamClass(String[] array, String value) throws ParseException {
		Object v=null;
		if(array[2].equals("Integer")||array[2].equals("int")){
			 v = Integer.parseInt(value);
		}else if(array[2].equals("Date")){
			if(value.length()==10){
				v=DateUtils.parseDate(value, "yyyy-MM-dd");
			}else if(value.length()==7){
				v=DateUtils.parseDate(value, "yyyy-MM");
			}else if(value.length()==4){
				v=DateUtils.parseDate(value, "yyyy");
			}else{
				v=DateUtils.parseDate(value, "yyyy-MM-dd HH:mm:ss");
			}
			
	
		}else if(array[2].equals("String")){
			v=value;
		}else if(array[2].equals("Boolean")||array[2].equals("boolean")){
			v=Boolean.parseBoolean(value);
		}else if(array[2].equals("Long")||array[2].equals("long")){
			v=Long.parseLong(value);
		}else if(array[2].equals("Short")||array[2].equals("short")){
			v=Short.parseShort(value);
		}else if(array[2].equals("Float")||array[2].equals("float")){
			v=Float.parseFloat(value);
		}else if(array[2].equals("Double")||array[2].equals("double")){
			v=Double.parseDouble(value);
		}else{
			v=value;
		}
		return v;
	}
	/**
	 * 根据条件查询查询数量
	 * @param alarmService
	 * @param paramsDescription
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long getMax(String service, String paramsDescription) {
		String methodName="get"+service;
		Long rowCount=null;
		try {
			Method method = BaseActionHandler.class.getMethod(methodName);
			BaseService serviceEntity = (BaseService) method.invoke(this);
			Criterion[] criterions=parseParamsDes(paramsDescription);
			if(criterions!=null&&criterions.length>0){
				rowCount = serviceEntity.getRowCount(criterions);
			}else{
				rowCount=serviceEntity.getRowCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	/**
	 * 根据条件查询数量
	 * @param service
	 * @param criterions
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long getMaxByCriterions(String service,Criterion...criterions){
		BaseService baseService = parseService(service);
		Long max=baseService.getRowCount(criterions);
		return max;
	};
	/**
	 * 根据条件取得大最大页码
	 * @return
	 */
	public Integer getMaxPage(String service,Integer size, String paramsDescription){
		Long max = getMax(service, paramsDescription);
		return getMaxPage(size, max);	
	}
	/**
	 * 根据条件取得大最大页码
	 * @return
	 */
	public Integer getMaxPage(Integer size, Long max) {
		if(max%size==0){
			return (int) (max/size);
		}
		return (int) (max/size+1);
	}
	/**
	 * 根据条件取得大最大页码
	 * @return
	 */
	public Integer getMaxPageByCriterions(String service,Integer size,Criterion...criterions){
		Long max = getMaxByCriterions(service, criterions);
		return getMaxPage(size, max);	
		
	}
}
