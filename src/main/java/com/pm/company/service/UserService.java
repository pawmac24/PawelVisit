package com.pm.company.service;

import com.pm.company.model.User;

/**
 * Created by pmackiewicz on 2015-11-04.
 */
public interface UserService {
    User findByUsername(String username);
}
