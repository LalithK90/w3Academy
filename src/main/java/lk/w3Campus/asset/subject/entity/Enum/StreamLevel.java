package lk.w3Campus.asset.subject.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StreamLevel {
    OL("Ordinary Level"),
    AL("Advance Level");

    private final String streamLevel;
}