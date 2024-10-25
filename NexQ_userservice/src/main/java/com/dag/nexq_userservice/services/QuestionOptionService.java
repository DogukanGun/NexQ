package com.dag.nexq_userservice.services;

import com.dag.nexq_userservice.data.entity.QuestionOption;
import com.dag.nexq_userservice.repository.QuestionOptionRepository;
import com.dag.nexq_userservice.services.interfaces.ICrudService;
import org.springframework.stereotype.Service;

@Service
public class QuestionOptionService extends ICrudService<QuestionOptionRepository, QuestionOption,Integer> {
    public QuestionOptionService(QuestionOptionRepository repository) {
        super(repository);
    }
}
