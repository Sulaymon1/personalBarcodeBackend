package ru.personal.services.interfaces;

import javax.servlet.http.HttpServletResponse;


public interface FileInfoService {
    void getPicture(String fileName, HttpServletResponse response);

    String getImageBase64(String fileName);

    String savePicture(String file);
}
