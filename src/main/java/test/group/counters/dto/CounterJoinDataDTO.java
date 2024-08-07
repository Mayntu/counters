package test.group.counters.dto;

public class CounterJoinDataDTO
{
    private String groupName;
    private CounterDataDTO counterDataDTO;
    private Float sum;


    public CounterJoinDataDTO ()
    {

    }

    public CounterJoinDataDTO (String groupName, CounterDataDTO counterData, Float sum)
    {
        this.groupName = groupName;
        this.counterDataDTO = counterData;
        this.sum = sum;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public void setCounterData(CounterDataDTO counterData)
    {
        this.counterDataDTO = counterData;
    }

    public CounterDataDTO getCounterData()
    {
        return this.counterDataDTO;
    }

    public void setSum(Float sum)
    {
        this.sum = sum;
    }

    public Float getSum()
    {
        return this.sum;
    }
}
