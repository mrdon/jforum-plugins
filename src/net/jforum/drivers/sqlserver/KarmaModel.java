/*
 * Created on Feb 17, 2005
 * 
 */
package net.jforum.drivers.sqlserver;

import java.util.Date;
import java.util.List;
import net.jforum.util.preferences.SystemGlobals;

/**
 * @author Franklin S. Dattein (<a href="mailto:franklin@hp.com">franklin@hp.com</a>)
 *
 */
public class KarmaModel extends net.jforum.drivers.generic.KarmaModel {

    /* 
     * @see net.jforum.model.KarmaModel#getMostRatedUserByPeriod(java.util.Date, java.util.Date)
     */
    public List getMostRatedUserByPeriod(int start, Date firstPeriod, Date lastPeriod, String orderField) throws Exception {            
        String sql = SystemGlobals.getSql("GenericModel.selectByLimit") + " "+start +" "+ SystemGlobals.getSql("KarmaModel.getMostRatedUserByPeriod");
        String orderby = " ORDER BY "+orderField+" DESC";
        sql += orderby;
        return super.getMostRatedUserByPeriod(sql, start, firstPeriod, lastPeriod, orderField);        	    	    
    }
}
