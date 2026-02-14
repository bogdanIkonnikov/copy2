package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tbank.copy2.service.model.UserModel;
import tbank.copy2.service.repository.UserModelRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserModelRepository repository;

    public UserDetails getByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UserDetails createUser(UserModel model) {
        return repository.save(model);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}