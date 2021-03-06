package w3Campus.asset.student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import w3Campus.asset.commonAsset.model.Enum.Gender;
import w3Campus.asset.commonAsset.model.Enum.Title;
import w3Campus.asset.commonAsset.model.FileInfo;
import w3Campus.asset.message.entity.EmailMessage;
import w3Campus.asset.student.entity.Enum.StudentStatus;
import w3Campus.asset.subject.entity.SubjectStudent;
import w3Campus.asset.userManagement.entity.User;
import w3Campus.util.audit.AuditEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Student")
public class Student extends AuditEntity {

    @Column(unique = true)
    private String code;

    @Size(min = 5, message = "Your name cannot be accepted")
    private String name;

    @Size(min = 5, message = "At least 5 characters should be included calling name")
    private String callingName;

    @Size(max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 ")
    @Column(unique = true)
    private String nic;

    @Column(unique = true)
    private String bitRegNumber;

    @Column(unique = true)
    private String bitIndexNumber;

    @Size(max = 10, message = "Mobile number length should be contained 10 and 9")
    private String mobileOne;

    private String mobileTwo;

    private String land;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255)
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus;

    @OneToOne(mappedBy = "student")
    private User user;

    @ManyToMany(mappedBy = "students")
    private List<EmailMessage> emailMessages;

    @OneToMany(mappedBy = "student")
    private List<SubjectStudent> subjectStudents;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentResult> studentResults;

    @Transient
    private MultipartFile file;

    @Transient
    private List<String> removeImages = new ArrayList<>();

    @Transient
    private List<FileInfo> fileInfos = new ArrayList<>();


}
