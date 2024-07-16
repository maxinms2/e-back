package com.icodeap.ecommerce.backend.infrastructure.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.icodeap.ecommerce.backend.domain.model.ModeloCategory;
import com.icodeap.ecommerce.backend.infrastructure.entity.ModeloCategoryEntity;

@Mapper(componentModel = "spring")
public interface ModeloCategoryMapper {
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "dateCreated", target = "dateCreated"),
                    @Mapping(source = "name", target = "name"),
                    @Mapping(source = "description", target = "description"),
                    @Mapping(source = "dateUpdated", target = "dateUpdated"),
            }
    )

    ModeloCategory toModeloCategory(ModeloCategoryEntity modeloCategoryEntity);
    Iterable<ModeloCategory> toModeloCategoryList(Iterable<ModeloCategoryEntity> modeloCategoryEntities);

    @InheritInverseConfiguration
    ModeloCategoryEntity toOModeloCategoryEntity(ModeloCategory modeloCategory);
}
