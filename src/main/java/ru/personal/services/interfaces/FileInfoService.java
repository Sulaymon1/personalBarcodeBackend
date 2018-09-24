package ru.personal.services.interfaces;

import ru.personal.constants.Image;
import ru.personal.models.User;

import javax.servlet.http.HttpServletResponse;


public interface FileInfoService {


    String getImageBase64(String fileName, Image image);


    String savePicture(String imageBytes, Image imageType);

    void getPhoto(String fileName, String photoType, HttpServletResponse response);

    void removePhoto(User user, String imageType);
}
