package test.group.counters.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.group.counters.CustomExceptions.CounterReadingNotFoundException;
import test.group.counters.models.CounterReadingModel;
import test.group.counters.repositories.CounterReadingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CounterReadingService
{
    @Autowired
    private CounterReadingRepository counterReadingRepository;

    public List<CounterReadingModel> getAll()
    {
        List<CounterReadingModel> counterReadingModelList = new ArrayList<>();
        Iterable<CounterReadingModel> counterReadingModels = counterReadingRepository.findAll();
        counterReadingModels.forEach(counterReadingModelList::add);
        return counterReadingModelList;
    }

    public CounterReadingModel get(Long id) throws CounterReadingNotFoundException
    {
        Optional<CounterReadingModel> counterReadingModelOptional = counterReadingRepository.findById(id);

        if (counterReadingModelOptional.isPresent())
        {
            return counterReadingModelOptional.get();
        }

        throw new CounterReadingNotFoundException();
    }

    public void insert(CounterReadingModel counterReadingModel)
    {
        counterReadingRepository.save(counterReadingModel);
    }
}
