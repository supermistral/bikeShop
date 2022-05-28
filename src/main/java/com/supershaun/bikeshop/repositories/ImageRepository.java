package com.supershaun.bikeshop.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

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

    URL resourceUrl = getClass().getResource("/");

    public String save(byte[] image, String name) throws Exception {
        String[] splittedName = name.split("\\.");
        String newName = String.join("", Arrays.copyOfRange(
                splittedName,
                0,
                splittedName.length - 1
        )) + Calendar.getInstance().getTimeInMillis() + "." + splittedName[splittedName.length - 1];

        String resourcePath = Paths.get(resourceUrl.toURI()).toString();
        String pathInsideResources = Paths.get(imagePath, newName).toString();
        Path newFile = Paths.get(resourcePath, pathInsideResources);

        Files.createDirectories(newFile.getParent());
        Files.write(newFile, image);

        return pathInsideResources.replace("\\", "/");
    }

    public byte[] findByName(String name) throws Exception {
        Resource imageFile = new ClassPathResource(Paths.get(imagePath, name).toString());
        return StreamUtils.copyToByteArray(imageFile.getInputStream());
    }

    public boolean deleteByName(String name) throws Exception {
        return Files.deleteIfExists(Paths.get(imagePath, name));
    }
}
