package lk.w3Academy.asset.subject.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SitedTime {
    FIRST("First Time"),
    SECOND("Second"),
    THIRD("Third");
    private final String sitedTime;
}
