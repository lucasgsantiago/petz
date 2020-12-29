package com.petz.apiclientes.infrastructure.mappers;

public interface Mapper {
    <T, C> T converter(C source);
}
