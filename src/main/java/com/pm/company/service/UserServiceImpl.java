package com.pm.company.service;

import com.pm.company.model.User;
import com.pm.company.repository.UserRepository;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    public final static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        //UserDTO userDTO = mapper.map(user, UserDTO.class);
        return user;
    }
}
