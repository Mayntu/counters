package test.group.counters.models;

public class CounterJoinDataModel
{
    private String groupName;
    private CounterData counterData;
    private Float sum;


    public CounterJoinDataModel ()
    {

    }

    public CounterJoinDataModel (String groupName, CounterData counterData, Float sum)
    {
        this.groupName = groupName;
        this.counterData = counterData;
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

    public void setCounterData(CounterData counterData)
    {
        this.counterData = counterData;
    }

    public CounterData getCounterData()
    {
        return this.counterData;
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
