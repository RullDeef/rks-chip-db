package ru.spacecorp.mobdekbkp.service;


public interface MessagesSendService {
    String NAME = "mobdekbkp_MessagesSendService";

    public void sendMsg(String theme);
}