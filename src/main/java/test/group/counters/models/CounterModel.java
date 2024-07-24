package test.group.counters.models;

import jakarta.persistence.*;

@Entity
@Table(name = "counter_model")
public class CounterModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counter_id_seq")
    @SequenceGenerator(sequenceName = "counter_id_seq", name = "counter_id_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String groupName;

    public CounterModel ()
    {

    }

    public CounterModel (String name, String group)
    {
        this.name = name;
        this.groupName = group;
    }

    public CounterModel (Long _id, String name, String group)
    {
        this.id = _id;
        this.name = name;
        this.groupName = group;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setGroupName(String group)
    {
        this.groupName = group;
    }

    public String getGroupName()
    {
        return groupName;
    }
}

