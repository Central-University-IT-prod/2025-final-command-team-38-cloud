package ru.prodcontest.app.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prodcontest.app.service.interfaces.AdminService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AdminServiceImpl implements AdminService {
    private static final byte[] PASSWORD_HASH = Base64.getDecoder().decode(System.getenv("ADMIN_PASSWORD_HASH"));

    @Override
    public void adminOrException(String adminPass) {
        if (adminPass == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(adminPass.getBytes());
            if (!Arrays.equals(digest.digest(), PASSWORD_HASH)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
