package lk.w3Campus.asset.employee.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
    //Administrative Status
    GENEXHR("General Executive Human Resource"),
    //Academic Status
    ACE("Assistant Commissioner Of Excise"),
    //Not mentioned
    NTM("Not mentioned")    ;

    private final String designation;
}
