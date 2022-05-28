package com.supershaun.bikeshop.services.interfaces;


public interface IImageService {
    String save(byte[] image, String name) throws Exception;
    byte[] findByName(String name) throws Exception;
    String saveToItemInstance(byte[] image, String name, Long id) throws Exception;
}
