package com.example.tsfdata;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constants {
    public static final String BASE_URL = "http://139.59.65.145:9090";
    public static final String LOGIN_URL = "/user/login";
    public static final String SIGNUP_URL = "/user/signup";
    public static final String PERSONAL_URL = "/user/personaldetail/";
    public static final String IMG_URL = "/user/personaldetail/pp/post";
    public static final String PROFILEPIC_URL = "/user/personaldetail/profilepic/";
    public static final String EDU_URL = "/user/educationdetail/";
    public static final String PROFESSIONAL_URL = "/user/professionaldetail/";
public static final String CERTIFICATE_URL="/user/educationdetail/certificate/";
}