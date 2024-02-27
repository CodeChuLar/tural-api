package az.code.agency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    Client client;
    String price;
    String dateRange;
    String additionalInfo;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private Session session;
}
