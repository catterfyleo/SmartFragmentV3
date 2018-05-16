package lib.smart.fragmentv4;

import android.support.v4.app.Fragment;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public interface FragmentFunction {
    /**
     * 加载主界面
     * @param fragment
     * @param commitCallBack
     */
    void addMain(SmartFragment fragment,int containerViewId,CommitCallBack commitCallBack);

    /**
     * 加载嵌套的fragment
     * @param startF
     * @param fragment
     * @param commitCallBack
     */
    void addChild(SmartFragment startF,SmartFragment fragment,int containerViewId,CommitCallBack commitCallBack);

    /**
     * 加载同级fragment
     * @param startF
     * @param fragment
     * @param commitCallBack
     */
    void addBrother(SmartFragment startF, SmartFragment fragment,int containerViewId,CommitCallBack commitCallBack);

    void show(SmartFragment fragment,CommitCallBack commitCallBack);

    void hide(SmartFragment fragment,CommitCallBack commitCallBack);

    void remove(SmartFragment fragment,CommitCallBack commitCallBack);
}
