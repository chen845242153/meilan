package com.meilancycling.mema.ble.command;

import com.meilancycling.mema.ble.bean.CommandEntity;
import com.meilancycling.mema.ble.computer.ComputerConnectImp;

import java.nio.charset.StandardCharsets;

/**
 * 文件通道指令
 *
 * @author sorelion qq 571135591
 */
public class FileCommandManager {
    private static FileCommandManager mFileCommandManager;

    public static synchronized FileCommandManager getInstance() {
        if (mFileCommandManager == null) {
            mFileCommandManager = new FileCommandManager();
        }
        return mFileCommandManager;
    }

    /**
     * 获取历史数据(新数据)
     */
    public CommandEntity getHistoricalData() {
        /*
         * 文件协议公共长度
         * 起始符
         * 数据长度
         * 数据段命令字（序列号 命令）
         * 结束符
         */
        if (ComputerConnectImp.fileNumber == 15) {
            ComputerConnectImp.fileNumber = 0;
        } else {
            ComputerConnectImp.fileNumber = ComputerConnectImp.fileNumber + 1;
        }
        int commonLength = 4;
        byte[] bytes = new byte[commonLength + 1];
        bytes[0] = (byte) 0x01;
        bytes[1] = (byte) 0x02;
        bytes[2] = (byte) (((ComputerConnectImp.fileNumber << 4) + 1) & 0Xff);
        bytes[3] = (byte) 0x01;
        bytes[4] = (byte) 0x04;
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        return commandEntity;
    }

    /**
     * 删除历史数据
     */
    public CommandEntity deleteHistoricalData(String fileName) {
        if (ComputerConnectImp.fileNumber == 15) {
            ComputerConnectImp.fileNumber = 0;
        } else {
            ComputerConnectImp.fileNumber = ComputerConnectImp.fileNumber + 1;
        }
        //文件名要加/0
        byte[] data = fileName.getBytes(StandardCharsets.UTF_8);

        byte[] bytes = new byte[data.length + 7];
        bytes[0] = (byte) 0x01;
        bytes[1] = (byte) ((data.length + 4) & 0Xff);
        bytes[2] = (byte) (((ComputerConnectImp.fileNumber << 4) + 2) & 0Xff);
        bytes[3] = (byte) 0x00;
        bytes[4] = (byte) ((data.length + 1) & 0Xff);
        System.arraycopy(data, 0, bytes, 5, data.length);
        bytes[data.length + 5] = 0;
        bytes[data.length + 6] = (byte) 0x04;
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        return commandEntity;
    }

    /**
     * 历史数据应答
     */
    public CommandEntity historicalDataResponse(int number) {
        byte[] bytes = new byte[6];
        bytes[0] = (byte) 0x01;
        bytes[1] = (byte) 0x03;
        bytes[2] = (byte) 0x00;
        bytes[3] = (byte) 0x00;
        bytes[4] = (byte) (number & 0Xff);
        bytes[5] = (byte) 0x04;
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setData(bytes);
        return commandEntity;
    }


}
