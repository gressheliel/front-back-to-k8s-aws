package com.elhg.order.service;

import com.elhg.order.entity.Sequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class SequenceGenerator {

    private final MongoOperations mongoOperations;

    public int generateNextOrderId(){
        Sequence counter = mongoOperations.findAndModify(
                query(where("_id").is("sequence")),
                new Update().inc("sequence", 1),
                options().returnNew(true).upsert(true),
                Sequence.class);
        return counter.getSequence();

    }

}

