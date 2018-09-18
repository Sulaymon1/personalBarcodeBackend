package ru.personal.services.interfaces;

import javax.servlet.http.HttpServletResponse;


public interface FileInfoService {
    void getPicture(String fileName, HttpServletResponse response);
    String savePicture(String file);
}
