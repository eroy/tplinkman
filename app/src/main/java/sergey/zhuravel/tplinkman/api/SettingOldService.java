package sergey.zhuravel.tplinkman.api;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;

public interface SettingOldService {

    @GET(ApiConstant.LOGOUT)
    Observable<Response<ResponseBody>> setLogout(@Header("Authorization") String cookie,
                                                 @Header("Referer") String referer);

    @GET(ApiConstant.REBOOT)
    Observable<Response<ResponseBody>> setReboot(@Header("Authorization") String cookie,
                                                 @Header("Referer") String referer);

    @GET(ApiConstant.WIFI_MODE_SETTING)
    Observable<Response<ResponseBody>> setWifiMode(@Header("Authorization") String cookie,
                                                   @Header("Referer") String referer,
                                                   @Query("ap") String ap);
}
