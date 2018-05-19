package com.inschos.proposal.service;

import com.inschos.proposal.model.Person;

/**
 * Created by IceAnt on 2018/4/3.
 */
public interface PersonService {

    int join(Person person);

    Person findByPapersCode(Person record);

}
