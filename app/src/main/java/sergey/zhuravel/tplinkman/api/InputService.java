package sergey.zhuravel.tplinkman.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.ApiConstant;


public interface InputService {

    @GET(ApiConstant.INPUT_KEY)
    Observable<Response<ResponseBody>> getKey(@Header("Cookie") String cookie);

    @GET(ApiConstant.INPUT_VALIDATE)
    Observable<Response<ResponseBody>> validateInput(@Header("Cookie") String cookie,
                                                     @Header("Referer") String referer);

}
