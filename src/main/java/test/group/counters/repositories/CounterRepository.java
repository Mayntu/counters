package test.group.counters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import test.group.counters.entities.CounterModel;

public interface CounterRepository extends JpaRepository<CounterModel, Long>
{

}
