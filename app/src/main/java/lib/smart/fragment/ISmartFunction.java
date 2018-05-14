package lib.smart.fragment;

import java.util.List;


/**
 * Created by Augustine on 2018/5/14.
 * <p>
 * email:nice_ohoh@163.com
 */

public interface ISmartFunction {

    void add(SmartFragment fragment, FragmentModel model, ICommitCallBack commitCallBack);

    void add(SmartFragment fragment, int containerViewId ,ICommitCallBack commitCallBack);

    void add(List<SmartFragment> fragmentList, int containerViewId);

    void addChild(List<SmartFragment> fragmentList, int containerViewId,String parentTag);



    void show(SmartFragment fragment);

    void hide(SmartFragment fragment);

    void show(String tag);

    void hide(String tag);

    void showHideAllChild(SmartFragment fragment);


    void remove(SmartFragment fragment,ICommitCallBack commitCallBack);

    void remove(String tag,ICommitCallBack commitCallBack);



}
