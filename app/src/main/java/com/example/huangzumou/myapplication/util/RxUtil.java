package com.example.huangzumou.myapplication.util;
import com.example.huangzumou.myapplication.util.LogUtil;
import com.example.huangzumou.myapplication.model.http.exception.ApiException;
import com.example.huangzumou.myapplication.model.http.response.SsPanelResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<SsPanelResponse<T>, T> handleSsPanelResult() {   //compose判断结果
        return new FlowableTransformer<SsPanelResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<SsPanelResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<SsPanelResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(SsPanelResponse<T> tMyHttpResponse) {
                        //LogUtil.i(tMyHttpResponse.getMsg());
                        if(tMyHttpResponse.getRet() == 1) {
                            //LogUtil.i("正常返回 "+tMyHttpResponse.getMsg());
                            return createData(tMyHttpResponse.getData());
                        }else if(tMyHttpResponse.getRet() == 0){
                            //LogUtil.i("校验失败");
                            return Flowable.error(new ApiException(tMyHttpResponse.getMsg()));
                        }else{
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }



    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
