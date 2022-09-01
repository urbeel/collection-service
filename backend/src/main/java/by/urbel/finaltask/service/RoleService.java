package by.urbel.finaltask.service;

import by.urbel.finaltask.domain.user.ERole;
import by.urbel.finaltask.domain.user.Role;
import by.urbel.finaltask.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role saveRoleByName(ERole name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if (optionalRole.isEmpty()) {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        }
        return optionalRole.get();
    }
}
