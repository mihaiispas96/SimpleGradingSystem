package com.simple.school.grading.system.SimpleSchoolGradingSystem.session;

import org.hibernate.Session;

/**
 * SessionContext is a singleton class that stores a thread safe instance of a hibernate session object to be used in the application for a particular request
 *
 * @author Ispas Mihai
 */
public class SessionContext {
    /**
     * The singleton instance that is used in the application
     */
    private static SessionContext instance;

    /**
     * An instance of ThreadLocal that ensures thread safety for the session opened for a particular request (it stores an instance of session for each thread that uses it)
     */
    private final ThreadLocal<Session> sessionThreadContext;

    /**
     * <p>
     *     This is a private no argument constructor used for the instantiation of the singleton object
     *          - it instantiates the sessionThreadContext member
     * </p>
     */
    private SessionContext()
    {
        this.sessionThreadContext = new ThreadLocal<>();
    }

    /**
     * <p>
     *     This is a static getter for the singleton instance
     *          - it returns the singleton instance or creates it if it doesn't already exist
     * </p>
     * @return the SessionContext singleton instance
     */
    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    /**
     * <p>
     *     This is a method that gets the Session object that was stored in the ThreadLocal (sessionThreadContext) member to be used by the current thread (associated with each incoming request)
     * </p>
     * @return the Session instance for the current thread
     */
    public Session getCurrentSession()
    {
        return sessionThreadContext.get();
    }

    /**
     * <p>
     *     This is a method that stores a session instance in the ThreadLocal (sessionThreadContext) member to be used by the current thread (associated with each incoming request)
     *     @param session -> the session to be stored in the singleton instance for the current thread
     * </p>
     */
    public void setCurrentSession(final Session session)
    {
        sessionThreadContext.set(session);
    }

    /**
     * <p>
     *     This is a method that detaches the session instance used by the current thread (associated with each incoming request) from the ThreadLocal (sessionThreadContext) member
     * </p>
     */
    public void removeCurrentSession()
    {
        sessionThreadContext.remove();
    }
}
