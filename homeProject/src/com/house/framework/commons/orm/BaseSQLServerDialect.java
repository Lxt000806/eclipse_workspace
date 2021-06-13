package com.house.framework.commons.orm;

import java.sql.Types;
import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServer2008Dialect;

public class BaseSQLServerDialect extends SQLServer2008Dialect {

    @SuppressWarnings("deprecation")
	public BaseSQLServerDialect() {  
        super();  
        registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
        registerHibernateType(Types.NCHAR, Hibernate.STRING.getName());
        registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());  
        registerHibernateType(Types.LONGNVARCHAR, Hibernate.STRING.getName());  
        registerHibernateType(Types.DECIMAL, Hibernate.DOUBLE.getName());  
    } 
    
    @Override
	public String getLimitString(String querySqlString, boolean hasOffset) {
		String result = super.getLimitString(querySqlString, hasOffset);
		result =  result + " order by __hibernate_row_nr__ ";
		return result;
	}
}
