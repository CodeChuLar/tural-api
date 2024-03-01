package az.code.agency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime statusTime;

    @JoinColumn(name = "agent_id")
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private Agent agent;

    @JoinColumn(name = "request_id")
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private Request request;
}
