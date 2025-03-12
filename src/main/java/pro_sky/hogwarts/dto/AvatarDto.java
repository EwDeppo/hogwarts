package pro_sky.hogwarts.dto;

public record AvatarDto(
        String filePath,
        long fileSize,
        String mediaType
) {
}
