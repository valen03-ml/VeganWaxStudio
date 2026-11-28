package com.veganwaxstudio.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Interceptor encargado de controlar el acceso según el perfil y el tipo de operación.
@Component
public class AutorizacionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        // Recupera la sesión sin crear una nueva
        HttpSession session = request.getSession(false);

        // Comprueba que el usuario ha iniciado sesión
        if (session == null || session.getAttribute("perfil") == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Es necesario iniciar sesion.");
            return false;
        }

        String perfil = (String) session.getAttribute("perfil");
        String ruta = request.getRequestURI();
        String metodo = request.getMethod();

        // Comprueba los permisos del perfil
        if (!tienePermiso(perfil, ruta, metodo)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tiene permisos para realizar esta operacion.");
            return false;
        }

        return true;
    }

    /**
     * Comprueba los permisos según el perfil,
     * la ruta solicitada y el método HTTP.
     */
    private boolean tienePermiso(String perfil, String ruta, String metodo) {

        // El jefe tiene acceso a todas las funcionalidades
        if ("Jefe".equalsIgnoreCase(perfil)) {
            return true;
        }

        // El encargado puede consultar y gestionar productos,
        // materiales y proveedores, además de registrar ventas.
        if ("Encargado".equalsIgnoreCase(perfil)) {

            if (ruta.startsWith("/api/productos")
                    || ruta.startsWith("/api/categorias-productos")
                    || ruta.startsWith("/api/materiales")
                    || ruta.startsWith("/api/proveedores")) {
                return true;
            }

            if (ruta.startsWith("/api/ventas")) {
                return metodo.equals("GET") || metodo.equals("POST");
            }

            return ruta.startsWith("/api/informes")
                    && metodo.equals("GET");
        }

        // El recepcionista puede consultar productos e informes
        // y registrar ventas.
        if ("Recepcionista".equalsIgnoreCase(perfil)) {

            if (ruta.startsWith("/api/productos")
                    || ruta.startsWith("/api/informes")) {
                return metodo.equals("GET");
            }

            if (ruta.startsWith("/api/ventas")) {
                return metodo.equals("GET") || metodo.equals("POST");
            }
        }

        return false;
    }
}