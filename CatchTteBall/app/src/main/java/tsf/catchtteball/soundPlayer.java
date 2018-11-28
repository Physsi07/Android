package tsf.catchtteball;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Starlyn on 1/5/17.
 */
public class soundPlayer
{
    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;

    public soundPlayer(Context context)
    {

        //SoundPool (int maxStream, int streamType, int scrQuality)
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC , 0);

        hitSound = soundPool.load(context, R.raw.hit, 1);
        overSound = soundPool.load(context, R.raw.over, 1);

    }

    public void playHitSound()
    {
        //play (int sound ID,float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);

    }

    public void playOverSound()
    {
        soundPool.play(overSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
