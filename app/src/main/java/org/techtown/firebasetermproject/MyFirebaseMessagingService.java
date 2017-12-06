package org.techtown.firebasetermproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.content.ContentValues.TAG;
//푸시 메시지를 전달 받는 역할 구글 클라우드 서버에서 보내오는 메시지는 이 클래스에서 받을 수 있다.
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "박정환";

    // 서버에서 보내오는 메시지를 받아서 처리
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String msgBody = "";
        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String contents = data.get("contents");


        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + from+ "contents : "+ contents);

        sendToActivity(getApplicationContext(), from, contents);


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            msgBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + msgBody);
        }

    }

    private void sendToActivity(Context context, String from, String contents) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from",from);
        intent.putExtra("contents",contents);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}