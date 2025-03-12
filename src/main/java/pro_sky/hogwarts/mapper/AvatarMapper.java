package pro_sky.hogwarts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pro_sky.hogwarts.dto.AvatarDto;
import pro_sky.hogwarts.entity.Avatar;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AvatarMapper {

    AvatarDto toAvatarDto(Avatar avatar);

    Avatar fromAvatarDto(AvatarDto avatarDto);
}
