package com.example.userinformation.net.apis;

import com.example.userinformation.models.User;
import com.example.userinformation.net.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**API methods related to the user
 * Created by Sagar on 17-02-2017.
 */

public interface UserAPI {

    @GET("get-users")
    Call<List<User>> fetchUsers();

    @FormUrlEncoded
    @POST("submit-user-data")
    Call<User> submitUserData(
        @Field("name") String name,
        @Field("age") int age,
        @Field("email") String email,
        @Field("profession") String profession);

    @GET("delete-user/{email}")
    Call<BaseResponse> deleteUser(
        @Path("email") String email);

    @FormUrlEncoded
    @POST("update-user-data")
    Call<BaseResponse> updateUser(
        @Field("name") String name,
        @Field("age") int age,
        @Field("email") String email,
        @Field("profession") String profession);
}
