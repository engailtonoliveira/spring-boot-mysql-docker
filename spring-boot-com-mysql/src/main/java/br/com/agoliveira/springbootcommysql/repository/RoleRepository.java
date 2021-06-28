package br.com.agoliveira.springbootcommysql.repository;

import br.com.agoliveira.springbootcommysql.model.Role;
import br.com.agoliveira.springbootcommysql.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(final RoleName roleName);
}
