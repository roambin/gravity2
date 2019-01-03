package token;

/**
 * 全局配置常量
 * @author XY
 *
 */
public class Constant {
	
	/**
	 * 数据请求返回码
	 */
	/**
	 * 成功
	 */
	public static final int RESCODE_SUCCESS = 1000;				//成功
	public static final int RESCODE_SUCCESS_DATA = 1001;		//成功(有返回数据)
	public static final int RESCODE_NOEXIST = 1004;				//查询结果为空
	/**
	 * 失败
	 */
	public static final int RESCODE_EXCEPTION = 1002;			//请求抛出异常
	public static final int RESCODE_EXCEPTION_DATA = 1008;		//异常带数据
	public static final int RESCODE_NOLOGIN = 1003;				//未登陆状态
	public static final int RESCODE_NOAUTH = 1005;				//无操作权限
	public static final int RESCODE_LOGINEXPIRE = 1009;			//登录过期
	/**
	 * token（暂时没有刷新自动token机制，通过重新登录获取）
	 */
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
	public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN
	
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

	/**
	 * jwt
	 */
	public static final String JWT_ID = "5236A";										//jwtid
	public static final String JWT_SECERT = "7786sp7fc3a55826a61c034d5ec82r8a";			//密匙
	public static final long JWT_TTL = 24*60*60*1000;									//token有效时间
	
}
