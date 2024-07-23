package test.group.counters.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.group.counters.CustomExceptions.CounterGroupNotFoundException;
import test.group.counters.models.CounterModel;
import test.group.counters.repositories.CounterRepository;

import java.util.Optional;
@Service
public class CounterService
{
    @Autowired
    private CounterRepository counterRepository;


    public CounterModel get(Long id) throws CounterGroupNotFoundException {
        Optional<CounterModel> counterModelOptional = counterRepository.findById(id);

        if(counterModelOptional.isPresent())
        {
            return counterModelOptional.get();
        }

        throw new CounterGroupNotFoundException();
    }

    public void insert(CounterModel counterModel)
    {
        counterRepository.save(counterModel);
    }
}
