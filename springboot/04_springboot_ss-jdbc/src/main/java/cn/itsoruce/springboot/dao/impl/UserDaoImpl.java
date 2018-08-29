package cn.itsoruce.springboot.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.itsoruce.springboot.dao.IUserDao;
import cn.itsoruce.springboot.domian.User;


@Repository
public class UserDaoImpl implements IUserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void save(User user) {
		String sql = "insert into t_user(name) values(?)";
		jdbcTemplate.update(sql , user.getName());
	}

	@Override
	public void remove(Long id) {

	}

	@Override
	public void update(User user) {

	}

	@Override
	public User loadById(Long id) {
		return null;
	}

	@Override
	public List<User> loadAll() {
		String sql = "select * from t_user";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

}

class UserRowMapper implements RowMapper<User>
{
	//把一行转换为一个对象
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		User result = new User();
		
		result.setId(rs.getLong("id"));
		result.setName(rs.getString("name"));
		return result;
	}
	
}
