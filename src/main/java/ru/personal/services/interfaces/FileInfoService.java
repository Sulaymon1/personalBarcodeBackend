package ru.personal.services.interfaces;

import ru.personal.constants.Image;

import javax.servlet.http.HttpServletResponse;


public interface FileInfoService {


    String getImageBase64(String fileName, Image image);


    String savePicture(String imageBytes, Image imageType);

    void getProfilePhoto(String fileName, HttpServletResponse response);
}
