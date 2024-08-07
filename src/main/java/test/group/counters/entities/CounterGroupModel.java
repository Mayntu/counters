package test.group.counters.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "counter_group_model")
public class CounterGroupModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counter_group_id_seq")
    @SequenceGenerator(sequenceName = "counter_group_id_seq", name = "counter_group_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    public CounterGroupModel ()
    {

    }

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
