package coms309.cycrawl.com.volleywithdb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Nate on 10/6/2017.
 */

public class myApplication extends ArrayAdapter<String>{

    private String[] accountID;
    private String[] userName;
    private String[] userPassword;
    private String[] adminID;
    private Activity context;


    public myApplication(Activity context, String[] accountID, String[] userName, String[] userPassword, String[] adminID){
        super(context, R.layout.list_view_layout, accountID);
        this.context = context;
        this.accountID = accountID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.adminID  = adminID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView textViewaccountID = (TextView) listViewItem.findViewById(R.id.textViewaccountID);
        TextView textViewuserName = (TextView) listViewItem.findViewById(R.id.textViewuserName);
        TextView textViewuserPassword = (TextView) listViewItem.findViewById(R.id.textViewuserPassword);
        TextView textViewadminID = (TextView) listViewItem.findViewById(R.id.textViewadminID);

        textViewaccountID.setText(accountID[position]);
        textViewuserName.setText(userName[position]);
        textViewuserPassword.setText(userPassword[position]);
        textViewadminID.setText(adminID[position]);

        return listViewItem;
    }
}
