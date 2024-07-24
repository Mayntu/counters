package test.group.counters.services;

import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
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
        Optional<CounterGroupModel> counterGroupModelOptional = counterGroupRepository.findById(id);

        if(counterGroupModelOptional.isPresent())
        {
            return counterGroupModelOptional.get();
        }

        throw new CounterGroupNotFoundException();
    }

    public List<CountersGroupCountersCountDTO> getCounters()
    {
        try
        {
            return counterGroupRepository.getCounterGroupCountersCount();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void insert(CounterGroupModel counterGroupModel)
    {
        counterGroupRepository.save(counterGroupModel);
    }
}