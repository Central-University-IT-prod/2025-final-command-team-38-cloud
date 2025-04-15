package ru.prodcontest.app.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void adminOrException(String adminPass);
}
