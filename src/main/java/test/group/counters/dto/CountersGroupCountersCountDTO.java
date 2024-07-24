package test.group.counters.dto; import jakarta.persistence.*;
@Entity
public class CountersGroupCountersCountDTO
{
    @Id
    private String groupName;
    private Long countersCount;
    public CountersGroupCountersCountDTO () {}
    public CountersGroupCountersCountDTO (String groupName, Long countersCount) { this.groupName = groupName; this.countersCount = countersCount; }

    public String getGroupName()
    {
        return this.groupName;
    }

    public Long getCountersCount() { return this.countersCount; }
}