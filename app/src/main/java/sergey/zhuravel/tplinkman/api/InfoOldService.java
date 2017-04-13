package sergey.zhuravel.tplinkman.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;


public interface InfoOldService {

    @GET(ApiConstant.INFO_FIRMWARE)
    Observable<Response<ResponseBody>> getInfoFirmware(@Header("Authorization") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_MAC)
    Observable<Response<ResponseBody>> getInfoMac(@Header("Authorization") String cookie,
                                                  @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WIFI_NAME)
    Observable<Response<ResponseBody>> getInfoWifiName(@Header("Authorization") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WIFI_PASS)
    Observable<Response<ResponseBody>> getInfoWifiPass(@Header("Authorization") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_STATUS)
    Observable<Response<ResponseBody>> getInfoStatus(@Header("Authorization") String cookie,
                                                     @Header("Referer") String referer);


    @GET(ApiConstant.INFO_WIFI_STATION)
    Observable<Response<ResponseBody>> getInfoWifiStation(@Header("Authorization") String cookie,
                                                          @Header("Referer") String referer);


    @GET(ApiConstant.INFO_WAN_DYNAMIC)
    Observable<Response<ResponseBody>> getInfoWanDynamic(@Header("Authorization") String cookie,
                                                         @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_STATIC)
    Observable<Response<ResponseBody>> getInfoWanStatic(@Header("Authorization") String cookie,
                                                        @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_PPTP)
    Observable<Response<ResponseBody>> getInfoWanPptp(@Header("Authorization") String cookie,
                                                      @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_PPPOE)
    Observable<Response<ResponseBody>> getInfoWanPppoe(@Header("Authorization") String cookie,
                                                       @Header("Referer") String referer);

    @GET(ApiConstant.INFO_WAN_TYPE)
    Observable<Response<ResponseBody>> getInfoWanType(@Header("Authorization") String cookie,
                                                      @Header("Referer") String referer);
}
