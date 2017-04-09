package com.franckyi.mpb.core.tasks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.franckyi.mpb.MPBApplication;

import javafx.concurrent.Task;

public class DownloadModTask extends Task<Void> {

	private URL source;
	private File destination;
	
	public DownloadModTask(URL source, File destination) {
		this.source = source;
		this.destination = destination;
	}

	@Override
	protected Void call() throws Exception {
		MPBApplication.print("Download started : " + destination.getName());
		HttpURLConnection httpConnection = (HttpURLConnection) (source.openConnection());
        long completeFileSize = httpConnection.getContentLength();
        BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
        FileOutputStream fis = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        long dl = 0;
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
            dl += count;
            this.updateProgress(dl, completeFileSize);
        }
        fis.close();
        bis.close();
		return null;
	}

}
