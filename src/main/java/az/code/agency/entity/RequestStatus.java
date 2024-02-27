package az.code.agency.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String status;

    LocalDateTime statusTime;

    @JoinColumn(name = "agent_id")
    @ManyToOne
    private Agent agent;

    @JoinColumn(name = "request_id")
    @ManyToOne
    private Request request;

    @OneToOne(mappedBy = "requestStatus")
    Offer offer;
}
