import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>
 * Title: Проект Java Super Computer
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * </p>
 * <p>
 * Copyright: Copyright MEPHI (c) 2014
 * </p>
 *
 * @author vyacheslav.beketnov &lt;beketnov.vm@gmail.ru&gt;
 * @version 1.0
 */
public class WaitingFormatter extends TimerTask {
    private static final long DEFAULT_DELAY = 1000;
    private long outputDelay;
    private String waitingMessage = "Waiting";
    private Timer timer;

    public WaitingFormatter() {
        timer = new Timer();
        outputDelay = DEFAULT_DELAY;
    }

    public void startWaitingOutput() {
        timer.schedule(this, outputDelay, outputDelay);
    }

    public void stopWaitingOutput() {
        timer.cancel();
    }

    public void setOutputDelay(long outputDelay) {
        this.outputDelay = outputDelay;
    }

    public void setWaitingMessage(String waitingMessage) {
        this.waitingMessage = waitingMessage;
    }

    private void pause() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.print(waitingMessage);
        int n = 3;
        for (int i=0; i<n; ++i) {
            pause();
            System.out.print(".");
        }

        for (int i=0; i<=n; ++i) {
            System.out.print("\r");
        }

        pause();
    }

    public static void main(String[] args) {
        WaitingFormatter formatter = new WaitingFormatter();
        formatter.startWaitingOutput();
    }
}
