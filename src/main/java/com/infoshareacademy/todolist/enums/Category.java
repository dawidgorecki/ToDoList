package com.infoshareacademy.todolist.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {
    WORK("Praca"),
    HOME("Dom");

    private final String description;
}
