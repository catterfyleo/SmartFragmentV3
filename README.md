# SmartFragmentV3


https://github.com/CH-Augustine/SmartFragmentV3

单Activity多Fragment的应用构架思想

使用：
main_activity.xml
<FrameLayout
    android:id="@+id/contentFrameLayout"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</FrameLayout>

MainActivity.class extends SmartActivity

 MainFragment mainFragment = MainFragment.newInstance();
        add(mainFragment, R.id.contentFrameLayout, new ICommitCallBack() {
            @Override
            public void onCommit(SmartFragment fragment) {
                fragment.getModel().swipeBack = false;
                fragment.getSwipeBackLayout().setOpenTouchSlide(false);
                fragment.getModel().enterAnim = false;
                fragment.getModel().exitAnim = false;
                fragment.getModel().popEnterAnim = true;
                fragment.getModel().popExitAnim = true;
                show(fragment.getTAG());
            }
        });
        
    @Override
    public void onBackPressed() {
        if(!backPressed()){
            super.onBackPressed();
        }
    }

