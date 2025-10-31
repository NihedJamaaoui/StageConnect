package isetb.tp6.projetmobile.utils;

import java.util.List;

import isetb.tp6.projetmobile.model.EmailAndPasswordRequest;
import isetb.tp6.projetmobile.model.LoginResponse;
import isetb.tp6.projetmobile.model.Offre;
import isetb.tp6.projetmobile.model.RegisterRequest;
import isetb.tp6.projetmobile.model.Utilisateur;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Service {
    @GET("offre/getAllOffres")
    Call<List<Offre>> getAllOffers();

    @POST("utilisateur/Login")
    Call<LoginResponse> loginUser(@Body EmailAndPasswordRequest emailAndPasswordRequest);

    @Multipart
    @POST("utilisateur/RegisterE")
    Call<Utilisateur> registerEntre(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("nom") RequestBody nom,
            @Part("adresse") RequestBody adresse,
            @Part("telephone") RequestBody telephone,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("utilisateur/RegisterS")
    Call<Utilisateur> registerStag(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("nom") RequestBody nom,
            @Part("prenom") RequestBody prenom,
            @Part("date_naissance") RequestBody date_naissance,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part cv
    );
    @PUT("offre/updateOffre/{id}")
    Call<Offre> updateOffre(@Path("id") Long id, @Body Offre updatedOffre);

    @DELETE("offre/deleteOffre/{id}")
    Call<Void> deleteOffre(@Path("id") Long id);

    @POST("offre/createOffre/{userId0000000000000000000}")
    Call<Offre> createOffre(@Path("userId") String userId, @Body Offre newOffre);

}