package com.aq.blogapp.constants;


public class AppConstants {

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "50";
    public static final String SORT_BY = "blogId";
    public static final String SORT_DIR = "asc";

    //    JWT constants
    public static final Long JWT_TOKEN_VALIDITY = (long) (5 * 60 * 60);


    //    Role constants
    public static final Integer ROLE_ADMIN_CODE = 502;
    public static final Integer ROLE_NORMAL_CODE = 501;

    public static final String ROLE_ADMIN_USER = "ADMIN_USER";
    public static final String ROLE_NORMAL_USER = "NORMAL_USER";

}
