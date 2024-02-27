package az.code.agency.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "clients")
public class Client {
    @Id
    long clientId;
    long chatId;
    String fullName;
    String phoneNumber;

    @OneToMany(mappedBy = "client")
    private List<Session> sessionList;
}
