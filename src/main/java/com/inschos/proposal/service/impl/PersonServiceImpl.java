package com.inschos.proposal.service.impl;

import com.inschos.proposal.kit.TimeKit;
import com.inschos.proposal.mapper.BankMapper;
import com.inschos.proposal.mapper.PersonMapper;
import com.inschos.proposal.model.Person;
import com.inschos.proposal.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IceAnt on 2018/4/3.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private BankMapper bankMapper;

    @Override
    public int join(Person person) {

        long currentTime = TimeKit.currentTimeMillis();

        person.created_at = currentTime;
        person.updated_at = currentTime;
        person.status = 1;
        Person exists = personMapper.findByPapersCode(person);
        int result = 0;
        if(exists==null){
            result = personMapper.insert(person);
        }else{
            person.id = exists.id;
            result = 1;
        }
        return result;
    }

    @Override
    public Person findByPapersCode(Person search) {
        return search!=null?personMapper.findByPapersCode(search):null;
    }
}
