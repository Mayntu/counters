package test.group.counters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.group.counters.dto.CountersGroupCountersCountDTO;
import test.group.counters.entities.CounterGroupModel;

import java.util.List;

public interface CounterGroupRepository extends JpaRepository<CounterGroupModel, Long>
{
    @Query("SELECT new CountersGroupCountersCountDTO(cgm.name, COUNT(cem.name)) FROM CounterGroupModel cgm JOIN CounterModel cem ON cgm.name = cem.groupName GROUP BY cgm.name")
    List<CountersGroupCountersCountDTO> getCounterGroupCountersCount();
}
