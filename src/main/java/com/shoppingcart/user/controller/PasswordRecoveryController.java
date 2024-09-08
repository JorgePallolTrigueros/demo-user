package com.shoppingcart.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordRecoveryController {

    private boolean isValidUUID(String uuid) {
        // Aquí puedes implementar la lógica para verificar si el UUID es válido (por ejemplo, buscarlo en la base de datos)
        try {
            // Lógica para validar el UUID con la base de datos
            return true; // Cambiar por la validación real
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @GetMapping("/reset-password/{uuid}")
    public String showPasswordResetPage(@PathVariable String uuid, Model model) {
        if (!isValidUUID(uuid)) {
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

        if (!isValidUUID(uuid)) {
            model.addAttribute("error", "El enlace de recuperación no es válido.");
            return "error-page";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("uuid", uuid);
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "reset-password";
        }

        // Lógica para actualizar la contraseña en la base de datos
        // userService.updatePassword(uuid, password);

        redirectAttributes.addFlashAttribute("message", "Contraseña reestablecida exitosamente.");
        return "redirect:/login";  // Redirigir a la página de login
    }

}
