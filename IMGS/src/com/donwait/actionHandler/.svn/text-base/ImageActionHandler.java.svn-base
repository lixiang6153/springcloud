package com.donwait.actionHandler;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.donwait.model.Image;

@Component
@Transactional(rollbackFor = Exception.class)
public class ImageActionHandler extends BaseActionHandler{
	public boolean add(Image img) {
		try {
			img.setId(UUID.randomUUID().toString());
			getImageService().add(img);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Image img) {
		try {
			getImageService().delete(img.getId());
			return true;	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Image img) {
		try {
			getImageService().update(img);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Image find(Image img) {
		try {
			return getImageService().find(img.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public List<Image> findAll(Integer page, Integer size) {
		try {
			return getImageService().findAll(page, size);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int executeSql(String sql){
		try {
			return getImageService().addOrUpdateOrDeleteBySql(sql);	
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
