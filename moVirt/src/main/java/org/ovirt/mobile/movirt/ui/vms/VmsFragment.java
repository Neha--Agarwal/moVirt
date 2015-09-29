package org.ovirt.mobile.movirt.ui.vms;

import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.melnykov.fab.FloatingActionButton;
import org.androidannotations.annotations.EFragment;
import org.ovirt.mobile.movirt.R;
import org.ovirt.mobile.movirt.model.Vm;
import org.ovirt.mobile.movirt.provider.OVirtContract;
import org.ovirt.mobile.movirt.ui.BaseEntityListFragment;
import android.widget.LinearLayout;
import android.widget.ListView;

@EFragment(R.layout.fragment_base_entity_list)
public class VmsFragment extends BaseEntityListFragment<Vm> implements OVirtContract.Vm {

    private static final String TAG = VmsFragment.class.getSimpleName();
boolean searchtoggle=false;
    public VmsFragment() {
        super(Vm.class);
    }

    @Override
    protected CursorAdapter createCursorAdapter() {
        SimpleCursorAdapter vmListAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.vm_list_item,
                null,
                new String[]{NAME, STATUS},
                new int[]{R.id.vm_name, R.id.vm_status}, 0);
ListView listView = (ListView) getView().findViewById(android.R.id.list);
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.attachToListView(listView);
        final LinearLayout searchfrag= (LinearLayout) getView().findViewById(R.id.searchbox);

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(searchtoggle==false){
                   searchfrag.setVisibility(View.VISIBLE); searchtoggle=true;}
               else if(searchtoggle==true){
                   searchfrag.setVisibility(View.GONE); searchtoggle=false;}
           }
       });
        vmListAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == cursor.getColumnIndex(NAME)) {
                    TextView textView = (TextView) view;
                    String vmName = cursor.getString(cursor.getColumnIndex(NAME));
                    textView.setText(vmName);
                } else if (columnIndex == cursor.getColumnIndex(STATUS)) {
                    ImageView imageView = (ImageView) view;
                    Vm.Status status = Vm.Status.valueOf(cursor.getString(cursor.getColumnIndex(STATUS)));
                    imageView.setImageResource(status.getResource());
                }

                return true;
            }
        });

        return vmListAdapter;
    }
}
