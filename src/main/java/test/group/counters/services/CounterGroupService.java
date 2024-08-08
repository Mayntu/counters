package test.group.counters.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.CustomExceptions.InvalidCounterGroupException;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.entities.CounterGroupModel;
import test.group.counters.repositories.CounterGroupRepository;

import java.util.List;

@Service
public class CounterGroupService
{
    private final CounterGroupRepository counterGroupRepository;

    public CounterGroupService(CounterGroupRepository counterGroupRepository) {
        this.counterGroupRepository = counterGroupRepository;
    }


    public CounterGroupModel get(Long id) throws CounterGroupNotFoundException {
        return counterGroupRepository.findById(id).orElseThrow(CounterGroupNotFoundException::new);
    }

    public List<CountersGroupCountersCountDTO> getCounters() throws ServerErrorException {
        try {
            return counterGroupRepository.getCounterGroupCountersCount();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }

    public void insert(CounterGroupModel counterGroupModel)
    {
        try {
            counterGroupRepository.save(counterGroupModel);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidCounterGroupException();
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }
}