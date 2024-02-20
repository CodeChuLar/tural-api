package az.code.agency.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    String price;
    String dateRange;
    String additionalInfo;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
}
