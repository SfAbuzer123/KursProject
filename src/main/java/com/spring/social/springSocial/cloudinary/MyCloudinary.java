package com.spring.social.springSocial.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class MyCloudinary {
    public static Cloudinary cloudinary;
    static{
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxsv5iq47",
                "api_key", "976427658557723",
                "api_secret", "ESu8iEkDYSkRiZwhMp7Ph5Rh05U",
                "secure", true));
    }
}
