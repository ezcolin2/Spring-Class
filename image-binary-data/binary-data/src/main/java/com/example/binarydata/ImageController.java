package com.example.binarydata;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    //이미지 등록
    @PostMapping("/api/images")
    public String postImage(@RequestPart("image") MultipartFile image, Model model) throws Exception {
        String imageBinary = convertBinary(image);
        System.out.println("imageBinary = " + imageBinary);
        Image imageEntity = new Image();
        imageEntity.setImageBinaryData(imageBinary);
        imageRepository.save(imageEntity);
        model.addAttribute("imageBinary", "data:image/gif;base64,"+imageBinary);
        return "image";


    }
    //데이터베이스의 이미지 조회
    @GetMapping("/api/images/{id}")
    public String getImage(@PathVariable Long id, Model model) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            model.addAttribute("imageBinary", "data:image/gif;base64,"+image.get().getImageBinaryData());
        }
        return "image";
    }

    //바이너리 변환
    public String convertBinary(MultipartFile files) throws Exception{

        String fileName = StringUtils.cleanPath(files.getOriginalFilename()) ;
        BufferedImage image = ImageIO.read(files.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, fileName.substring(fileName.lastIndexOf(".") + 1), baos);
        return new String(Base64.encodeBase64(baos.toByteArray(), true));
    }
}
