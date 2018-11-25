package org.mpo.newstracker.service;

import org.mpo.newstracker.entity.dao.UserDao;
import org.mpo.newstracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${news-admin.username}")
    private String adminUsername;

    @Value("${news-admin.password}")
    private String adminPassword;

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /* org.mpo.newstracker.entity.dao.UserDao daoUser = userRepository.findByUsername(username);
        if (daoUser==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserDao(daoUser.getUsername(),daoUser.getPassword(), AuthorityUtils.createAuthorityList(daoUser.getRole()));*/

        log.info("search for user: "+username);
        //need to put {noop} because of some password encription error
        if(username.equals(adminUsername)){
            return new User(adminUsername,"{noop}"+adminPassword, AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER"));
        }
        //return new User("batman@cz.cz","{noop}pass", AuthorityUtils.createAuthorityList("ROLE_USER"));
        UserDao userDao = getUserByUsername(username);
        return new User(userDao.getUsername(),"{noop}"+userDao.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
    private UserDao getUserByUsername(String username){
        if(userRepository.existsByUsername(username)){
            return userRepository.findByUsername(username);
        }
        else
        {
            throw new UsernameNotFoundException(username);
        }
        //return userRepository.existsByUsername(username) ? userRepository.findByUsername(username) : null ;//throw  new RuntimeException("user not found!!!");
    }

}
