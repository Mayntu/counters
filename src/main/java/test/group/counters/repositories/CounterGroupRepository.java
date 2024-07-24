package test.group.counters.repositories;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.models.CounterGroupModel;

import java.util.List;

public interface CounterGroupRepository extends CrudRepository<CounterGroupModel, Long>
{
    @Query("SELECT new CountersGroupCountersCountDTO(cgm.name, COUNT(cem.name)) FROM CounterGroupModel cgm JOIN CounterModel cem ON cgm.name = cem.groupName GROUP BY cgm.name")
    List<CountersGroupCountersCountDTO> getCounterGroupCountersCount();
}
