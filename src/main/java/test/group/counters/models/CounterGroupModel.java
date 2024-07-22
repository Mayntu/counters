package test.group.counters.models;

import jakarta.persistence.*;

@Entity
@Table(name = "counter_group_model")
public class CounterGroupModel
{
    @Id
    private Long id;

    private String name;

    public CounterGroupModel (String name)
    {
        this.name = name;
    }

    public CounterGroupModel (Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
