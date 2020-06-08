package lk.w3Campus.asset.commonAsset.model.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PresentOrNot {
    PRESENT("Present"),
    ABSENT("Absent");

    private final String presentOrNot;
}
