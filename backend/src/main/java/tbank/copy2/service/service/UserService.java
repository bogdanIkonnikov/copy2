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

    public UserDetails getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public UserDetails createUser(UserModel model) {
        return repository.save(model);
    }

    public UserModel getUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }
}