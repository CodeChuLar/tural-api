package az.code.agency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String price;
    String dateRange;
    String additionalInfo;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    @JsonIgnore
    @ToString.Exclude
    private Agent agent;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    Request request;


}
