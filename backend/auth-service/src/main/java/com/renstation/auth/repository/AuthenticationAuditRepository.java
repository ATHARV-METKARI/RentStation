package com.renstation.auth.repository;
import com.renstation.auth.entity.AuthenticationAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AuthenticationAuditRepository extends JpaRepository<AuthenticationAudit, UUID> {
}
