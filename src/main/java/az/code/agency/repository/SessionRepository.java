package az.code.agency.repository;

import az.code.agency.entity.Agent;
import az.code.agency.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
}
