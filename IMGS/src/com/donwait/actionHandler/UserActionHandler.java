package com.donwait.actionHandler;

import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.donwait.error.ErrorCode;
import com.donwait.model.User;
import com.donwait.util.EncryptorUtil;
import com.donwait.util.JsonUtil;
import com.donwait.util.ReturnValue;

@Component
@Transactional(rollbackFor = Exception.class)
public class UserActionHandler extends BaseActionHandler {

	public Object login(User user) {
		try {
			List<User> users = getUserService().findAll(Restrictions.eq("userName", user.getUserName()));
			if (users == null || users.size() == 0) {
				return new ReturnValue(ErrorCode.ERROR_NOT_FOUND);
			}
			User u = users.get(0);
			if (EncryptorUtil.checkPassword(user.getPassword(), u.getPassword())) {
				user.setToken(UUID.randomUUID().toString());
				// 返回用户token
				JSONObject jsonObject = new JSONObject(); 
				jsonObject.put("token", user.getToken());
				return new ReturnValue(jsonObject);
			}
			return new ReturnValue(ErrorCode.ERROR_USER_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnValue(ErrorCode.ERROR_SERVER_ERROR);
		}
	}

	public Object add(User user) {
		try {
			List<User> users = getUserService().findAll(Restrictions.eq("userName", user.getUserName()));
			if (!isNull(users)) {
				return new ReturnValue(ErrorCode.ERROR_OBJECT_EXIST);
			}
			String password = user.getPassword();
			String encryptPassword = EncryptorUtil.encryptPassword(password);
			user.setPassword(encryptPassword);
			getUserService().add(user);
			return new ReturnValue();
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnValue(ErrorCode.ERROR_SERVER_ERROR);
		}
	}

	public Object delete(User user) {
		try {
			User u = getUserService().find(user.getUserName());
			if(u != null){
				getUserService().delete(u);
				return new ReturnValue();
			}
			return new ReturnValue(ErrorCode.ERROR_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnValue(ErrorCode.ERROR_SERVER_ERROR);
		}		
	}

	public Object find(User user){
		try {
			User u = getUserService().find(user.getUserName());
			if(u != null){
				JSONObject userJSON = JsonUtil.getJsonObject(u);
				return new ReturnValue(userJSON);
			}
			return new ReturnValue(ErrorCode.ERROR_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnValue(ErrorCode.ERROR_SERVER_ERROR);
		}
	}

	public Object findAll(Integer page, Integer size){
		try {
			List<User> users = getUserService().findAll(page, size);
			if(users !=null && users.size() > 0){
				JSONArray jsonArray = new JSONArray();
				for (User user : users) {
					JSONObject userJSON = JsonUtil.getJsonObject(user);
					jsonArray.put(userJSON);
				}
				return new ReturnValue(jsonArray);
			}
			return new ReturnValue(ErrorCode.ERROR_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnValue(ErrorCode.ERROR_SERVER_ERROR);
		}
	}

	public Object update(User user) {
		try {
			User u = getUserService().find(user.getUserName());
			if(u != null){
				JsonUtil.updateObjectData(u, user);
				getUserService().update(u);
				return new ReturnValue();
			}
			return new ReturnValue(ErrorCode.ERROR_NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnValue(ErrorCode.ERROR_SERVER_ERROR);
		}	
	}
}
