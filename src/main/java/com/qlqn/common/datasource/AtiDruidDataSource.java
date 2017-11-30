package com.qlqn.common.datasource;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @classname AtiDruidDataSource
 * @version
 */
@Repository
public class AtiDruidDataSource extends DruidDataSource {

	private static final long serialVersionUID = -6230722754420646348L;

	private String username;

    private String password;

    private AtiDruidDataSource(){}

    private static AtiDruidDataSource atiDruidDataSource = new AtiDruidDataSource();

    public static AtiDruidDataSource getInstance() {
        return atiDruidDataSource;
    }


    @Override
    public void setUsername(String username) {
        try {
            this.username = username;
            super.setUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setPassword(String password) {
        try {
            this.password = password;
            super.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
