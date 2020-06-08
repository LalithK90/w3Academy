package lk.w3Campus.asset.subject.entity.Enum;

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
