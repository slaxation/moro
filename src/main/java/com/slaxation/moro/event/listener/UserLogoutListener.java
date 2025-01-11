package com.slaxation.moro.event.listener;

import com.slaxation.moro.dto.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserLogoutListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void logoutUserAfterCommit(UserDTO userDTO) {

        SecurityContextHolder.clearContext();
    }

}
