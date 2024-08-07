package test.group.counters.dto;

public class AuthRequest
{
    private String username;
    private String password;
    private Boolean isOperator;

    public AuthRequest() {}

    public AuthRequest(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.isOperator = null;
    }

    public AuthRequest(String username, String password, boolean isOperator)
    {
        this.username = username;
        this.password = password;
        this.isOperator = isOperator;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setIsOperator(boolean isOperator)
    {
        this.isOperator = isOperator;
    }

    public Boolean getIsOperator()
    {
        return isOperator;
    }
}
