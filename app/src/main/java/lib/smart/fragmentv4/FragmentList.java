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

    /**
     * 根据tag 查找自己的兄弟model，但是不包含自己
     */
    public List<FragmentModel> findBrotherModelsByTag(String tag){
        FragmentModel fromModel = findModelByTag(tag);
        List<FragmentModel> list = new ArrayList<>();
        for(FragmentModel model : modelList){
            if(model.brotherParentTag.equals(fromModel.brotherParentTag)){
               if(!model.brotherParentTag.equals(model.tag)){
                   list.add(model);
               }
            }
        }
        return list;
    }

    /**
     * 根据parentTag查找models
     * @param parentTag
     * @return
     */
    public List<FragmentModel> findModelsByParentTag(String parentTag){
        List<FragmentModel> list = new ArrayList<>();
        for(FragmentModel model : modelList){
            if(model.parentTag.equals(parentTag)){
                list.add(model);
            }
        }
        return list;
    }

    public int getSize(){
        return modelList.size();
    }


    public FragmentModel getLastModel(){
        return modelList.get(modelList.size()-1);
    }
}

