package com.zhuolei.mobilesafe.components.dialog;


import com.zhuolei.mobilesafe.main.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



public class UpdateConfirmLayer extends DialogLayer {

	private TextView txtUpdateNotice;

	private TextView txtVersion;

	private TextView txtVersionContent;

	private Button btnUpdateOK;

	private Button btnUpdateCancel;

	private LinearLayout progresslayer;

	private TextView txtDownloadProgress;

	private TextView txtFileSize;

	private LinearLayout buttonlayer;

	private ProgressBar proDownload;

	private int total_size = 0;

	private int dialog_mode = DLG_MODE_NORMAL_UPDATE;

	public UpdateConfirmLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public UpdateConfirmLayer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {

		LayoutInflater.from(context).inflate(R.layout.versionup_confirm_layer, this);

		txtUpdateNotice = (TextView) findViewById(R.id.txtUpdateNotice);
		txtVersion = (TextView) findViewById(R.id.txtVersion);
		txtVersionContent = (TextView) findViewById(R.id.txtUpdateContent);
		btnUpdateOK = (Button) findViewById(R.id.btnUpdateOK);
		btnUpdateCancel = (Button) findViewById(R.id.btnUpdateCancel);
		progresslayer = (LinearLayout) findViewById(R.id.progress_layer);
		txtDownloadProgress = (TextView) findViewById(R.id.txtDownloaded);
		txtFileSize = (TextView) findViewById(R.id.txtFileSize);
		buttonlayer = (LinearLayout) findViewById(R.id.button_layer);
		proDownload = (ProgressBar) findViewById(R.id.proDownload);
	}

	@Override
	public void setValue(int id, int value) {
		// TODO Auto-generated method stub
		switch (id) {
		case PARAM_ID_DLG_MODE:
			dialog_mode = value;
			if (dialog_mode == DLG_MODE_DOWNLOADING) {
				progresslayer.setVisibility(View.VISIBLE);
				buttonlayer.setVisibility(View.GONE);
			} else if (dialog_mode == DLG_MODE_DOWNLOAD_FAILED) {
				progresslayer.setVisibility(View.GONE);
				buttonlayer.setVisibility(View.VISIBLE);
			} else if (dialog_mode == DLG_MODE_INSTALL) {
				progresslayer.setVisibility(View.GONE);
				buttonlayer.setVisibility(View.VISIBLE);
				btnUpdateOK.setText("Install");
				btnUpdateCancel.setText("exit");
			} else {
				progresslayer.setVisibility(View.GONE);
				buttonlayer.setVisibility(View.VISIBLE);
				txtUpdateNotice.setText(dialog_mode == DLG_MODE_NORMAL_UPDATE ? "发现新版本" : "强制更新");
				btnUpdateCancel.setText(dialog_mode == DLG_MODE_NORMAL_UPDATE ? "cancel" : "exit");
			}
			this.invalidate();
			break;
		case PARAM_ID_DOWNLOAD_PROGRESS:
			if (dialog_mode == DLG_MODE_DOWNLOADING) {
				proDownload.setProgress((total_size != 0 ? (value * 100 / total_size) : 0));
				txtDownloadProgress.setText((value / 1024) + "k");
			}
			break;
		case PARAM_ID_DOWNLOAD_FILESIZE:
			if (dialog_mode == DLG_MODE_DOWNLOADING) {
				total_size = value;
				txtFileSize.setText((value / 1024) + "k");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void setValue(int id, String value) {
		// TODO Auto-generated method stub
		switch (id) {
		case PARAM_ID_VERSION:
			txtVersion.setText(value);
			break;
		case PARAM_ID_VERSION_CONTENT:
			txtVersionContent.setText(value);
			break;

		default:
			break;
		}
	}

	@Override
	public void setPositiveBtnListener(OnClickListener listener) {
		// TODO Auto-generated method stub
		btnUpdateOK.setOnClickListener(listener);
	}

	@Override
	public void setNegativeBtnListener(OnClickListener listener) {
		// TODO Auto-generated method stub
		btnUpdateCancel.setOnClickListener(listener);
	}

	public static final int PARAM_ID_VERSION = 1;

	public static final int PARAM_ID_VERSION_CONTENT = 2;

	public static final int PARAM_ID_DLG_MODE = 3;

	public static final int PARAM_ID_DOWNLOAD_PROGRESS = 4;

	public static final int PARAM_ID_DOWNLOAD_FILESIZE = 5;

	public static final int DLG_MODE_INSTALL = 4;

	public static final int DLG_MODE_DOWNLOAD_FAILED = 3;

	public static final int DLG_MODE_DOWNLOADING = 2;

	public static final int DLG_MODE_FORCE_UPDATE = 1;

	public static final int DLG_MODE_NORMAL_UPDATE = 0;
}
