package com.meilancycling.mema.ble.command;

import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.computer.ComputerConnectImp;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 码表设置通道指令
 *
 * @author sorelion qq 571135591
 */
public class BleCommandManager {
    private static BleCommandManager mBleCommandManager;

    public static synchronized BleCommandManager getInstance() {
        if (mBleCommandManager == null) {
            mBleCommandManager = new BleCommandManager();
        }
        return mBleCommandManager;
    }

    /**
     * 获取设备信息
     */
    public CommandEntity getDeviceInformation() {
        CommandEntity commandEntity = new CommandEntity();
        byte[] bytes = new byte[2];
        bytes[0] = (byte) 0x01;
        commandEntity.setData(bytes);
        return commandEntity;
    }

    /**
     * 进入ota
     */
    public CommandEntity initOta() {
        CommandEntity commandEntity = new CommandEntity();
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (2 & 0Xff);
        commandEntity.setData(bytes);
        return commandEntity;
    }

    /**
     * 应答命令
     */
    public byte[] answerCommand(int command) {
        byte[] bytes = new byte[3];
        bytes[0] = (byte) 0x3D;
        bytes[1] = (byte) 0x01;
        bytes[2] = (byte) (command & 0xff);
        return bytes;
    }

    /**
     * 语言设置
     */
    public CommandEntity languageSettings(int language) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) 0x01;
        bytes[1] = (byte) (language & 0xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(1);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * @param timeFormat 0 12小时制
     *                   1 24小时制
     */
    public CommandEntity timeFormat(int timeFormat) {
        byte[] bytes = new byte[1];
        if (timeFormat == 0) {
            bytes[0] = (byte) (2 & 0xff);
        } else {
            bytes[0] = (byte) ((2 + 128) & 0xff);
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(2);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 整体单位设置
     *
     * @param unit 0 公制
     *             1 英制
     */
    public CommandEntity unitSetting(int unit, int unitType) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) 0x03;
        //仅整体设置
        if (unitType == 0) {
            bytes[1] = (byte) (unit & 0xff);
        } else {
            //单独设置
            bytes[1] = (byte) (unit * 16 + unit * 8 + unit * 4 + unit * 2 + unit);
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(3);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 声音设置
     *
     * @param soundSwitch 0 关
     *                    1 开
     */
    public CommandEntity soundSettings(int soundSwitch) {
        byte[] bytes = new byte[1];
        if (soundSwitch == 0) {
            bytes[0] = (byte) (4 & 0xff);
        } else {
            bytes[0] = (byte) ((4 + 128) & 0xff);
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(4);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 消息提醒
     *
     * @param messageSwitch 0 关
     *                      1 开
     */
    public CommandEntity messageReminderSwitch(int messageSwitch) {
        byte[] bytes = new byte[1];
        if (messageSwitch == 0) {
            bytes[0] = (byte) (5 & 0xff);
        } else {
            bytes[0] = (byte) ((5 + 128) & 0xff);
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(5);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * @param isOpen 0 关闭
     *               1 打开
     * @param type   警告项目
     *               1 时间
     *               2 距离
     *               3 最大速度
     *               4 最大踏频
     *               5 最大心率
     *               6 最大功率
     * @param value  警告值
     */
    public CommandEntity warningReminder(int isOpen, int type, int value) {
        byte[] bytes = new byte[6];
        bytes[0] = (byte) 0x06;
        bytes[1] = (byte) ((isOpen * 128 + type) & 0xff);
        bytes[2] = (byte) ((value >> 24) & 0Xff);
        bytes[3] = (byte) ((value >> 16) & 0Xff);
        bytes[4] = (byte) ((value >> 8) & 0Xff);
        bytes[5] = (byte) (value & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(6);
        commandEntity.setType(type);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * odo设置
     *
     * @param odo 单位m
     */
    public CommandEntity odoSettings(long odo) {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) 0x07;
        bytes[1] = (byte) ((odo >> 24) & 0Xff);
        bytes[2] = (byte) ((odo >> 16) & 0Xff);
        bytes[3] = (byte) ((odo >> 8) & 0Xff);
        bytes[4] = (byte) (odo & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(7);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 海拔校正
     *
     * @param altitude 单位0.01
     */
    public CommandEntity altitudeCorrection(long altitude) {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) 0x08;
        bytes[1] = (byte) ((altitude >> 24) & 0Xff);
        bytes[2] = (byte) ((altitude >> 16) & 0Xff);
        bytes[3] = (byte) ((altitude >> 8) & 0Xff);
        bytes[4] = (byte) (altitude & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(8);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 心率设置
     *
     * @param mode  模式
     *              0 通用
     *              1 跑步
     *              2 骑行
     *              3 保留
     * @param drill 训练方法
     *              0 按最大心率
     *              1 按储备心率
     */
    public CommandEntity hrSetting(int mode, int drill, int maxHeart, int restingHeart, int data1, int data2, int data3, int data4, int data5) {
        byte[] bytes = new byte[10];
        bytes[0] = (byte) 0x09;
        bytes[1] = (byte) ((mode << 6) | (drill << 5));
        bytes[2] = (byte) (maxHeart & 0Xff);
        bytes[3] = (byte) (restingHeart & 0Xff);
        bytes[5] = (byte) (data1 & 0Xff);
        bytes[6] = (byte) (data2 & 0Xff);
        bytes[7] = (byte) (data3 & 0Xff);
        bytes[8] = (byte) (data4 & 0Xff);
        bytes[9] = (byte) (data5 & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(9);
        commandEntity.setType((mode << 6) | (drill << 5));
        commandEntity.setData(bytes);
        commandEntity.setCommandType(drill);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 功率设置
     */
    public CommandEntity powerSettings(int data, int data1, int data2, int data3, int data4, int data5, int data6, int data7) {
        byte[] bytes = new byte[17];
        bytes[0] = (byte) 0x0A;
        bytes[2] = (byte) ((data >> 8) & 0X0f);
        bytes[3] = (byte) (data & 0Xff);
        bytes[6] = (byte) ((data1 >> 4) & 0Xff);
        bytes[7] = (byte) (((data1 & 0X0f) << 4) | ((data2 >> 8) & 0X0f));
        bytes[8] = (byte) (data2 & 0Xff);
        bytes[9] = (byte) ((data3 >> 4) & 0Xff);
        bytes[10] = (byte) (((data3 & 0X0f) << 4) | ((data4 >> 8) & 0X0f));
        bytes[11] = (byte) (data4 & 0Xff);
        bytes[12] = (byte) ((data5 >> 4) & 0Xff);
        bytes[13] = (byte) (((data5 & 0X0f) << 4) | ((data6 >> 8) & 0X0f));
        bytes[14] = (byte) (data6 & 0Xff);
        bytes[15] = (byte) ((data7 >> 4) & 0Xff);
        bytes[16] = (byte) (((data7 & 0X0f) << 4) & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(10);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 用户信息
     *
     * @param gender 0 保密
     *               1 男
     *               2 女
     * @param height 0.01cm
     * @param weight 0.01kg
     */
    public CommandEntity userInfo(int gender, int height, int weight, int year, int mouth, int day) {
        byte[] bytes = new byte[10];
        bytes[0] = (byte) (0x0B & 0Xff);
        bytes[1] = (byte) (gender << 6);
        bytes[2] = (byte) ((height >> 8) & 0Xff);
        bytes[3] = (byte) (height & 0Xff);
        bytes[4] = (byte) ((weight >> 8) & 0Xff);
        bytes[5] = (byte) (weight & 0Xff);
        bytes[6] = (byte) ((year >> 8) & 0Xff);
        bytes[7] = (byte) (year & 0Xff);
        bytes[8] = (byte) (mouth & 0Xff);
        bytes[9] = (byte) (day & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(11);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 当前时间
     */
    public CommandEntity currentTime(long currentTime) {
        byte[] bytes = new byte[6];
        int timeZone = 0;
        switch (getTimeZone()) {
            case -12:
                timeZone = 26;
                break;
            case -11:
                timeZone = 27;
                break;
            case -10:
                timeZone = 28;
                break;
            case -9:
                timeZone = 30;
                break;
            case -8:
                timeZone = 31;
                break;
            case -7:
                timeZone = 32;
                break;
            case -6:
                timeZone = 33;
                break;
            case -5:
                timeZone = 34;
                break;
            case -4:
                timeZone = 36;
                break;
            case -3:
                timeZone = 38;
                break;
            case -2:
                timeZone = 39;
                break;
            case -1:
                timeZone = 40;
                break;
            case 0:
                timeZone = 0;
                break;
            case 1:
                timeZone = 1;
                break;
            case 2:
                timeZone = 2;
                break;
            case 3:
                timeZone = 3;
                break;
            case 4:
                timeZone = 5;
                break;

            case 5:
                timeZone = 7;
                break;
            case 6:
                timeZone = 10;
                break;
            case 7:
                timeZone = 12;
                break;
            case 8:
                timeZone = 13;
                break;
            case 9:
                timeZone = 16;
                break;
            case 10:
                timeZone = 18;
                break;
            case 11:
                timeZone = 20;
                break;
            case 12:
                timeZone = 22;
                break;
            case 13:
                timeZone = 24;
                break;
            case 14:
                timeZone = 25;
                break;
            default:
        }
        bytes[0] = (byte) (0x0C & 0Xff);
        bytes[1] = (byte) ((currentTime >> 24) & 0xff);
        bytes[2] = (byte) ((currentTime >> 16) & 0xff);
        bytes[3] = (byte) ((currentTime >> 8) & 0xff);
        bytes[4] = (byte) (currentTime & 0xff);
        bytes[5] = (byte) (timeZone & 0xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(12);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SETTING_COMMAND);
        return commandEntity;
    }

    /**
     * 获取时区
     */
    private int getTimeZone() {
        Calendar cal = Calendar.getInstance();
        int offset = cal.get(Calendar.ZONE_OFFSET);
        cal.add(Calendar.MILLISECOND, -offset);
        Long timeStampUTC = cal.getTimeInMillis();
        Long timeStamp = (timeStampUTC + TimeZone.getDefault().getOffset(timeStampUTC));
        Long timeZone = (timeStamp - timeStampUTC) / (1000 * 3600);
        return Integer.parseInt(String.valueOf(timeZone));
    }


    /**
     * 设置传感器名字
     */
    public CommandEntity setSensorName(int deviceId, String name) {
        byte[] nameBytes = name.trim().getBytes(StandardCharsets.UTF_8);
        /*
         * cmdId+设置项+设置参数+字符串结束（/0）
         */
        int number = 4;
        byte[] bytes = new byte[number + nameBytes.length];
        bytes[0] = 0x03;
        bytes[1] = (byte) ((nameBytes.length + 2 + 128) & 0Xff);
        bytes[2] = (byte) (deviceId & 0Xff);
        for (int i = 0; i < nameBytes.length; i++) {
            bytes[3 + i] = nameBytes[i];
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(3);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SENSOR_COMMAND);
        return commandEntity;

    }

    /**
     * 设置传感器轮径值
     */
    public CommandEntity SetSensorValue(int deviceId, int value) {
        byte[] bytes = new byte[7];
        bytes[0] = 0x03;
        bytes[1] = (byte) (5 & 0Xff);
        bytes[2] = (byte) (deviceId & 0Xff);
        bytes[3] = (byte) ((value >> 24) & 0Xff);
        bytes[4] = (byte) ((value >> 16) & 0Xff);
        bytes[5] = (byte) ((value >> 8) & 0Xff);
        bytes[6] = (byte) (value & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setCommandId(3);
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SENSOR_COMMAND);
        return commandEntity;
    }

    /**
     * 消息通知
     */
    public CommandEntity sendNotificationMessage(int type, String title, String content) {
        byte[] titleByte = title.getBytes(StandardCharsets.UTF_8);
        byte[] totalByte = (title + content).getBytes(StandardCharsets.UTF_8);
        //  序列号 命令号2 最多5包
        int packetHeader = 10;
        //  类型1    时分2  总长度1  标题长度1
        int defaultLength = 5;
        int maxLength = 99;

        int byteSize = totalByte.length + defaultLength + packetHeader;
        if (byteSize > maxLength) {
            byteSize = maxLength - packetHeader;
        } else {
            byteSize = totalByte.length + defaultLength;
        }
        byte[] messageByte = new byte[byteSize];
        messageByte[0] = (byte) (type & 0xff);
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        messageByte[1] = (byte) (hour & 0Xff);
        messageByte[2] = (byte) (minute & 0Xff);
        messageByte[3] = (byte) ((byteSize - defaultLength) & 0Xff);

        if (titleByte.length > byteSize - defaultLength) {
            messageByte[4] = (byte) ((byteSize - defaultLength) & 0Xff);
        } else {
            messageByte[4] = (byte) (titleByte.length & 0Xff);
        }
        for (int i = 5; i < byteSize; i++) {
            messageByte[i] = totalByte[i - 5];
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(messageByte);
        commandEntity.setCommandType(ComputerConnectImp.INFORMATION_COMMAND);
        return commandEntity;
    }

    /**
     * 挂电话
     */
    public CommandEntity hookUp() {
        byte[] bytes = new byte[5];
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        bytes[0] = (byte) (11 & 0Xff);
        bytes[1] = (byte) (((hour * 60 + minute) >> 8) & 0Xff);
        bytes[2] = (byte) ((hour * 60 + minute) & 0Xff);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.INFORMATION_COMMAND);
        return commandEntity;
    }


    /**
     * 搜索传感器
     */
    public CommandEntity searchSensor() {
        byte[] bytes = new byte[1];
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SENSOR_COMMAND);
        return commandEntity;
    }

    /**
     * 添加传感器
     *
     * @param mList 标识符集合
     */
    public CommandEntity addSensor(List<Integer> mList) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) 0x01;
        if (null != mList && mList.size() > 0) {
            int a = 0;
            for (Integer integer : mList) {
                a = a | (1 << integer);
            }
            bytes[1] = (byte) (a & 0Xff);
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SENSOR_COMMAND);
        return commandEntity;
    }

    /**
     * 删除传感器
     */
    public CommandEntity deleteSensor(List<Integer> mList) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) 0x02;
        int a = 0;
        if (null != mList && mList.size() > 0) {
            for (Integer integer : mList) {
                a = a | (1 << integer);
            }
            bytes[1] = (byte) (a >> 16 & 0Xff);
            bytes[2] = (byte) (a >> 8 & 0Xff);
            bytes[3] = (byte) (a & 0Xff);
        }
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        commandEntity.setCommandType(ComputerConnectImp.SENSOR_COMMAND);
        return commandEntity;
    }
}
