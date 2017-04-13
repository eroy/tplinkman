package sergey.zhuravel.tplinkman.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;

public interface InfoService {

    @GET(ApiConstant.INFO_FIRMWARE)
    Observable<Response<ResponseBody>> getInfoFirmware(@Header("Cookie") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_MAC)
    Observable<Response<ResponseBody>> getInfoMac(@Header("Cookie") String cookie,
                                                  @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WIFI_NAME)
    Observable<Response<ResponseBody>> getInfoWifiName(@Header("Cookie") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WIFI_PASS)
    Observable<Response<ResponseBody>> getInfoWifiPass(@Header("Cookie") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_STATUS)
    Observable<Response<ResponseBody>> getInfoStatus(@Header("Cookie") String cookie,
                                                     @Header("Referer") String referer);


    @GET(ApiConstant.INFO_WIFI_STATION)
    Observable<Response<ResponseBody>> getInfoWifiStation(@Header("Cookie") String cookie,
                                                          @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_DYNAMIC)
    Observable<Response<ResponseBody>> getInfoWanDynamic(@Header("Cookie") String cookie,
                                                         @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_STATIC)
    Observable<Response<ResponseBody>> getInfoWanStatic(@Header("Cookie") String cookie,
                                                        @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_PPTP)
    Observable<Response<ResponseBody>> getInfoWanPptp(@Header("Cookie") String cookie,
                                                      @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_PPPOE)
    Observable<Response<ResponseBody>> getInfoWanPppoe(@Header("Cookie") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_TYPE)
    Observable<Response<ResponseBody>> getInfoWanType(@Header("Cookie") String cookie,
                                                      @Header("Referer") String referer);

}
