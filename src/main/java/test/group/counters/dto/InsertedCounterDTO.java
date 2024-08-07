package test.group.counters.dto;

public class InsertedCounterDTO
{
    private final String username;
    private final String password;


    public InsertedCounterDTO(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
