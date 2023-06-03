package com.discussion.csye6220.dao;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {

    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> sessionThread = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().setProperty("hibernate.connection.url",System.getenv("hibernate_connection_url")).setProperty("hibernate.connection.password",System.getenv("hibernate_connection_password")).setProperty("hibernate.connection.username", System.getenv("hibernate_connection_username")).buildSessionFactory();
    

    protected DAO() {
    }

    public static Session getSession() {
        Session session = (Session) DAO.sessionThread.get();

        if (session == null) {
            session = sessionFactory.openSession();
            DAO.sessionThread.set(session);
        }
        return session;
    }

    protected void begin() {
    	Session session = getSession();
    	System.out.println("Beginning of Transaction with Session  " + session.hashCode());
        session.beginTransaction();
    }

    protected void commit() {
    	Session session = getSession();
    	System.out.println("About to commit Transaction with Session  " + session.hashCode());
        getSession().getTransaction().commit();
    }

    protected void rollback() {
        try {
        	Session session = getSession();
        	System.out.println("About to Rollback Transaction with Session  " + session.hashCode());
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot rollback", e);
        }
        try {
        	Session session = getSession();
        	System.out.println("About to close session in Rollback  " + session.hashCode());
            getSession().close();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot close", e);
        }
        DAO.sessionThread.set(null);
    }

    public static void close() {
    	Session session = getSession();
    	System.out.println("About to close session  " + session.hashCode());
        getSession().close();
        DAO.sessionThread.set(null);
    }
}
