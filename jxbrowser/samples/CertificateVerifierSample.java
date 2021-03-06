/*
 * Copyright (c) 2000-2016 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to accept/reject SSL certificates using
 * custom SSL certificate verifier.
 */
public class CertificateVerifierSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        NetworkService networkService = browser.getContext().getNetworkService();
        networkService.setCertificateVerifier(new CertificateVerifier() {
            @Override
            public CertificateVerifyResult verify(CertificateVerifyParams params) {
                // Reject SSL certificate for all "google.com" hosts.
                if (params.getHostName().contains("google.com")) {
                    return CertificateVerifyResult.INVALID;
                }
                return CertificateVerifyResult.OK;
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://google.com");
    }
}
