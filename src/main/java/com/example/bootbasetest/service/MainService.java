package com.example.bootbasetest.service;

import com.example.bootbasetest.dto.*;
import com.example.bootbasetest.event.MessgeSendEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Component
public class MainService {
    private final ApplicationEventPublisher publisher;

    public MainService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void handler(TerminalData data) {
        List<DataModule> list = new ArrayList<>();
        FormatData formatData = new FormatData();
        formatData.setName("APPO");
        DataModule dataModule1 = new DataModule();
        dataModule1.setMName1("LD");
        dataModule1.setValue1(data.getDistanceLeft().toString());
        dataModule1.setUnit1("M");

        DataModule dataModule2 = new DataModule();
        dataModule2.setMName1("RD");
        dataModule2.setValue1(data.getDistanceRight().toString());
        dataModule2.setUnit1("M");

        DataModule dataModule3 = new DataModule();
        dataModule3.setMName1("LS");
        dataModule3.setValue1(data.getSpeedLeft().toString());
        dataModule3.setUnit1("M");

        DataModule dataModule4 = new DataModule();
        dataModule4.setMName1("RS");
        dataModule4.setValue1(data.getSpeedRight().toString());
        dataModule4.setUnit1("M");

        DataModule dataModule5 = new DataModule();
        dataModule5.setMName1("AG");
        dataModule5.setValue1(data.getAngle().toString());
        //todo
        dataModule5.setUnit1("A");
        list.add(dataModule1);
        list.add(dataModule2);
        list.add(dataModule3);
        list.add(dataModule4);
        list.add(dataModule5);
        formatData.setDataModules(list);
        fromatting(formatData);
    }
    public void handler(HookStationData data) {
        FormatData formatData = new FormatData();
        formatData.setName("HOOK");
        List<DataModule> list = new ArrayList<>();
        for (int i = 0; i < data.getHooks().size(); i++) {
            HookData hookData = data.getHooks().get(i);
            DataModule dataModule1 = new DataModule();
            dataModule1.setMName1((i + 1)+"H");
            dataModule1.setValue1(hookData.getValue().toString());
            dataModule1.setUnit1("T");
            list.add(dataModule1);
        }
        formatData.setDataModules(list);
        fromatting(formatData);
    }

    public void fromatting(FormatData data) {
        final String format = "501 A A \"V\"\n";
        final String dataFormat1 = "NNNN:MMXXXXU0HHYYYYP";
        final String dataFormat = "@#$";
        StringBuilder output = new StringBuilder();

        String name = data.getName();
        //如果name长度不等于4，如果长于4则截取前4位,如果小于4则补位空格,并大写
        if (name.length() > 4) {
            name = data.getName().substring(0, 4).toUpperCase();
        } else {
            name = String.format("%-4s", data.getName()).toUpperCase();
        }
        name = name + ":";
        output.append(name);
        for (int i = 0; i < data.getDataModules().size(); i++) {
            DataModule dataModule = data.getDataModules().get(i);
            String mName1 = dataModule.getMName1();
            String value1 = dataModule.getValue1();
            String unit1 = dataModule.getUnit1();
            output.append(dataFormat);

            if (i % 2 == 1) { // 每两次拼接一次
                output.append(name);
            }
            //如果mName1长度不等于2，如果长于2则截取前2位,如果小于2则补位空格,并大写
            mName1 = manipulateString(mName1, 2).toUpperCase();
            //如果value1长度不等于4，如果长于4则截取前4位,如果小于3则在前面补位下划线
            value1 = manipulateString(value1, 4).toUpperCase();
            //如果unit1长度不等于1，如果长于1则截取前1位,如果小于1则填充空格,并大写
            unit1 = manipulateString(unit1, 1).toUpperCase();
            output = new StringBuilder(output.toString().replace("@", mName1));
            output = new StringBuilder(output.toString().replace("#", value1));
            output = new StringBuilder(output.toString().replace("$", unit1));

            //如果是单数次循环,在后面拼接一个空格
            if (i % 2 == 0) {
                output.append("_");
            }
        }

        output = new StringBuilder(format.replace("V", output.toString()));
        sendMessage(output.toString());
    }
    public void sendMessage(String message) {
        StringDto stringDto = new StringDto();
        stringDto.setString(message);
        publisher.publishEvent(new MessgeSendEvent(this, stringDto));
    }
    public static String manipulateString(String input,int length) {
        if (input.length() < length) {
            // 字符串长度小于4，前面补充下划线
            while (input.length() < length) {
                input = "_" + input;
            }
        } else if (input.length() > length) {
            // 字符串长度大于4，截取前4位
            input = input.substring(0, length);
        }
        return input;
    }
}
