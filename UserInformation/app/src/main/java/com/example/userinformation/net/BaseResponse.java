package com.example.userinformation.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sagar on 17-02-2017.
 */

public class BaseResponse
{
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
}
