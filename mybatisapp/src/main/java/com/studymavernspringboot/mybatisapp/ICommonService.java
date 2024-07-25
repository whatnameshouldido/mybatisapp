package com.studymavernspringboot.mybatisapp;

import java.util.List;

public interface ICommonService<T> {
    T findById(Long id);
    List<T> getAllList();
    T insert(T dto) throws Exception;
    Boolean remove(Long id) throws Exception;
    T update(Long id, T dto) throws Exception;
}
