package com.shoppingcart.user.service.user;

import com.shoppingcart.user.dao.entity.UserEntity;
import com.shoppingcart.user.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<UserEntity> optionalUser = userRepository.findById(email);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException(email);
        }

        final UserEntity userEntity = optionalUser.get();

        final Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("GUEST"));

        return new org.springframework.security.core.userdetails.User(
                email,
                userEntity.getPassword(),
                authorities);
    }
}
