package com.example.rotation.Rotation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RotationService {
    RotationRepository rotationRepository;


    //@Transactional
//   public BufferedImage setSide(int width, int height, BufferedImage image) {
//        if (width != height) {
//            if (angle == 90 || angle == 270) {
//                newImage = new BufferedImage(height, width, image.getType());
//
//            } else if (angle == 180) {
//                newImage = new BufferedImage(width, height, image.getType());
//            } else {
//                throw new RuntimeException("angle must be one with this: 90,180,270");
//            }
//        } else {
//            newImage = new BufferedImage(width, height, image.getType());
//        }
//        return newImage;
//
//    }
    @Transactional
    public RotationEntity saveImages(MultipartFile file) throws IOException {

        String format = file.getContentType().split("/")[1]; // np. „png” lub „jpeg”
        byte[] originalImageByte = file.getBytes();
        BufferedImage originalImage = transformByteToImage(originalImageByte);
        RotationEntity imageEntity = new RotationEntity();
        imageEntity.setOriginalImage(originalImageByte);
        imageEntity.setFormat(format);
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage bufferedImage = rotation(originalImage, height, width);
        imageEntity.setModifiedImage(transformImageToByte(bufferedImage));
        return rotationRepository.save(imageEntity);

    }

    @Transactional
    public BufferedImage rotation(BufferedImage image, int height, int width) {
        BufferedImage newImage = new BufferedImage(height, width, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                newImage.setRGB(y, width - 1 - x, pixel);
            }
        }
        return newImage;
    }


    @Transactional
    public byte[] transformImageToByte(BufferedImage rotatedImage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(rotatedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    @Transactional
    public BufferedImage transformByteToImage(byte[] originalImageByte) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(originalImageByte);
        return ImageIO.read(byteArrayInputStream);
    }

    @Transactional
    public RotationEntity getImage(Long id) {
       return rotationRepository.findById(id).orElseThrow(()-> new RuntimeException("isnt such image"));
    }
}


