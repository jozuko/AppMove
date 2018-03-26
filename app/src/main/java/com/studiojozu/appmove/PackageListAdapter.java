package com.studiojozu.appmove;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Jozuko
 * @version 1.0 2012/12/31
 */
public final class PackageListAdapter extends ArrayAdapter<PackageData> {

	private LayoutInflater mLayoutInflater = null;

	/**
	 * @param context
	 * @param textViewResourceId
	 */
	public PackageListAdapter(Context context, int textViewResourceId) {
		super(context, R.layout.list_item);
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<PackageData> packageDatas) {
		clear();
		for (PackageData packageData : packageDatas) {
			add(packageData);
		}
	}

	/**
	 * @see ArrayAdapter#getView(int, View, ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.list_item, null);
		}

		ImageView iconImageView = (ImageView) convertView.findViewById(R.id.icon);
		TextView labelTextView = (TextView) convertView.findViewById(R.id.label);
		TextView packageNameTextView = (TextView) convertView.findViewById(R.id.packageName);

		PackageData packageData = (PackageData) getItem(position);

		if (packageData.getPackageIcon() != null) {
			iconImageView.setImageDrawable(packageData.getPackageIcon());
		}
		labelTextView.setText(packageData.getApplicationLabel());
		packageNameTextView.setText(packageData.getPackageName());

		return convertView;
	}

}
