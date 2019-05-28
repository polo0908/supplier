package com.cbt.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class DbPoolUtil {
	private static final Log LOG = LogFactory.getLog(DbPoolUtil.class);
	public static ComboPooledDataSource pool = null;

	public static void init() {
		try {
//			long st = System.currentTimeMillis();
			destory();
			LOG.info("initialize the database connection pool begin");
			synchronized (DbPoolUtil.class) {
				InputStream ins = DbPoolUtil.class.getResourceAsStream("/jdbc.properties");
				Properties p = new Properties();
				try {
					p.load(ins);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pool = new ComboPooledDataSource();
				pool.setDriverClass(p.getProperty("driver"));
				pool.setJdbcUrl(p.getProperty("url"));
				pool.setUser(p.getProperty("username"));
				pool.setPassword(p.getProperty("password"));
				pool.setMaxIdleTime(300);
				pool.setIdleConnectionTestPeriod(1800);
				pool.setAcquireIncrement(3);
				pool.setMaxPoolSize(300);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 销毁
	 */
	public static void destory() {
		try {
			if (pool != null) {
				DataSources.destroy(pool);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			if (pool == null) {
				init();
			}
			return pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void returnConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
