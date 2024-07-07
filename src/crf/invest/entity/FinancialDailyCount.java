/**
 * 
 */
package crf.invest.entity;

import java.util.Date;

import crf.util.DateUtil;

/**
 * <p>Title: Financial</p>
 * <p>Description: 理财师每日统计</p>
 * <p>Company: </p> 
 * @author whb
 * @date 2016-4-18 上午10:25:49
 */
public class FinancialDailyCount {
	private Long daily_id;
	private String daily_phone; //手机号
	private String daily_name;  //理财师姓名
	private String daily_code;  //工号
	private String daily_state; //状态 1禁用 0正常
	private Date daily_create_time;  //创建时间
	private String daily_location;  //所属地区
	private String daily_user_count;  //所邀请用户数
	public Long getDaily_id() {
		return daily_id;
	}
	public void setDaily_id(Long daily_id) {
		this.daily_id = daily_id;
	}
	public String getDaily_phone() {
		return daily_phone;
	}
	public void setDaily_phone(String daily_phone) {
		this.daily_phone = daily_phone;
	}
	public String getDaily_name() {
		return daily_name;
	}
	public void setDaily_name(String daily_name) {
		this.daily_name = daily_name;
	}
	public String getDaily_code() {
		return daily_code;
	}
	public void setDaily_code(String daily_code) {
		this.daily_code = daily_code;
	}
	public String getDaily_state() {
		return daily_state;
	}
	public void setDaily_state(String daily_state) {
		this.daily_state = daily_state;
	}
	public Date getDaily_create_time() {
		return daily_create_time;
	}
	public void setDaily_create_time(Date daily_create_time) {
		this.daily_create_time = daily_create_time;
	}
	public String getDaily_location() {
		return daily_location;
	}
	public void setDaily_location(String daily_location) {
		this.daily_location = daily_location;
	}
	public String getDaily_user_count() {
		return daily_user_count;
	}
	public void setDaily_user_count(String daily_user_count) {
		this.daily_user_count = daily_user_count;
	}
	
	public String getDaily_create_timeStr(){
		return DateUtil.dateToStr(daily_create_time,"yyyy-MM-dd HH:mm:ss");
	}
}
