/**
 *
 */
package com.swsk.web.util;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cglib.beans.BeanMap;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author y.z
 */
public class Ret {
	private static RetDTO ret;

	/**
	 * 数据获取成功
	 */
	public static String  msgSuccess(){
		ret = new RetDTO();
		ret.setCode(Const.SUCCESS_CODE);
		ret.setMsg(Const.SUCCESS_MSG);
		return getJson(ret);
	}

	/**
	 * 数据获取成功
	 * @param msg
	 * @param mapData
	 */
	public static String msgSuccess(String msg,Map<Object, Object> mapData){
		ret = new RetDTO();
		ret.setCode(Const.SUCCESS_CODE);
		ret.setMsg(msg);
		ret.setData(mapData);
		return getJson(ret);
	}

	/**
	 * 数据获取成功
	 * @param mapData
	 */
	public static String msgSuccess(Map<Object, Object> mapData){
		ret = new RetDTO();
		ret.setCode(Const.SUCCESS_CODE);
		ret.setMsg(Const.SUCCESS_MSG);
		ret.setData(mapData);
		return getJson(ret);
	}

	/**
	 * 数据获取成功
	 * @param object
	 */
	public static String msgSuccess(Object object){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", Const.SUCCESS_CODE);
		ret.put("msg", Const.SUCCESS_MSG);
		ret.put("data",object);
		return getJson(ret);
	}
	/**
	 * 数据获取成功
	 * @param msg
	 */
	public static String setSuccessMsg(String msg){
		Map<String, Object> ret = new HashMap<>();
		ret.put("code", Const.SUCCESS_CODE);
		ret.put("msg",msg);
		return getJson(ret);
	}


	public static String setJqGrid(Object data,int total,int records){
		Map<String, Object> ret = new HashMap<>();
		ret.put("rows",data);
		ret.put("total",total);
		ret.put("records",records);
		return getJson(ret);
	}

	public static String setJqGrid(Object data){
		Map<String, Object> ret = new HashMap<>();
		ret.put("rows",data);
		return getJson(ret);
	}



	/**
	 * 添加成功
	 */
	public static String msgAdd(){
		ret = new RetDTO();
		ret.setCode(Const.SUCCESS_CODE);
		ret.setMsg(Const.ADD_SUCCESS_MSG);
		return getJson(ret);
	}

	/**
	 * 更新成功
	 */
	public static String msgUpdate(){
		ret = new RetDTO();
		ret.setCode(Const.SUCCESS_CODE);
		ret.setMsg(Const.UPDATE_SUCCESS_MSG);
		return getJson(ret);
	}

	/**
	 * 删除成功
	 */
	public static String msgDel(){
		ret = new RetDTO();
		ret.setCode(Const.SUCCESS_CODE);
		ret.setMsg(Const.DEL_SUCCESS_MSG);
		return getJson(ret);
	}

	/**
	 * 添加失败
	 */
	public static String msgAddFail(){
		return msgSetVal("添加失败");
	}

	/**
	 * 更新失败
	 */
	public static String msgUpdateFail(){
		return msgSetVal("更新失败");
	}

	/**
	 * 删除失败
	 */
	public static String msgDelFail(){
		return msgSetVal("删除失败");
	}

	/**
	 * 系统错误
	 */
	public static String msgSystemErr(){
		ret = new RetDTO();
		ret.setCode(Const.SYS_ERROR_CODE);
		ret.setMsg(Const.SYS_ERROR_MSG);
		return getJson(ret);
	}

	public static String msgSystemErr(String msg){
		ret = new RetDTO();
		ret.setCode(Const.SYS_ERROR_CODE);
		ret.setMsg(msg);
		return getJson(ret);
	}

	/**
	 * 无权限
	 */
	public static String msgAuthErr(){
		ret = new RetDTO();
		ret.setCode(Const.AUTH_ERROR_CODE);
		ret.setMsg(Const.AUTH_ERROR_MSG);
		return getJson(ret);
	}

	public static String msgLoginTimeOut(){
		ret = new RetDTO();
		ret.setCode(Const.ADMIN_ERROR_CODE);
		ret.setMsg(Const.ADMIN_ERROR_MSG);
		return getJson(ret);
	}

	public static String msgLoginErr(){
		ret = new RetDTO();
		ret.setCode(Const.LOGIN_ERROR_CODE);
		ret.setMsg(Const.LOGIN_ERROR_MSG);
		return getJson(ret);
	}

	/**
	 * 必传参数为空
	 */
	public static String msgParamNull(){
		ret = new RetDTO();
		ret.setCode(Const.ISBLANK_ERROR_CODE);
		ret.setMsg(Const.ISBLANK_ERROR_MSG);
		return getJson(ret);
	}

	/**
	 * 自定义
	 * @param msg
	 * @return
	 */
	public static String msgSetVal(String msg){
		ret = new RetDTO();
		ret.setCode(Const.OTHER_ERROR_CODE);
		ret.setMsg(msg);
		return getJson(ret);
	}

	public static String msgSetVal(String msg,Map<Object,Object> mapData){
		ret = new RetDTO();
		ret.setCode(Const.OTHER_ERROR_CODE);
		ret.setMsg(msg);
		ret.setData(mapData);
		return getJson(ret);
	}

	/**
	 * 表格表页
	 * @param page
	 */
	/*protected void setPage(Page<?> page){
		Map<Object, Object> map = new HashMap<Object,Object>();
		map.put("totalPage",page.getTotalPage());
		map.put("curPage",page.getPageNumber());
		map.put("list", page.getList());
		msgSuccess(map);
	}*/

	/**
	 * 获取导航
	 */
	/*protected void getNavigation(){
		String url = getRequest().getRequestURI();

		PermissionService permissionService = new PermissionService();

		setAttr("navigation", permissionService.getNavigation(url.substring(1,url.length())));
	}*/

	/*public String uploadImg(String field){
		File file = null;

		try {
			file = super.getFile(field).getFile();
		} catch (Exception e) {
			file = null;
		}

		// 图片上传
		if (null != file) {

			String path = "/upload/images" + "/"+ DateUtil.formatDate(new Date(), "yyyyMMdd") + "/";

			Map<String, String> imaPath = CommonUtils.uploadFile(getRequest(),file,path);

			return imaPath.get("serverPath");

		}
		return null;
	}*/

	//返回json
	public static String getJson(Object object){
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(fmt);
        String json = "";
        try {
        	json = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //System.out.println(ss);
		return json;
	}

	public static <T> Map<String, Object> beanToMap(T bean) {
		Map<String, Object> map = new HashMap<>();
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				map.put(key+"", beanMap.get(key));
			}
		}
		return map;
	}



	/**
	 * jqGrid格式数据获取成功
	 * @param object
	 */
	public static String jqGridData(Object object){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", Const.SUCCESS_CODE);
		ret.put("msg", Const.SUCCESS_MSG);
		ret.put("rows",object);
		return getJson(ret);
	}
}
