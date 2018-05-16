package lib.smart.fragmentv4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Augustine on 2018/5/15.
 * <p>
 * email:nice_ohoh@163.com
 */

public class FragmentList {

    private List<FragmentModel> modelList;

    private FragmentListChangeListener fragmentListChangeListener;

    public FragmentList(){
        modelList = new ArrayList<>();
    }

    public FragmentListChangeListener getFragmentListChangeListener() {
        return fragmentListChangeListener;
    }

    public void setFragmentListChangeListener(FragmentListChangeListener fragmentListChangeListener) {
        this.fragmentListChangeListener = fragmentListChangeListener;
    }

    public List<FragmentModel> getModelList() {
        return modelList;
    }

    public void addModel(FragmentModel model){
        modelList.add(model);
        if(fragmentListChangeListener != null){
            fragmentListChangeListener.addListener(model);
        }
    }

    public void remove(String tag){
        for(FragmentModel model : modelList){
            if(model.tag.equals(tag)){
                if(fragmentListChangeListener != null){
                    fragmentListChangeListener.removeListener(model);
                }
                modelList.remove(model);
                return;
            }
        }
    }

    public FragmentModel findModelByTag(String tag){
        for(FragmentModel model : modelList){
            if(model.tag.equals(tag)){
                return model;
            }
        }
        return null;
    }

    public int getSize(){
        return modelList.size();
    }


}

