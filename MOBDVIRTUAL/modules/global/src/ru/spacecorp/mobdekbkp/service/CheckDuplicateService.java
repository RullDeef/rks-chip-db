package ru.spacecorp.mobdekbkp.service;


import ru.spacecorp.mobdekbkp.entity.Typonominal;

public interface CheckDuplicateService {
    String NAME = "mobdekbkp_CheckDuplicateService";

    Boolean duplicateTyponominal(Typonominal typonominal);
}