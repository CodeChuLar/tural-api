package az.code.agency.repository;

import az.code.agency.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> {

}
