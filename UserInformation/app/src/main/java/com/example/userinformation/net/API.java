package com.example.userinformation.net;

/**
 * Created by Sagar on 17-02-2017.
 */
import com.example.userinformation.net.apis.UserAPI;
import com.example.userinformation.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A singleton that creates the Retrofit API
 * <p>
 * Created on 1/5/17.
 *
 * @author Sagar Lakhia
 */
public enum API
{
    INSTANCE;

    private static final int MILLISECONDS_PER_SECOND = 1000;

    public static UserAPI userAPI()
    {
        return INSTANCE.userAPI;
    }

    private final UserAPI userAPI;

    API()
    {
        OkHttpClient client;

        // Add detailed logging for requests
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>()
        {
            @Override
            public void write(JsonWriter out, Boolean value) throws IOException
            {
                if (value == null)
                {
                    out.nullValue();
                }
                else
                {
                    out.value(value ? 1 : 0);
                }
            }

            @Override
            public Boolean read(JsonReader in) throws IOException
            {
                JsonToken peek = in.peek();
                switch (peek)
                {
                    case BOOLEAN:
                        return in.nextBoolean();
                    case NULL:
                        in.nextNull();
                        return null;
                    case NUMBER:
                        return in.nextInt() == 1;
                    case STRING:
                        return Boolean.parseBoolean(in.nextString());
                    default:
                        throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
                }
            }
        };

        // Set up Retrofit
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userAPI = retrofit.create(UserAPI.class);
    }

    public static class GsonUTCDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date>
    {
        public GsonUTCDateAdapter()
        {
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(date.getTime() / MILLISECONDS_PER_SECOND);
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
        {
            long epoch = jsonElement.getAsLong();

            if (epoch == 0)
            {
                return null;
            }

            return new Date(epoch * MILLISECONDS_PER_SECOND);
        }
    }
}