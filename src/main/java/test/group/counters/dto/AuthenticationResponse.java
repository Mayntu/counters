package test.group.counters.dto;

public class AuthenticationResponse
{
    private String token;

    public AuthenticationResponse()
    {

    }

    public AuthenticationResponse(String token)
    {
        this.token = token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }
}
