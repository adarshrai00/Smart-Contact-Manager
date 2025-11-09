package com.scm.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.UUID;


@Service
public class ImageServic {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile image,String filename ) {



        try {
            byte [] data= new byte[image.getInputStream().available()] ;
            image.getInputStream().read(data);

            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id",filename
            ));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
  return this.getUrlFromPublicId(filename);
    }

  public String getUrlFromPublicId(String publicId) {
           return
  cloudinary
          .url()
          .transformation(new Transformation<>().width(500).height(500).crop("fill"))
          .generate(publicId);

  }

}
