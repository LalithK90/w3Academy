package lk.w3Campus.asset.userManagement.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UType {
    STAFF("Staff"),
    NONSPECIFIC(" Not Specified"),
    STUDENT("Student");

    private final String uType;
}
