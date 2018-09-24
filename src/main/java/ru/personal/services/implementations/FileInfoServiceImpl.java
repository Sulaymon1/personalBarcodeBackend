package ru.personal.services.implementations;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.personal.constants.Image;
import ru.personal.models.User;
import ru.personal.services.interfaces.FileInfoService;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileInfoServiceImpl implements FileInfoService {

    @Value("${storage.photoPath}")
    private String photoPath;

    @Value("${storage.qrImagePath}")
    private String qrImagePath;

    @Value("${storage.coverPhotoPath}")
    private String coverPhotoPath;

    @Override
    public String getImageBase64(String fileName, Image image){
        String encodedString = null;
            try {
                byte[] bytes = new byte[0];
                if (image.equals(Image.Photo)) {
                    bytes = FileUtils.readFileToByteArray(new File(photoPath + fileName + ".jpeg"));
                }else if (image.equals(Image.QRimage)){
                    bytes = FileUtils.readFileToByteArray(new File(qrImagePath + fileName + ".jpeg"));
                }
                encodedString = Base64.getEncoder().encodeToString(bytes);
            }catch (Exception e){
                    e.printStackTrace();
                }
        return encodedString;
    }

    @Override
    public String savePicture(String imageBytes, Image imageType) {
        byte[] imageByte;
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            imageByte = base64Decoder.decodeBuffer(imageBytes);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageByte);
            BufferedImage image = ImageIO.read(byteArrayInputStream);
            String newFileName = UUID.randomUUID().toString();
            File file = null;
            if (imageType.equals(Image.Photo)){
                file = new File(photoPath + newFileName + ".jpeg");
            }else if (imageType.equals(Image.QRimage)){
                file = new File(qrImagePath + newFileName + ".jpeg");
            }else if (imageType.equals(Image.CoverPhoto)){
                file = new File(coverPhotoPath + newFileName + ".jpeg");
            }
            file.mkdir();
            ImageIO.write(image, "jpeg", file);
            byteArrayInputStream.close();
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getPhoto(String fileName, String photoType, HttpServletResponse response) {
        try {
            File file = null;
            if (photoType.equals("profilePhoto")){
               file = new File(photoPath + fileName + ".jpeg");
            }
            else if (photoType.equals("coverPhoto")){
                file = new File(coverPhotoPath + fileName + ".jpeg");
            }
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
    public void removePhoto(User user, String imageType){
        String path = null;
        String fileName = null;
        if (imageType.equals("coverPhoto")){
            path = coverPhotoPath;
            fileName = user.getCoverPhotoPath();
            user.setCoverPhotoPath(null);
        }else if (imageType.equals("profilePhoto")){
            path = photoPath;
            fileName = user.getProfilePhotoPath();
            user.setProfilePhotoPath(null);
        }
        try {
            Files.deleteIfExists(Paths.get(path + fileName + ".jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
