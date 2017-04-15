package sergey.zhuravel.tplinkman.ui.block;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.subscriptions.CompositeSubscription;
import sergey.zhuravel.tplinkman.constant.ApiConstant;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.model.Blocked;
import sergey.zhuravel.tplinkman.utils.MacFormating;
import sergey.zhuravel.tplinkman.utils.RxUtils;
import sergey.zhuravel.tplinkman.utils.Utils;

public class BlockPresenter implements BlockContract.Presenter {

    private CompositeSubscription mCompositeSubscription;
    private BlockContract.Model mModel;
    private BlockContract.View mView;
    private List<String> mMacList;
    private int mCurrentPage = 1;

    public BlockPresenter(BlockContract.View mView, BlockContract.Model mModel) {
        this.mModel = mModel;
        this.mView = mView;
        mMacList = new ArrayList<>();


        mCompositeSubscription = RxUtils.getNewCompositeSubIfUnsubscribed(mCompositeSubscription);
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
        mMacList = null;
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }

    @Override
    public void getWifiFilterInfo() {
        mCompositeSubscription.add(mModel.getInfoWifiFilter(ApiConstant.INFO_WIFI_FILTER, TypeConstant.INFO_WIFI_FILTER, mCurrentPage)
                .subscribe(strings -> {

                    if (Integer.parseInt(strings.get(1)) == mCurrentPage) {
                        List<String> listMac = Utils.getMacByString(strings.get(0));

                        mMacList.addAll(listMac);

                        if (listMac.size() > 0) {
                            mView.noBlockedTextAccessibility(false);
                            mView.addBlockedList(getMacAndDescrByStr(strings.get(0), listMac));
                        } else {
                            mView.noBlockedTextAccessibility(true);
                        }


                        ++mCurrentPage;
                        getWifiFilterInfo();

                    }

                }, throwable -> Log.e("SERJ", throwable.getMessage())));


    }

    @Override
    public void setConfirmUnBlock(int position) {
        String mac = mMacList.get(position);
        mView.showConfirmUnBlockDialog(mac, position);
    }

    @Override
    public void setUnBlockClient(String mac, int position) {
        mCompositeSubscription.add(mModel.setUnblockClient(ApiConstant.INFO_WIFI_FILTER, position)
                .subscribe(s -> {
                            if (s.contains(TypeConstant.CLIENT_UNBLOCK)) {
                                mView.showSuccessToast(mac);

                            } else {
                                mView.showErrorToast();
                            }

                        },
                        throwable -> Log.e("UnBlock-error-po", throwable.getMessage())));

        reloadList();
    }

    @Override
    public void setBlockClient(String mac, String reason) {
        if (MacFormating.macValidate(mac) && !mac.equals(mView.getMacDevice())) {

            mCompositeSubscription.add(mModel.setBlockClient(ApiConstant.WIFI_BLOCKED_REFERER, mac, reason)
                    .subscribe(s -> {

                                if (s.contains(TypeConstant.CLIENT_UNBLOCK)) {
                                    mView.showSuccessToast(mac);

                                    reloadList();
                                } else {
                                    mView.showErrorToast();
                                }

                            },
                            throwable -> Log.e("Block-error-po", throwable.getMessage())));


        } else {
            mView.showNoValidateMacToast();
        }

    }

    private void reloadList() {
        mCurrentPage = 1;
        mView.clearBlockedList();
        getWifiFilterInfo();
    }

    private List<Blocked> getMacAndDescrByStr(String str, List<String> listMac) {
        List<Blocked> data = new ArrayList<>();
        for (String mac : listMac) {
            String[] str2 = str.split(mac);
            String[] desk = str2[1].split(",");
            data.add(new Blocked(mac, desk[4].replace("\"", "")));
        }

        return data;
    }


}
