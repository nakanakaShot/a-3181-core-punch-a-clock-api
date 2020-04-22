package com.herokuapp.a3181core.punchaclockdev.domain.service;

import com.herokuapp.a3181core.punchaclockdev.infrastructure.AttendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AttendService {

    private final AttendRepository attendRepository;

    /**
     * nameを受け取り固定値を返す
     *
     * @param name : APIを試験的に作った時のgetパラメータのひとつ
     * @return attendからもらう固定値
     */
    public String parameterBridge(String name) {

        if (isNotEmpty(name)) {
            return attendRepository.getParameter(name);
        }
        throw new RuntimeException();
    }

    private boolean isNotEmpty(String name) {
        return !StringUtils.isEmpty(name);

    }
}
