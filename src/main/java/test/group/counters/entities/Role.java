package test.group.counters.entities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    OPERATOR(
          Set.of(
                  Permission.GET_COUNTER,
                  Permission.GET_COUNTER_GROUP,
                  Permission.GET_READING
          )
    ),
    ADMIN(
            Set.of(
                    Permission.POST_COUNTER,
                    Permission.GET_COUNTER,
                    Permission.POST_COUNTER_GROUP,
                    Permission.GET_COUNTER_GROUP,
                    Permission.POST_READING,
                    Permission.GET_READING
            )
    ),
    METER(
            Set.of(
                    Permission.POST_READING
            )
    );

    private final Set<Permission> permissions;


    Role(Set<Permission> permissions)
    {
        this.permissions = permissions;
    }


    public Set<Permission> getPermissions()
    {
        return permissions;
    }


    public List<SimpleGrantedAuthority> getAuthorities()
    {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
