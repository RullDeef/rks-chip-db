package ru.iovchinnikov.notificationsusers.service;

import com.haulmont.cuba.core.entity.FileDescriptor;

public interface MessageService {
    String NAME = "notificationsusers_MessageService";

    void send(String senderLogin, String receiverLogin, String subject, String text, boolean sent);
    void send(String senderLogin, String receiverLogin, String subject, String text, boolean sent, FileDescriptor file);

}