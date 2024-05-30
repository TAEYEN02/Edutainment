package org.koreait.edutainment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    private MediaPlayer player;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener focusChangeListener;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("음악 재생 서비스", "onCreate() 호출");
        super.onCreate();

        // 오디오 포커스 변경 리스너 생성
        focusChangeListener = focusChange -> {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    // 오디오 포커스를 얻었을 때
                    if (player == null) {
                        player = MediaPlayer.create(this, R.raw.music);
                        player.setLooping(true);
                    }
                    player.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    // 오디오 포커스를 잃었을 때
                    if (player != null) {
                        player.stop();
                        player.release();
                        player = null;
                    }
                    break;
                // 다른 경우들...
            }
        };

        // 오디오 포커스 요청
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(focusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // 오디오 포커스 요청이 승인되었을 때
            player = MediaPlayer.create(this, R.raw.music);
            player.setLooping(true);
            player.start();
        }
    }

    @Override
    public void onDestroy() {
        Log.e("음악 재생 서비스", "onDestroy() 호출");
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        // 오디오 포커스 해제
        audioManager.abandonAudioFocus(focusChangeListener);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("서비스 테스트", "onStartCommand() 호출");
        return super.onStartCommand(intent, flags, startId);
    }
}
