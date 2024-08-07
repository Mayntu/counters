package test.group.counters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import test.group.counters.entities.CounterReadingModel;

public interface CounterReadingRepository extends JpaRepository<CounterReadingModel, Long>
{

}
