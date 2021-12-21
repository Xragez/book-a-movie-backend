package com.bookamovie.be.mapper;

import com.bookamovie.be.entity.Role;
import com.bookamovie.be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    default org.springframework.security.core.userdetails.User userDetails(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorityCollection(user.getRoles()));
    }

    default Collection<? extends GrantedAuthority> authorityCollection(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
