package lib.smart.fragmentv4;


/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public interface FragmentFunction {
    /**
     * 加载主界面，每个activity仅可调用一次改方法
     *
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
    void addBrother(SmartFragment startF, SmartFragment fragment,CommitCallBack commitCallBack);

    /**
     * 创建一个单独的fragment
     * tip1:无法使用侧滑关闭
     * tip2:无法在此基础上创建兄弟fragment
     * @param startF
     * @param fragment
     * @param commitCallBack
     */
    void addAlone(SmartFragment startF,SmartFragment fragment,CommitCallBack commitCallBack);

    void show(SmartFragment fragment,CommitCallBack commitCallBack);

    void hide(SmartFragment fragment,CommitCallBack commitCallBack);

    void remove(SmartFragment fragment,CommitCallBack commitCallBack);

    /**
     * 删除自己的兄弟fragment
     * @param formFragment
     * @param commitCallBack 在完成删除时，调用 1 次，并回调null
     */
    void removeBrothers(SmartFragment formFragment,CommitCallBack commitCallBack);

    /**
     * 删除最后一个
     * @param commitCallBack
     */
    void removeLast(CommitCallBack commitCallBack);
}
