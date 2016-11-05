package com.fifaminer.service.common.impl;

import com.fifaminer.service.common.ClockService;
import org.springframework.stereotype.Service;

@Service
public class ClockServiceImpl implements ClockService {

    @Override
    public Long now() {
        return System.currentTimeMillis();
    }
}
