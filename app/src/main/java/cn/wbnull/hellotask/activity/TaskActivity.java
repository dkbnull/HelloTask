package cn.wbnull.hellotask.activity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wbnull.hellotask.R;
import cn.wbnull.hellotask.util.SpUtils;

/**
 * 定时任务界面
 *
 * @author dukunbiao(null)  2022-09-11
 */
@SuppressLint("NonConstantResourceId")
public class TaskActivity extends AppCompatActivity {

    @BindView(R.id.taskEdtClockIn)
    public EditText taskEdtClockIn;

    @BindView(R.id.taskEdtClockOut)
    public EditText taskEdtClockOut;

    private Timer timer1;
    private Timer timer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ButterKnife.bind(this);

        taskEdtClockIn.setText(SpUtils.getClockIn());
        taskEdtClockOut.setText(SpUtils.getClockOut());

        timeTrigger();
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.taskEdtClockIn)
    public void onClickEdtClockIn() {
        String clockIn = SpUtils.getClockIn();
        String[] datetime = clockIn.split(":");
        int hourOfDay = 0;
        int minute = 0;
        if (datetime.length == 2) {
            hourOfDay = Integer.parseInt(datetime[0]);
            minute = Integer.parseInt(datetime[1]);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, thour, tminute) -> {
            String time = "";
            if (thour < 10) {
                time = "0" + thour;
            } else {
                time += thour;
            }
            time += ":";
            if (tminute < 10) {
                String m = "0" + tminute;
                time += m;
            } else {
                time += tminute;
            }
            taskEdtClockIn.setText(time);
        }, hourOfDay, minute, true);
        timePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.taskEdtClockOut)
    public void onClickEdtClockOut() {
        String clockOut = SpUtils.getClockOut();
        String[] datetime = clockOut.split(":");
        int hourOfDay = 0;
        int minute = 0;
        if (datetime.length == 2) {
            hourOfDay = Integer.parseInt(datetime[0]);
            minute = Integer.parseInt(datetime[1]);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, thour, tminute) -> {
            String time = "";
            if (thour < 10) {
                time = "0" + thour;
            } else {
                time += thour;
            }
            time += ":";
            if (tminute < 10) {
                String m = "0" + tminute;
                time += m;
            } else {
                time += tminute;
            }

            taskEdtClockOut.setText(time);
        }, hourOfDay, minute, true);
        timePickerDialog.show();
    }

    @OnClick(R.id.taskBtnSave)
    public void onClickSave() {
        SpUtils.setClockIn(taskEdtClockIn.getText().toString());
        SpUtils.setClockOut(taskEdtClockOut.getText().toString());

        timeTrigger();
    }

    @OnClick(R.id.taskBtnClockIn)
    public void onClickClockIn() {
        startWeWork();
    }

    private void timeTrigger() {
        time1Trigger();
        time2Trigger();
    }

    private void time1Trigger() {
        String clockIn = SpUtils.getClockIn();
        String[] datetime = clockIn.split(":");
        int hour = 0;
        int minute = 0;
        if (datetime.length == 2) {
            hour = Integer.parseInt(datetime[0]);
            minute = Integer.parseInt(datetime[1]);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        if (date.before(new Date())) {
            date = this.addDay(date);
        }
        timer1 = new Timer(true);
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                startWeWork();
            }
        }, date, 86400000);
    }

    private void time2Trigger() {
        String clockOut = SpUtils.getClockOut();
        String[] datetime = clockOut.split(":");
        int hour = 0;
        int minute = 0;
        if (datetime.length == 2) {
            hour = Integer.parseInt(datetime[0]);
            minute = Integer.parseInt(datetime[1]);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        if (date.before(new Date())) {
            date = this.addDay(date);
        }
        timer2 = new Timer(true);
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                startWeWork();
            }
        }, date, 86400000);
    }

    private Date addDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private void startWeWork() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.tencent.wework",
                "com.tencent.wework.launch.LaunchSplashActivity"));
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(intent);
    }
}
