package com.inventorysystem.common.utilities;


import com.inventorysystem.common.exceptions.BusinessException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    private static final ModelMapper modelMapper = new ModelMapper();

    private ModelConverter() {
    }

    /**
     * convertToMap.
     *
     * @param obj obj
     * @return Map
     */
    public static Map<String, Object> convertToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (Exception e) {
                throw new BusinessException(e.getLocalizedMessage());
            }
        }
        return map;
    }

    public static  <D, E> E dtoToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public static  <D, E> D entityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * listDtoToEntity.
     *
     * @param dtos List of DTOs
     * @param entityClass Class of the entity
     * @return List of entities
     */
    public static  <D, E> List<E> listDtoToEntity(List<D> dtos, Class<E> entityClass) {
        return dtos.stream()
                .map(dto -> dtoToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    /**
     * listEntityToDto.
     *
     * @param entities List of entitites
     * @param dtoClass Class of the entity
     * @return List of entities
     */
    public static <D, E> List<D> listEntityToDto(List<E> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> entityToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public static  <D, E> Page<D> pageEntityToDto(Page<E> entities, Class<D> dtoClass) {
        return entities.map(entity -> entityToDto(entity, dtoClass));
    }
}
