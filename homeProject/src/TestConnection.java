
import java.sql.Connection;
import java.sql.SQLException;
import com.house.framework.commons.utils.DbUtil;

public class TestConnection {
	public static void main(String[] args) {
		Connection dbConn = null;
		try {
			dbConn = DbUtil.getConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(dbConn!=null)
					dbConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
