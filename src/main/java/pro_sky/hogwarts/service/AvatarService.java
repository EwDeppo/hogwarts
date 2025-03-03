package pro_sky.hogwarts.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro_sky.hogwarts.entity.Avatar;
import pro_sky.hogwarts.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Slf4j
@Transactional
public class AvatarService {

    @Value("${students.avatar.dir.path}")
    private String avatarDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long student_id, MultipartFile avatarFile) throws IOException {
        var student = studentService.findStudentById(student_id);
        log.debug("An object of class 'Student' is created by ID - {}", student_id);

        Path filePath = Path.of(avatarDir, student_id + "." + getExtension(avatarFile.getOriginalFilename()));
        log.debug("Path to the directory with downloaded files");
        Files.createDirectories(filePath.getParent());
        log.debug("Create a directory for storing data");
        Files.deleteIfExists(filePath);
        log.debug("Remove a file from the directory if it already exists");

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            log.debug("Create input - {} and output - {} stream to read and transfer data", is, os);
            bis.transferTo(bos);
            log.debug("Starting the data transfer process");
        }

        var avatar = findAvatar(student_id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
        log.info("Save avatar for student");
    }

    public Avatar findAvatar(Long studentId) {
        log.info("Was invoked method for find avatar by ID student - {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            log.debug("Create input - {} stream to read and transfer data", is);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            log.info("A method was called to reduce the file extension");
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        log.info("A method was called to find the file extension");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Collection<Avatar> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        log.info("Was invoked method for find avatars");
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
