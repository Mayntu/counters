package test.group.counters.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "counter_reading_model")
public class CounterReadingModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "counter_reading_id_seq")
    @SequenceGenerator(name = "counter_reading_id_seq", sequenceName = "counter_reading_id_seq", allocationSize = 1)
    private Long id;
    @NotNull(message = "where is id")
    private Long counterId, groupId;
    @NotNull(message = "where is date")
    private String date;
    @NotNull(message = "where is reading")
    @PositiveOrZero(message = "current reading must be (not negative) or zero")
    private float currentReading;

    public CounterReadingModel ()
    {

    }

    public CounterReadingModel (Long counterId, Long groupId, String date, float currentReading)
    {
        this.counterId = counterId;
        this.groupId = groupId;
        this.date = date;
        this.currentReading = currentReading;
    }

    public CounterReadingModel(Long id, Long counterId, Long groupId, String date, float currentReading)
    {
        this.id = id;
        this.counterId = counterId;
        this.groupId = groupId;
        this.date = date;
        this.currentReading = currentReading;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setCounterId(Long counterId)
    {
        this.counterId = counterId;
    }

    public Long getCounterId()
    {
        return this.counterId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public Long getGroupId()
    {
        return groupId;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate()
    {
        return date;
    }

    public void setCurrentReading(float currentReading)
    {
        this.currentReading = currentReading;
    }

    public float getCurrentReading()
    {
        return currentReading;
    }
}
