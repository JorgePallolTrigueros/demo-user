package com.shoppingcart.user.controller;

import com.shoppingcart.user.service.password.PasswordRecoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoverService passwordRecoverService;


    @GetMapping("/reset-password/{uuid}")
    public String showPasswordResetPage(@PathVariable String uuid, Model model) {
        if (!passwordRecoverService.tokenIsValid(uuid)) {
            model.addAttribute("error", "El enlace de recuperación no es válido.");
            return "error-page";  // Mostrar una página de error en caso de UUID inválido
        }
        model.addAttribute("uuid", uuid);
        return "reset-password";
    }

    @PostMapping("/reset-password/{uuid}")
    public String handlePasswordReset(
            @PathVariable String uuid,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (!passwordRecoverService.tokenIsValid(uuid)) {
            model.addAttribute("error", "El enlace de recuperación no es válido.");
            return "error-page";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("uuid", uuid);
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "reset-password";
        }


        try {
            passwordRecoverService.changePassword(uuid,password,confirmPassword);
        }catch (final Exception ex){
            model.addAttribute("error", "Ocurrio un error al cambiar la contraseña: "+ex.getMessage());
            return "error-page";
        }

        redirectAttributes.addFlashAttribute("message", "Contraseña reestablecida exitosamente.");
        return "password-changed";  // Redirigir a la página de login
    }

}
