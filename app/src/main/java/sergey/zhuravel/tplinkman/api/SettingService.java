package sergey.zhuravel.tplinkman.api;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;

public interface SettingService {
    @GET(ApiConstant.LOGOUT)
    Observable<Response<ResponseBody>> setLogout(@Header("Cookie") String cookie,
                                                 @Header("Referer") String referer);

    @GET(ApiConstant.REBOOT)
    Observable<Response<ResponseBody>> setReboot(@Header("Cookie") String cookie,
                                                 @Header("Referer") String referer);
}
