package com.Atos.Webaccess.Approve.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateSessionFactorySignature {

    private static final ThreadLocal<Session> THREAD_LOCAL =
            new ThreadLocal<>();

    private static final Configuration CONFIGURATION =
            new Configuration();

    private static final ConfigHibernateSIBSignature CONFIG_APP =
            new ConfigHibernateSIBSignature();

    private static volatile SessionFactory sessionFactory;

    private HibernateSessionFactorySignature() {
        // Utility class
    }

    public static Session currentSession() throws HibernateException {

        Session session = THREAD_LOCAL.get();

        if (session != null && session.isOpen()) {
            return session;
        }

        THREAD_LOCAL.remove();

        SessionFactory factory = getSessionFactory();

        session = factory.openSession();
        THREAD_LOCAL.set(session);

        return session;
    }

    private static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            synchronized (HibernateSessionFactorySignature.class) {

                if (sessionFactory == null) {
                    try {
                        CONFIG_APP.getConfig(CONFIGURATION);

                        sessionFactory = CONFIGURATION.buildSessionFactory();

                    } catch (Exception e) {
                        throw new IllegalStateException(
                            "Failed to initialize Signature SessionFactory.",
                            e
                        );
                    }
                }
            }
        }

        return sessionFactory;
    }

    public static void closeSession() throws HibernateException {

        Session session = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();

        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }
}
