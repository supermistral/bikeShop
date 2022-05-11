package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.repositories.ImageRepository;
import com.supershaun.bikeshop.services.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService implements IImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public String save(byte[] image, String name) throws Exception {
        return imageRepository.save(image, name);
    }

    @Override
    public byte[] findByName(String name) throws Exception {
        return imageRepository.findByName(name);
    }
}
