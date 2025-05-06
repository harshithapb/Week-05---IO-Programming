package org.example.custom.advance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 1. Define the @RoleAllowed annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RoleAllowed {
    String value(); // Role name (e.g., "ADMIN", "USER")
}

interface AdminOperations {
    @RoleAllowed("ADMIN")
    void performAdminTask();

    void viewPublicInfo();
}

class AdminService implements AdminOperations {
    @Override
    public void performAdminTask() {
        System.out.println("Admin task performed.");
    }

    @Override
    public void viewPublicInfo() {
        System.out.println("Viewing public information.");
    }
}

class AuthorizationInvocationHandler implements InvocationHandler {
    private final Object target;
    private final String currentUserRole;

    public AuthorizationInvocationHandler(Object target, String currentUserRole) {
        this.target = target;
        this.currentUserRole = currentUserRole;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(RoleAllowed.class)) {
            RoleAllowed roleAllowedAnnotation = method.getAnnotation(RoleAllowed.class);
            String allowedRole = roleAllowedAnnotation.value();
            if (currentUserRole.equals(allowedRole)) {
                System.out.println("Authorization check passed for role: " + currentUserRole);
                return method.invoke(target, args);
            } else {
                System.out.println("Access Denied! User with role '" + currentUserRole +
                        "' is not allowed to access method '" + method.getName() + "'. Required role: " + allowedRole);
                return null; // Or throw an exception
            }
        } else {
            // No role restriction, allow access
            return method.invoke(target, args);
        }
    }
}

public class RoleBasedAccessControlExample {
    public static void main(String[] args) {
        AdminService adminService = new AdminService();

        // Simulate an ADMIN user
        AdminOperations adminUserProxy = (AdminOperations) Proxy.newProxyInstance(
                AdminService.class.getClassLoader(),
                new Class[]{AdminOperations.class},
                new AuthorizationInvocationHandler(adminService, "ADMIN")
        );

        System.out.println("--- ADMIN User Actions ---");
        adminUserProxy.performAdminTask();
        adminUserProxy.viewPublicInfo();

        System.out.println("\n--- REGULAR User Actions ---");
        // Simulate a REGULAR user
        AdminOperations regularUserProxy = (AdminOperations) Proxy.newProxyInstance(
                AdminService.class.getClassLoader(),
                new Class[]{AdminOperations.class},
                new AuthorizationInvocationHandler(adminService, "REGULAR")
        );

        regularUserProxy.performAdminTask();
        regularUserProxy.viewPublicInfo();
    }
}