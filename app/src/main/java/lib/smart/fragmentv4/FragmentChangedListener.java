package lib.smart.fragmentv4;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public interface FragmentChangedListener {

    void init(SmartFragment fragment);

    void userVisible(boolean visible);

    void destroy();

}
