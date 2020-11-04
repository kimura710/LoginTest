package com.example.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entity.LoginUser;

@Repository
public class LoginUserDao {
	
	@Autowired
	EntityManager em;
	
	public LoginUser findUser(String userName) {
		String query = "";
		query += "select * ";
		query += "from user";
		query += "where username = :userName"; //setParameterで引数の値を代入できるようにNamedParameterを利用
		
		return (LoginUser)em.createNativeQuery(query,LoginUser.class).setParameter("userName", userName).getSingleResult();
	}

}
