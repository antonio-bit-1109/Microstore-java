package org.example.microstoreprogetto.util.configuration.mapperutils;

import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;
import org.example.microstoreprogetto.util.base_entity.BaseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface GenericMapper<D extends BaseDTO, E extends BaseEntity, C extends BasedDTO_GET> {

    // passo un entity e una dto -- i valori presenti nell entity vengono trasferiti nella DTO passata come secondo parametro
    D toDTO(E entity, D dtoClass) throws RuntimeException;

    // questo metodo deve accettare un secondo parametro che sarà il tipo che ritornerà il metodo.
    List<? extends BaseEntity> MapperToListType(ArrayList<ProductInfoDTO> listaProdotti, Class<? extends BaseEntity> typeEntityReturn)
            throws RuntimeException,
            InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    C GeneralMethodToCastEntityToGetDTO(E entity);
}
