package com.supershaun.bikeshop.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Repository
public class ImageRepository {
    @Value("${settings.image.path}")
    private String imagePath;

    @Value("${settings.mode}")
    private String mode;

    private Path resourcePath;
    private boolean isProduction;

    @PostConstruct
    public void setResourcePath() throws URISyntaxException {
        isProduction = mode.equals("production");

        if (isProduction) {
            resourcePath = Paths.get("/");
        } else {
            resourcePath = Paths.get(getClass().getResource("/").toURI());
        }
    }

    public String save(byte[] image, String name) throws Exception {
        String[] splittedName = name.split("\\.");
        String newName = String.join("", Arrays.copyOfRange(
                splittedName,
                0,
                splittedName.length - 1
        )) + Calendar.getInstance().getTimeInMillis() + "." + splittedName[splittedName.length - 1];

        String pathInsideResources = Paths.get(imagePath, newName).toString();
        Path newFile = Paths.get(resourcePath.toString(), pathInsideResources);

        Files.createDirectories(newFile.getParent());
        Files.write(newFile, image);

        return Paths.get("/media", newName).toString().replace("\\", "/");
    }

    public byte[] findByNameInResources(String name) throws Exception {
        Resource imageFile = new ClassPathResource(Paths.get(imagePath, name).toString());
        return StreamUtils.copyToByteArray(imageFile.getInputStream());
    }

    public byte[] findByNameInFileSystem(String name) throws Exception {
        return Files.readAllBytes(Paths.get(resourcePath.toFile().getAbsolutePath(), imagePath, name));
    }

    public byte[] findByName(String name) throws Exception {
        if (isProduction)
            return findByNameInFileSystem(name);

        return findByNameInResources(name);
    }

    public boolean deleteByName(String name) throws Exception {
        return Files.deleteIfExists(Paths.get(imagePath, name));
    }
}
