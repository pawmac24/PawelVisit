package com.pm.company.service;

import com.pm.company.dto.result.ExampleTestDTO;

/**
 * Created by pmackiewicz on 2016-02-03.
 */
public interface ExampleTestService {
    ExampleTestDTO get();

    ExampleTestDTO find();

    ExampleTestDTO delete();
}
