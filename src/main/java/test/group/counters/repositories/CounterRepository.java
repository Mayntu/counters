package test.group.counters.repositories;

import org.springframework.data.repository.CrudRepository;
import test.group.counters.models.CounterModel;

public interface CounterRepository extends CrudRepository<CounterModel, Long>
{

}
