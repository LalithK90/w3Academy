package w3Campus.asset.course.entity;

import w3Campus.asset.commonAsset.model.Enum.PriceStatus;
import w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursePrice extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PriceStatus priceStatus;

    @ManyToOne
    private Course course;
}
