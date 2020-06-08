package lk.w3Academy.asset.student.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Attempt {
    FIRST("First Attempt"),
    SECOND("Second Attempt"),
    THIRD("Third Attempt");

    private final String attempt;
}
