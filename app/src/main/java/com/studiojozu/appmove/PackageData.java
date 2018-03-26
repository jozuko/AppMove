package com.studiojozu.appmove;

import android.graphics.drawable.Drawable;

/**
 * 
 * @author Jozuko
 * @version 1.0 2012/12/31
 */
public class PackageData {

	private Drawable mPackageIcon = null;

	private String mApplicationLabel = null;

	private String mPackageName = null;

	private String mApkPath = null;

	/**
	 * apkPathのgetter
	 * 
	 * @return apkPath
	 */
	public String getApkPath() {
		return mApkPath;
	}

	/**
	 * applicationLabelのgetter
	 * 
	 * @return applicationLabel
	 */
	public String getApplicationLabel() {
		return mApplicationLabel;
	}

	/**
	 * packageIconのgetter
	 * 
	 * @return packageIcon
	 */
	public Drawable getPackageIcon() {
		return mPackageIcon;
	}

	/**
	 * packageNameのgetter
	 * 
	 * @return packageName
	 */
	public String getPackageName() {
		return mPackageName;
	}

	/**
	 * apkPathのsetter
	 * 
	 * @param apkPath セットする値：apkPath
	 */
	public void setApkPath(String apkPath) {
		mApkPath = apkPath;
	}

	/**
	 * applicationLabelのsetter
	 * 
	 * @param applicationLabel セットする値：applicationLabel
	 */
	public void setApplicationLabel(String applicationLabel) {
		mApplicationLabel = applicationLabel;
	}

	/**
	 * packageIconのsetter
	 * 
	 * @param packageIcon セットする値：packageIcon
	 */
	public void setPackageIcon(Drawable packageIcon) {
		mPackageIcon = packageIcon;
	}

	/**
	 * packageNameのsetter
	 * 
	 * @param packageName セットする値：packageName
	 */
	public void setPackageName(String packageName) {
		mPackageName = packageName;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("package[").append((mPackageName == null ? "null" : mPackageName)).append("]");
		buffer.append("label[").append((mApplicationLabel == null ? "null" : mApplicationLabel)).append("]");
		buffer.append("sourceDir[").append((mApkPath == null ? "null" : mApkPath)).append("]");

		return buffer.toString();
	}
}
