package isetb.tp6.projetmobile.utils;

public class Apis {
        String URL = "http://192.168.202.9:8080/";

    public Service getService(){
        return Retrofit.getRetrofit(URL).create(Service.class);
    }

}
