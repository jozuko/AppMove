package com.studiojozu.appmove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;

/**
 * 
 * @author Jozuko
 * @version 1.0 2012/12/31
 */
public final class PackageLoader extends AsyncTask<String, Integer, List<PackageData>> {

	public interface Complete {
		public void onComplete(List<PackageData> packageDatas);
	}

	private Context mContext = null;

	private Complete mComplete = null;

	private PackageManager mPackageManager = null;

	public PackageLoader(Context context, Complete complete) {
		mContext = context;
		mComplete = complete;
	}

	/**
	 * @see AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		mPackageManager = mContext.getPackageManager();
	}

	/**
	 * @see AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected List<PackageData> doInBackground(String... params) {
		List<ApplicationInfo> applicationInfos = mPackageManager.getInstalledApplications(0);

		List<PackageData> packageDatas = new ArrayList<PackageData>();
		for (ApplicationInfo applicationInfo : applicationInfos) {
			String packageName = applicationInfo.packageName;

			PackageData packageData = new PackageData();

			CharSequence applicationLabel = applicationInfo.loadLabel(mPackageManager);
			if (TextUtils.isEmpty(applicationLabel)) {
				packageData.setApplicationLabel("");
			}
			else {
				packageData.setApplicationLabel(applicationLabel.toString());
			}

			packageData.setPackageIcon(applicationInfo.loadIcon(mPackageManager));
			packageData.setPackageName(packageName);
			packageData.setApkPath(applicationInfo.sourceDir);

			packageDatas.add(packageData);
		}

		Collections.sort(packageDatas, new PackageDataComparetor());
		return packageDatas;
	}

	/**
	 * @see AsyncTask#onPostExecute(Object)
	 */
	@Override
	protected void onPostExecute(List<PackageData> result) {
		mComplete.onComplete(result);
	}

	private int compareTo(String value1, String value2) {
		value1 = nullToEmpty(value1);
		value2 = nullToEmpty(value2);
		return value1.compareTo(value2);
	}

	private String nullToEmpty(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	private final class PackageDataComparetor implements Comparator<PackageData> {

		/**
		 * @see Comparator#compare(Object, Object)
		 */
		@Override
		public int compare(PackageData packageData1, PackageData packageData2) {
			int result = compareTo(packageData1.getApplicationLabel(), packageData2.getApplicationLabel());
			if (result != 0)
				return result;

			return compareTo(packageData1.getPackageName(), packageData2.getPackageName());
		}
	}
}
