package cn.leither.touchlibiary;

public class Nexus5 {
    Vevent vevent = new Vevent();
    private static int EV_SYN = 0;
    private static int EV_ABS = 3;
    private static int SYN_REPORT = 0;
    private static int ABS_MT_SLOT = 47;
    static int ABS_MT_TOUCH_MAJOR = 48;
    static int ABS_MT_POSITION_X = 53;
    private static int ABS_MT_POSITION_Y = 54;
    private static int ABS_MT_TRACKING_ID = 57;
    static int ABS_MT_PRESSURE = 58;

    public void touch(int finger_index, int x, int y) {
        vevent.sendevent("event1", EV_ABS, ABS_MT_SLOT, finger_index);
        vevent.sendevent("event1", EV_ABS, ABS_MT_TRACKING_ID, finger_index);
        vevent.sendevent("event1", EV_ABS, ABS_MT_POSITION_X, x);
        vevent.sendevent("event1", EV_ABS, ABS_MT_POSITION_Y, y);
        vevent.sendevent("event1", EV_SYN, SYN_REPORT, 0);
    }

    public void release(int finger_index) {
        vevent.sendevent("event1", EV_ABS, ABS_MT_SLOT, finger_index);
        vevent.sendevent("event1", EV_ABS, ABS_MT_TRACKING_ID, -1);
        vevent.sendevent("event1", EV_SYN, SYN_REPORT, 0);
    }

    public void tap(int x, int y){
        try {
            String action = "input tap " + x + " " + y;
            vevent.executeCommand(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void swipe(int x1, int y1, int x2, int y2){
        String action = "input swipe " + x1 + " " + y1 + " " + x2 + " " + y2;
        try {
            vevent.executeCommand(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void input(String text){
       String action = "am broadcast -a ADB_INPUT_TEXT --es msg " + text;
        try {
            vevent.executeCommand(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchIme(){
        String action = "ime set com.dylan_wang.capturescreen/com.leither.service.AdbIME";
        try {
            vevent.executeCommand(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}