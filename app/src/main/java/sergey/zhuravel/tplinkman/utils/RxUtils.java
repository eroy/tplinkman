package sergey.zhuravel.tplinkman.utils;

import rx.subscriptions.CompositeSubscription;


public class RxUtils {

    public static void unsubscribeIfNotNull(CompositeSubscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }
        return subscription;
    }

}
