package com.herokuapp.a3181core.punchaclockdev.domain.service;

import com.herokuapp.a3181core.punchaclockdev.infrastructure.AttendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AttendService {

    private final AttendRepository attendRepository;

    public void parameterBridge(String name) {
        if (isNotEmpty(name)) {
            attendRepository.getParameter(name);
        }
    }

    private boolean isNotEmpty(String name) {
        return !StringUtils.isEmpty(name);

    }
}
