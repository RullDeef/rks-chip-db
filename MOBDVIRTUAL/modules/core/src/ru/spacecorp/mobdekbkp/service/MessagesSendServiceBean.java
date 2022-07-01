package ru.spacecorp.mobdekbkp.service;

import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.core.DeviceListProjectApproval;

import javax.inject.Inject;

@Service(MessagesSendService.NAME)
public class MessagesSendServiceBean implements MessagesSendService {

    @Inject
    private DeviceListProjectApproval deviceListProjectApproval;

    @Override
    public void sendMsg(String theme) {
        deviceListProjectApproval.sendMsg(theme);
    }
}