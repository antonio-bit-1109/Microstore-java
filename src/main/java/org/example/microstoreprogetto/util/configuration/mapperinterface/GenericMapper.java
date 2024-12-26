package org.example.microstoreprogetto.util.configuration.mapperinterface;

import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;

public interface GenericMapper<D extends BaseDTO, E extends BaseEntity> {

    // passo un entity e una dto -- i valori presenti nell entity vengono trasferiti nella DTO passata come secondo parametro
    D toDTO(E entity, D dtoClass) throws RuntimeException;
}
