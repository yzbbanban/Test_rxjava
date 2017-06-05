package test.ban.com.test_rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.subjects.AsyncSubject;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private Observable<String> mObservable;
    private Observable<String> justObserable;
    private Observable<String> fromObservable;
    private Observable<String> deferObservable;
    private Observable<Long> intervalObservable;
    private Observable<Integer> rangeObservable;
    private Observable<Long> timerObservable;
    private Observable<String> repeatObservalbe;
    private Observer<String> mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        data();
//        obs();
        async();
    }

    private void async() {
        AsyncSubject<String> asyncSubject=AsyncSubject.create();
        asyncSubject.onNext("async ban");
        asyncSubject.onNext("async yy");
        asyncSubject.onNext("async uu");
        asyncSubject.onNext("async zi");
        asyncSubject.onNext("async xs");
        asyncSubject.onCompleted();
        asyncSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: "+s);
            }
        });
    }

    private void obs() {
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> list = new ArrayList<String>();
                list.add("ban");
                list.add("uu");
                list.add("yy");
                list.add("hz");
                list.add("zi");
                list.add("xs");
                subscriber.onNext(list);

            }
        }).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return !s.equals("uu");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String strings) {
                Log.i(TAG, "call: "+strings);
            }
        });
    }


    private void data() {
//        Observable<String> sender = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("hello world");
//            }
//        });
//        Observer<String> receiver = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i(TAG, "onCompleted: ");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, "onNext: " + s);
//            }
//        };
//        sender.subscribe(receiver);
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("create1"); //发射一个"create1"的String
                subscriber.onNext("create2"); //发射一个"create2"的String
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        });

        justObserable = Observable.just("just1", "just2");

        List<String> lists = new ArrayList<>();
        lists.add("from1");
        lists.add("from2");
        fromObservable = Observable.from(lists);


        deferObservable = Observable.defer(new Func0<Observable<String>>() {

            @Override
            public Observable<String> call() {
                return Observable.just("defer1", "defer2");
            }
        });

        intervalObservable = Observable.interval(1L, TimeUnit.SECONDS);

        rangeObservable = Observable.range(10, 7);//10,11,12,13,14,15,16

        timerObservable = Observable.timer(1L, TimeUnit.SECONDS);

        repeatObservalbe = Observable.just("repeat").repeat(3L);


        mObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: " + s);
            }
        };
        mObservable.subscribe(mObserver);
        justObserable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: " + s);
            }
        });
    }

    private static final String TAG = "MainActivity";
}
