package com.cbt.dao;

import com.cbt.entity.MessageLogTab;

public interface MessageLogTabMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageLogTab record);

    int insertSelective(MessageLogTab record);

    MessageLogTab selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MessageLogTab record);

    int updateByPrimaryKey(MessageLogTab record);
}