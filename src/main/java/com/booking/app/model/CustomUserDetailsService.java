package com.booking.app.model;

import com.booking.app.repository.UserRepository;
import com.booking.app.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Qualifier("customUserDetailsService")
public class CustomUserDetailsService extends UserDetailsServiceAutoConfiguration {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String name) throws UserNotFoundException {
        // returns the get(0) of the user list obtained from the db
        User domainUser = userRepository.findByUsername(name);

        CustomUserDetail customUserDetail=new CustomUserDetail();
        customUserDetail.setUser(domainUser);
        return (UserDetails) customUserDetail;

    }


}
