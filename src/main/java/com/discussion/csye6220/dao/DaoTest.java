package com.discussion.csye6220.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Configuration cfg = new Configuration();
	     SessionFactory sf = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
	}

}
