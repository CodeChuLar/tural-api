package az.code.agency.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "agents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String voen;
    String agentName;
    String companyName;

//    @OneToMany(mappedBy = "agent")
//    private List<Offer> offers;

    @OneToOne(mappedBy = "agent", cascade = CascadeType.ALL)
    private User user;

}
