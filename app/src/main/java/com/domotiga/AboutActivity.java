package com.domotiga;

import org.xmlrpc.android.XMLRPCClientSSH;
import org.xmlrpc.android.XMLRPCException;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.domotiga.tools.XMLRPC;

public class AboutActivity extends Activity {
	
	private ImageButton back, refresh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.about);        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
		
        refresh = (ImageButton)findViewById(R.id.refresh);
        refresh.setVisibility(View.GONE);
        
		back = (ImageButton)findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener()
   		{
   			public void onClick(View v)
   			{
   				finish();
   			}
   		});
		XMLRPCClientSSH client = XMLRPC.getClient(this);
		String domotigaversion = getResources().getString(R.string.error_server_unavailable);
		String uptime = getResources().getString(R.string.error_server_unavailable);
		if (client != null) {
			try {
				domotigaversion = (String) client
						.call("system.program_version");
				uptime = (String) client.call("system.program_uptime");
			} catch (XMLRPCException e) {
				e.printStackTrace();
			}
		}
		
		String apkversion = "";
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			apkversion = packageInfo.versionName;
		} catch (NameNotFoundException e) {
		}

		setContentView(R.layout.about);		
		
		TextView tav = (TextView) findViewById(R.id.TextView01);
		tav.setText(getResources().getString(R.string.about_software_version)+ apkversion);

		TextView tdv = (TextView) findViewById(R.id.TextView02);
		tdv.setText(getResources().getString(R.string.about_domotiga_version)+ domotigaversion);

		TextView tu = (TextView) findViewById(R.id.TextView03);
		tu.setText(getResources().getString(R.string.about_domotiga_uptime)+ uptime);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
}
