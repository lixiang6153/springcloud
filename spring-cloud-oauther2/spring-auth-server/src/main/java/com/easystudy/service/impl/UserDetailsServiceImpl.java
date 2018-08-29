package com.easystudy.service.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.Rights;
import com.easystudy.model.Role;
import com.easystudy.model.RoleRights;
import com.easystudy.service.RightsService;
import com.easystudy.service.RoleRightsService;
import com.easystudy.service.RoleService;
import com.easystudy.service.UserService;

/**
 * 实际的项目中，我们的登录用户数据可能存在数据库中，也可能是存放在ladap或其他微服务接口中，
 * springcloud oauth2给我们提供了一个UserDetailsService接口,在项目中，
 * 我们需要自行实现这个接口来获取用户信息
 * 提供了获取UserDetails的方式，只要实现UserDetailsService接口即可，
 * 最终生成用户和权限共同组成的UserDetails，在这里就可以实现从自定义的数据源
 * 中获取用户信息
 * @author Administrator
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;					// 用户服务
    @Autowired
    private RoleService roleService;					// 角色服务
    @Autowired
    private RoleRightsService roleRightsService;		// 权限服务
    @Autowired
    private RightsService rightsService;				// 权限服务


    /**
     * 通过用户名获取用户信息给oauth2
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	// 查找用户
    	ReturnValue<com.easystudy.model.User> userResult = userService.findByUsername(username);
        if (userResult.getError() != 0) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        
        // 查找的结果bean拷贝到userVo
        com.easystudy.model.User userVo = new com.easystudy.model.User();
        BeanUtils.copyProperties(userResult.getValue(),userVo);
        
        // 用户用户id查找对应的角色
        ReturnValue<List<Role>> roleResult = roleService.getRolesByUserId(userVo.getId());
        
        // 设置用户权限
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        if (0 == roleResult.getError()){        	
        	// 获取所有角色权限
            List<Role> roleVoList = roleResult.getValue();
            for (Role role : roleVoList){
            	
                // 角色必须是ROLE_开头，可以在数据库中设置---WebSecurityConfig也必须对应进行http验证,即保持一致---
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getName());
                grantedAuthorities.add(grantedAuthority);
                
                // 其他角色-每一个权限
                ReturnValue<List<RoleRights>> perResult = roleRightsService.getRoleRights(role.getId());
                if (0 == perResult.getError()){
                	List<RoleRights> permissionList = perResult.getValue();
                    for (RoleRights roleRight : permissionList) {
                    	ReturnValue<Rights> right = rightsService.findByRightId(roleRight.getRight_id());
                    	if(0 == right.getError()){
                    		GrantedAuthority authority = new SimpleGrantedAuthority(right.getValue().getName());
                            grantedAuthorities.add(authority);
                    	}
                    }
                }
            }
        }
        
        // 标识位设置
        boolean enabled = true; 						// 可用性 :true:可用 false:不可用
        boolean accountNonExpired = true; 				// 过期性 :true:没过期 false:过期
        boolean credentialsNonExpired = true; 			// 有效性 :true:凭证有效 false:凭证无效
        boolean accountNonLocked = true; 				// 锁定性 :true:未锁定 false:已锁定
        
        return new User(userVo.getUsername(), userVo.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
    }
}
