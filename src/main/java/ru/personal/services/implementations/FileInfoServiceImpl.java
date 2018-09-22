package ru.personal.services.implementations;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.personal.services.interfaces.FileInfoService;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileInfoServiceImpl implements FileInfoService {

    @Value("${storage.path}")
    private String path;

    @Override
    public void getPicture(String fileName, HttpServletResponse response) {
        try {
            File file = new File(path + fileName + ".jpeg");
            if (file.exists()) {
                InputStream is;
                is = new FileInputStream(file);
                response.setContentType("image/jpeg");
                org.apache.tomcat.util.http.fileupload.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImageBase64(String fileName){
        String encodedString = null;
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File(path + fileName + ".jpeg"));
            encodedString = Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodedString;
    }

    @Override
    public String savePicture(String imageBytes) {
        byte[] imageByte;
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            imageByte = base64Decoder.decodeBuffer(imageBytes);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte);
            BufferedImage image = ImageIO.read(byteArrayInputStream);
            String newFileName = UUID.randomUUID().toString();
            File file = new File(path + newFileName + ".jpeg");
            file.mkdir();
            ImageIO.write(image, "jpeg", file);
            byteArrayInputStream.close();
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
