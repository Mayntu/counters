package test.group.counters.repositories;

import org.springframework.data.repository.CrudRepository;
import test.group.counters.models.CounterGroupModel;

public interface CounterGroupRepository extends CrudRepository<CounterGroupModel, Long>
{
    
}
