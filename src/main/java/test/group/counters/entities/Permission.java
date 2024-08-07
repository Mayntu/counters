package test.group.counters.entities;

public enum Permission
{
    POST_COUNTER(
            "post_counter"
    ),
    GET_COUNTER(
            "get_counter"
    ),
    POST_COUNTER_GROUP(
            "post_counter_group"
    ),
    GET_COUNTER_GROUP(
            "get_counter_group"
    ),
    GET_READING(
            "get_reading"
    ),
    POST_READING(
            "post_reading"
    );

    private final String permission;


    Permission(String permission)
    {
        this.permission = permission;
    }


    public String getPermission()
    {
        return permission;
    }
}
