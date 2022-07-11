package com.company.reviews.web.toolkit.ui.client.ratingfield;

import com.vaadin.shared.communication.ServerRpc;

public interface RatingFieldServerRpc extends ServerRpc {
    void starClicked(int value);
}