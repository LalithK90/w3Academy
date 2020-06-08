package lk.w3Academy.asset.student.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubResult {
    AP("A +"),
    A("A"),
    AM("A -"),
    BP("B +"),
    B("B"),
    BM("B -"),
    CP("C +"),
    C("C"),
    CM("C -"),
    DP("D +"),
    D("D"),
    DM("D -"),
    EF("E or F");

    private final String subResult;
}
