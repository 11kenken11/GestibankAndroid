package com.sip.gestibank.Remote;

import com.sip.gestibank.Remote.ExchangeService;
import com.sip.gestibank.Remote.RetrofitClient;
import com.sip.gestibank.Remote.UserService;

public class APIUtils {

    private APIUtils(){
    };

    public static final String CURRENCY_API_URL = "https://api.exchangeratesapi.io/";

    public static final String GESTIBANK_API_URL = "http://192.168.1.7:85";

    /*private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(GESTIBANK_API_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }*/

    public static ExchangeService getExchangeService(){
        return RetrofitClient.getClient(CURRENCY_API_URL).create(ExchangeService.class);
    }

    public static final UserService getUserService(){
        return RetrofitClient.getClient(GESTIBANK_API_URL).create(UserService.class);
    }


}