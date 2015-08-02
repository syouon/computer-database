package com.excilys.dao.impl;

import com.excilys.dao.UserDAO;
import com.excilys.dao.UserService;
import com.excilys.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by syouon on 01/08/15.
 */
@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO dao;

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        com.excilys.model.User user = dao.findByUsername(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user
                .getAuthorities());

        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.excilys.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Authority> userRoles) {
        return userRoles.stream().map(authority ->
                new SimpleGrantedAuthority(authority.getRole())).collect(Collectors.toList());
    }
}
