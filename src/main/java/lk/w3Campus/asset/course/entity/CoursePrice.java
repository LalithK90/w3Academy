package lk.w3Campus.asset.course.entity;

import lk.w3Campus.asset.commonAsset.model.Enum.PriceStatus;
import lk.w3Campus.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.Year;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursePrice extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @DateTimeFormat(pattern = "yyyy")
    private Year year;

    @Enumerated( EnumType.STRING )
    private PriceStatus priceStatus;
}
