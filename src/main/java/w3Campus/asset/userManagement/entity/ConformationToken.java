package w3Campus.asset.userManagement.entity;

import w3Campus.asset.userManagement.entity.Enum.ConformationTokenType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ConformationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private ConformationTokenType conformationTokenType;

    @Column(unique = true, nullable = false)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    @Transient
    private String password, newPassword;


    public ConformationToken(String email) {
        this.email = email;
        this.conformationTokenType = ConformationTokenType.NEWUSER;
        this.token = UUID.randomUUID().toString();
        this.createDate = LocalDateTime.now();
        this.endDate = createDate.plusDays(1);
    }

    public ConformationToken passwordResetToken(String email) {
        this.email = email;
        this.conformationTokenType = ConformationTokenType.NEWUSER;
        this.token = UUID.randomUUID().toString();
        this.createDate = LocalDateTime.now();
        this.endDate = createDate.plusHours(1);
        return this;
    }
}
