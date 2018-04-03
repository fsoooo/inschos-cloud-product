package com.inschos.proposal.mapper;

import com.inschos.proposal.model.Person;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface PersonMapper {

    int insert(Person record);

    int update(Person update);

    Person findOne(int id);

    Person findByPapersCode(Person record);
}
