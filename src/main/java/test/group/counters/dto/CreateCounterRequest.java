package test.group.counters.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCounterRequest {
    @NotBlank(message = "name is not presented")
    private String name;
    @NotBlank(message = "group name is not presented")
    private String groupName;


    public CreateCounterRequest()
    {

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
