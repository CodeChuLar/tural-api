package az.code.agency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    LocalDateTime creationTime;
    LocalDateTime deadline;
    boolean expired;

    @ManyToOne
    User user;
    @OneToMany(mappedBy = "request")
    List<Offer> offers;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    Archive archive;

}
