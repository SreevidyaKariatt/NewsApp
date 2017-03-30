package robosoft.com.jsonparser.common.helper;




import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import robosoft.com.jsonparser.common.ui.DashBoardActivity;

/**
 * Created by sree on 11/12/15.
 */
public class FragmentOperations {
    public static void addFragment(DashBoardActivity activity,Fragment fragment,int containerId,String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerId,fragment,tag).addToBackStack(tag);
        transaction.commit();
    }
}
