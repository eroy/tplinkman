package sergey.zhuravel.tplinkman.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.GetValueConst;

public interface InfoService {

    @GET(ApiConstant.INFO_FIRMWARE)
    Observable<Response<ResponseBody>> getInfoFirmware(@Header(GetValueConst.COOKIE) String cookie,
                                                       @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_MAC)
    Observable<Response<ResponseBody>> getInfoMac(@Header(GetValueConst.COOKIE) String cookie,
                                                  @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WIFI_NAME)
    Observable<Response<ResponseBody>> getInfoWifiName(@Header(GetValueConst.COOKIE) String cookie,
                                                       @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WIFI_PASS)
    Observable<Response<ResponseBody>> getInfoWifiPass(@Header(GetValueConst.COOKIE) String cookie,
                                                       @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_STATUS)
    Observable<Response<ResponseBody>> getInfoStatus(@Header(GetValueConst.COOKIE) String cookie,
                                                     @Header(GetValueConst.REFERER) String referer);


    @GET(ApiConstant.INFO_WIFI_STATION)
    Observable<Response<ResponseBody>> getInfoWifiStation(@Header(GetValueConst.COOKIE) String cookie,
                                                          @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WIFI_STATION_NAME)
    Observable<Response<ResponseBody>> getInfoWifiStationName(@Header(GetValueConst.COOKIE) String cookie,
                                                              @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WAN_DYNAMIC)
    Observable<Response<ResponseBody>> getInfoWanDynamic(@Header(GetValueConst.COOKIE) String cookie,
                                                         @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WAN_STATIC)
    Observable<Response<ResponseBody>> getInfoWanStatic(@Header(GetValueConst.COOKIE) String cookie,
                                                        @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WAN_PPTP)
    Observable<Response<ResponseBody>> getInfoWanPptp(@Header(GetValueConst.COOKIE) String cookie,
                                                      @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WAN_PPPOE)
    Observable<Response<ResponseBody>> getInfoWanPppoe(@Header(GetValueConst.COOKIE) String cookie,
                                                       @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.INFO_WAN_TYPE)
    Observable<Response<ResponseBody>> getInfoWanType(@Header(GetValueConst.COOKIE) String cookie,
                                                      @Header(GetValueConst.REFERER) String referer);

}
