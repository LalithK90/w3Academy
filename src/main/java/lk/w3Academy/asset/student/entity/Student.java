package lk.w3Academy.asset.student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.w3Academy.asset.commonAsset.model.Enum.Gender;
import lk.w3Academy.asset.commonAsset.model.Enum.Title;
import lk.w3Academy.asset.commonAsset.model.FileInfo;
import lk.w3Academy.asset.employee.entity.Enum.Designation;
import lk.w3Academy.asset.message.entity.EmailMessage;
import lk.w3Academy.asset.student.entity.Enum.StudentStatus;
import lk.w3Academy.asset.subject.entity.Subject;
import lk.w3Academy.asset.userManagement.entity.User;
import lk.w3Academy.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "students")
    private List<EmailMessage> emailMessages;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "student_qualification",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "qualification_id"))
    private List<Qualification> qualifications;

    @Transient
    private List<MultipartFile> files = new ArrayList<>();

    @Transient
    private List<String> removeImages = new ArrayList<>();

    @Transient
    private List<FileInfo> fileInfos = new ArrayList<>();


}
