package test.group.counters.repositories;

import org.springframework.data.repository.CrudRepository;
import test.group.counters.models.CounterReadingModel;

public interface CounterReadingRepository extends CrudRepository<CounterReadingModel, Long>
{

}
