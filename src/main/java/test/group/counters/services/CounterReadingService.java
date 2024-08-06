package test.group.counters.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
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
        try {
            Iterable<CounterReadingModel> counterReadingModels = counterReadingRepository.findAll();
            counterReadingModels.forEach(counterReadingModelList::add);
            return counterReadingModelList;
        }
        catch (Exception e) {
            throw new ServerErrorException("not working server", e);
        }
    }

    public CounterReadingModel get(Long id)
    {
        return counterReadingRepository.findById(id).orElseThrow(CounterReadingNotFoundException::new);
    }

    public void insert(CounterReadingModel counterReadingModel)
    {
        counterReadingRepository.save(counterReadingModel);
    }
}
