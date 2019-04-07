package edu.etzion.koletzion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

public class ForegroundService extends Service {
	
	// Constants
	private static final int ID_SERVICE = 101;
	public static String streamName = "קול עציון";
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}
	
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// do stuff like register for BroadcastReceiver, etc.
		
		// Create the Foreground Service
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
		Notification notification = notificationBuilder.setOngoing(true)
				.setSmallIcon(R.drawable.audio)
				.setPriority(PRIORITY_MIN)
				.setCategory(NotificationCompat.CATEGORY_SERVICE)
				.build();
		
		startForeground(ID_SERVICE, notification);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopForeground(true);
	}
	
	@RequiresApi(Build.VERSION_CODES.O)
	private String createNotificationChannel(NotificationManager notificationManager) {
		String channelId = "default";
		
		NotificationChannel channel = new NotificationChannel(channelId, channelId,
				NotificationManager.IMPORTANCE_DEFAULT);
		// omitted the LED color
		channel.setImportance(NotificationManager.IMPORTANCE_NONE);
		channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
		notificationManager.createNotificationChannel(channel);
		return channelId;
	}
}