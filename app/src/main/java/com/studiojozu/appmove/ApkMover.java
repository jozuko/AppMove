package com.studiojozu.appmove;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * @author Jozuko
 * @version 1.0 2012/12/31
 */
public class ApkMover extends AsyncTask<PackageData, Integer, Boolean> {

	public interface Complete {
		public void onComplete(boolean result);
	}

	private Complete mComplete = null;

	public ApkMover(Complete complete) {
		mComplete = complete;
	}

	/**
	 * @see AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Boolean doInBackground(PackageData... params) {

		try {
			PackageData packageData = params[0];
			String apkPath = packageData.getApkPath();
			File apkFile = new File(apkPath);
			File targetFile = new File(new File(Environment.getExternalStorageDirectory(), "apkMove"), apkFile.getName());
			targetFile.getParentFile().mkdirs();

			copyTransfer(apkFile, targetFile);
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

	/**
	 * @see AsyncTask#onPostExecute(Object)
	 */
	@Override
	protected void onPostExecute(Boolean result) {
		mComplete.onComplete(result);
	}

	private void copyTransfer(File src, File target) throws IOException {

		FileChannel srcChannel = null;
		FileChannel destChannel = null;
		try {
			srcChannel = new FileInputStream(src).getChannel();
			destChannel = new FileOutputStream(target).getChannel();

			srcChannel.transferTo(0, srcChannel.size(), destChannel);
		}
		catch (IOException e) {
			Log.e("ApkMove", e.toString(), e);
			throw e;
		}
		finally {
			try {
				if (srcChannel != null)
					srcChannel.close();
			}
			catch (IOException e) {
				Log.e("ApkMove", e.toString(), e);
				throw e;
			}
			finally {
				try {
					if (destChannel != null)
						destChannel.close();
				}
				catch (IOException e) {
					Log.e("ApkMove", e.toString(), e);
					throw e;
				}
			}
		}

	}

}
