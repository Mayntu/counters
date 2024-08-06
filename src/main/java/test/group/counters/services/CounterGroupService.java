package test.group.counters.services;

import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterGroupException;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.models.CounterGroupModel;
import test.group.counters.repositories.CounterGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CounterGroupService
{
    @Autowired
    private CounterGroupRepository counterGroupRepository;


    public CounterGroupModel get(Long id) throws CounterGroupNotFoundException {
        return counterGroupRepository.findById(id).orElseThrow(CounterGroupNotFoundException::new);
    }

    public List<CountersGroupCountersCountDTO> getCounters() throws ServerErrorException {
        try {
            return counterGroupRepository.getCounterGroupCountersCount();
        } catch (Exception e) {
            throw new ServerErrorException("not working query", e);
        }
    }

    public void insert(CounterGroupModel counterGroupModel)
    {
        try {
            counterGroupRepository.save(counterGroupModel);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidCounterGroupException();
        } catch (Exception e) {
            throw new ServerErrorException("server error exception", e);
        }
    }
}