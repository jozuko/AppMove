package com.studiojozu.appmove;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements PackageLoader.Complete, ApkMover.Complete, AdapterView.OnItemClickListener {

	private PackageManager mPackageManager = null;

	private LoadPackageProgressDialog mLoadPackageProgressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mLoadPackageProgressDialog = new LoadPackageProgressDialog();
		mLoadPackageProgressDialog.show(getSupportFragmentManager(), "progress-dialog");

		String[] params = null;
		PackageLoader packageLoader = new PackageLoader(this, this);

		packageLoader.execute(params);
	}

	public static final class LoadPackageProgressDialog extends DialogFragment {

		/**
		 * @see DialogFragment#onCreateDialog(Bundle)
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			ProgressDialog progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage(getString(R.string.dialog_title_message));
			progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);

			return progressDialog;
		}
	}

	/**
	 * @see com.jozuo.tools.apkmove.PackageLoader.Complete#onComplete(List)
	 */
	@Override
	public void onComplete(List<PackageData> packageDatas) {
		ListView listView = (ListView) findViewById(R.id.list_apl);
		listView.setOnItemClickListener(this);

		PackageListAdapter listAdapter = new PackageListAdapter(getApplicationContext(), R.layout.list_item);
		listAdapter.setData(packageDatas);
		listView.setAdapter(listAdapter);

		mLoadPackageProgressDialog.dismiss();
	}

	/**
	 * @see AdapterView.OnItemClickListener#onItemClick(AdapterView, View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mLoadPackageProgressDialog.show(getSupportFragmentManager(), "progress-dialog");

		ListView listView = (ListView) parent;
		PackageListAdapter listAdapter = (PackageListAdapter) listView.getAdapter();

		PackageData packageData = listAdapter.getItem(position);
		ApkMover apkMover = new ApkMover(this);
		apkMover.execute(packageData);

	}

	/**
	 * @see com.jozuo.tools.apkmove.ApkMover.Complete#onComplete(boolean)
	 */
	@Override
	public void onComplete(boolean result) {
		mLoadPackageProgressDialog.dismiss();
		if (result) {
			Toast.makeText(getApplicationContext(), R.string.message_success, Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(getApplicationContext(), R.string.message_failed, Toast.LENGTH_SHORT).show();
		}
	}
}
