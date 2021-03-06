/**
 * 
 */
package com.gubs.springTutorial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * @author gubs
 * 
 */
public class SpringJdbc {

	private static final Logger log = Logger.getLogger(SpringJdbc.class);

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings({ "unchecked" })
	public void updateTelemetryCommandJobStatus() {

		String selectSQL = "SELECT RCI_COMMAND_SC_DEVICE_LOOKUP_ID, TELEMETRY_REPORT_DATA_STATUS FROM SMS_RCI_COMMAND_SC_DEVICE_LOOKUP WHERE CUSTOMER_ID = ? "
				+ "AND DEVICE_ZIGBEE_MAC_ID = ? "
				+ "AND TELEMETRY_JOB_STATUS = ? ORDER BY RCI_COMMAND_SC_DEVICE_LOOKUP_ID DESC LIMIT 1";

		Map<String, Object> mapValues = new HashMap<String, Object>();
		try {
			mapValues = (Map<String, Object>) getJdbcTemplate().query(selectSQL,
					new Object[] { 8, "000D6F000180B20E", 7 }, new ResultSetExtractor<Object>() {
						public Object extractData(ResultSet rs) throws SQLException {
							Map<String, Object> map = new HashMap<String, Object>();
							while (rs.next()) {
								int col1 = rs.getInt("RCI_COMMAND_SC_DEVICE_LOOKUP_ID");
								int col2 = rs.getInt("TELEMETRY_REPORT_DATA_STATUS");
								map.put("RCI_COMMAND_SC_DEVICE_LOOKUP_ID", col1);
								map.put("TELEMETRY_REPORT_DATA_STATUS", col2);
							}
							return map;
						};
					});
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.info("Not found");
		}

		if (mapValues.keySet().size() > 0) {
			Integer initialTelemetryStatus = (Integer) mapValues.get("TELEMETRY_REPORT_DATA_STATUS");
			int smsRciCommandJobId = (Integer) mapValues.get("RCI_COMMAND_SC_DEVICE_LOOKUP_ID");

			log.info(initialTelemetryStatus + " " + smsRciCommandJobId);
			initialTelemetryStatus = 1;

			if (initialTelemetryStatus != null) {
				int currentTelemetryStatus = 42;
				int InitialCommandID = (initialTelemetryStatus >>> 16) & 255; // 0x11223344
																				// (always
																				// a
																				// number
																				// between
																				// 0
																				// and
																				// 255)
				int FinalCommandID = (currentTelemetryStatus >>> 16) & 255;

				String updateSQL = "UPDATE SMS_RCI_COMMAND_JOB SET DATE_PROCESSED = ?, TELEMETRY_JOB_STATUS = ? WHERE ID = ?";
				if (InitialCommandID == FinalCommandID) {
					// getJdbcTemplate().update(updateSQL, new Object[] {
					// Calendar.getInstance().getTime(), 4,
					// smsRciCommandJobId });
				} else {
					getJdbcTemplate().update(updateSQL,
							new Object[] { Calendar.getInstance().getTime(), 0, smsRciCommandJobId });
				}
			}
		}

	}
}
