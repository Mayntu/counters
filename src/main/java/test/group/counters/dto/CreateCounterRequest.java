package test.group.counters.dto;

public class CreateCounterRequest {
    private String name;
    private String groupName;


    public CreateCounterRequest ()
    {
        this.name = null;
        this.groupName = null;
    }

    public CreateCounterRequest(String name, String groupName)
    {
        this.name = name;
        this.groupName = groupName;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return groupName;
    }
}
