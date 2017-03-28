package sergey.zhuravel.tplinkman.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;

public interface InfoService {

    @GET(ApiConstant.INFO_FIRMWARE)
    Observable<Response<ResponseBody>> getInfoFirmware(@Header("Cookie") String cookie,
                                                       @Header("Referer") String referer);

}
