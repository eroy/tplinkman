package sergey.zhuravel.tplinkman.api;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.GetValueConst;

public interface SettingOldService {

    @GET(ApiConstant.LOGOUT)
    Observable<Response<ResponseBody>> setLogout(@Header(GetValueConst.AUTH) String cookie,
                                                 @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.REBOOT)
    Observable<Response<ResponseBody>> setReboot(@Header(GetValueConst.AUTH) String cookie,
                                                 @Header(GetValueConst.REFERER) String referer);

    @GET(ApiConstant.WIFI_MODE_SETTING)
    Observable<Response<ResponseBody>> setWifiMode(@Header(GetValueConst.AUTH) String cookie,
                                                   @Header(GetValueConst.REFERER) String referer,
                                                   @Query(GetValueConst.AP) String ap);

    @GET(ApiConstant.WAN_DYNAMIC)
    Observable<Response<ResponseBody>> setWanDynamic(@Header(GetValueConst.AUTH) String cookie,
                                                     @Header(GetValueConst.REFERER) String referer);


    @GET(ApiConstant.WAN_STATIC)
    Observable<Response<ResponseBody>> setWanStatic(@Header(GetValueConst.AUTH) String cookie,
                                                    @Header(GetValueConst.REFERER) String referer,
                                                    @Query(GetValueConst.IP) String ip,
                                                    @Query(GetValueConst.MASK) String mask,
                                                    @Query(GetValueConst.GATEWAY) String gateway,
                                                    @Query(GetValueConst.DNS1) String dns1,
                                                    @Query(GetValueConst.DNS2) String dns2);

    @GET(ApiConstant.WAN_PPTP)
    Observable<Response<ResponseBody>> setWanPptp(@Header(GetValueConst.AUTH) String cookie,
                                                  @Header(GetValueConst.REFERER) String referer,
                                                  @Query(GetValueConst.PPTPNAME) String username,
                                                  @Query(GetValueConst.PPTPPASS) String pass,
                                                  @Query(GetValueConst.PPTPPASSCONF) String passConf,
                                                  @Query(GetValueConst.PPTPSERV) String server);

    @GET(ApiConstant.WAN_PPPOE)
    Observable<Response<ResponseBody>> setWanPppoe(@Header(GetValueConst.AUTH) String cookie,
                                                   @Header(GetValueConst.REFERER) String referer,
                                                   @Query(GetValueConst.PPPOENAME) String username,
                                                   @Query(GetValueConst.PPPOEPASS) String pass,
                                                   @Query(GetValueConst.PPPOEPASSCONF) String passConf);
}
