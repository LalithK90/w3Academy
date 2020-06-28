package w3Campus.asset.branch.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BranchStatus {
    AC("Active"),
    TCL("Temporally closed"),
    CL("Closed");
    private final String branchStatus;
}
