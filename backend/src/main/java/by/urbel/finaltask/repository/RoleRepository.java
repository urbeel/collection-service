package by.urbel.finaltask.repository;

import by.urbel.finaltask.domain.user.ERole;
import by.urbel.finaltask.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);
}
