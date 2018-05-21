package lib.smart.fragmentv4;

import android.util.Log;

/**
 * Created by Augustine on 2018/5/21.
 * <p>
 * email:nice_ohoh@163.com
 */

public class FragmentTaskLog {

    private FragmentList fragmentList;

    public FragmentTaskLog(FragmentList fragmentList) {
        this.fragmentList = fragmentList;
    }

    public void printLog(){
        log("当前共有："+fragmentList.getSize()+"个fragment");
        log("----------------------------------------------");
        for(int i = 0 ; i < fragmentList.getSize() ; i++){
            FragmentModel model = fragmentList.getModelList().get(i);
            if(model.parentTag.equals("main") && i == 0){
                log("栈顶         "+model.simpleName);
            }
            if(model.parentTag.equals("main") && i != 0){
                log("↓            "+model.simpleName);
            }
            for(int j = 0 ; j < fragmentList.getSize() ; j ++){
                FragmentModel model2 = fragmentList.getModelList().get(j);
                if(model.tag.equals(model2.parentTag)){
                log("child        "+model2.simpleName);
                }
            }
        }
    }

    private void log(String msg){
        Log.e("FragmentTaskLog",msg);
    }
}
