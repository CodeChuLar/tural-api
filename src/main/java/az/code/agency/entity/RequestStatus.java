package az.code.agency.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String status; //archive, pending, answered

    LocalDateTime statusTime;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent; //agent get elese statusuna esasen get elyer, UI da archive gonderme buttonu qoyaram basanda statusu archive olar duser basqa yere, o yerdede archivden cixarsa pending olar yene


    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @OneToOne(mappedBy = "requestStatus")
    Offer offer; //offerde answered olna biler yada pending
}
