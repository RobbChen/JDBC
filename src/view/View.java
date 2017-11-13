package view;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import action.GoddessAction;
import model.Goddess;

public class View {
	private static final String CONTEXT="欢迎开到女神party: \n"+
					"下面是女神的功能列表:\n"+
					"[Main/M]:主菜单\n"+
					"[Query/Q]:查看全部女神的信息\n"+
					"[GET/G]:查看某位女神的详细信息\n"+
					"[ADD/A]:添加信息\n"+
					"[UPDATE/U]:更新信息\n"+
					"[DELETE/D]:删除信息\n"+
					"[SEARCH/S]:查询信息(根据姓名，手机号来查询)\n"+
					"[EXIT/E]:退出信息系统\n"+
					"[BREAK/B]:退出当前功能，返回主菜单";
	private static final String OPERATION_MAIN="MAIN";
	private static final String OPERATION_QUERY="QUERY";
	private static final String OPERATION_GET="GET";
	private static final String OPERATION_ADD="ADD";
	private static final String OPERATION_UPDATE="UPDATE";
	private static final String OPERATION_DELETE="DELETE";
	private static final String OPERATION_SEARCH="SEARCH";
	private static final String OPERATION_EXIT="EXIT";
	private static final String OPERATION_BREAK="BREAK";
	
	public static void main(String[] args) throws Exception{
		System.out.println(CONTEXT);
		
		Scanner scan=new Scanner(System.in);
		GoddessAction action=new GoddessAction();
		
		String previous=null;
		int step=1;
		Goddess goes=null;
		
		while(scan.hasNext()){
			String in=scan.next();
			if(OPERATION_EXIT.equals(in.toUpperCase())
				||OPERATION_EXIT.substring(0, 1).equals(in.toUpperCase())){
				System.out.println("您已经成功退出信息查询系统");
				break;
			}else if(OPERATION_MAIN.equals(in.toUpperCase())
				||OPERATION_MAIN.substring(0, 1).equals(in.toUpperCase())){
					step=1;
					previous=null;
					goes=null;
					System.out.println(CONTEXT);
				}else if(OPERATION_QUERY.equals(in.toUpperCase())
				||OPERATION_QUERY.substring(0, 1).equals(in.toUpperCase())){
					List<Goddess> list=action.query();
					for(Goddess go:list){
						System.out.println(go.toString());
					}
				}else if(OPERATION_GET.equals(in.toUpperCase())
				||OPERATION_GET.substring(0, 1).equals(in.toUpperCase())
				||OPERATION_GET.equals(previous)){
					previous=OPERATION_GET;
					if(step==1){
						System.out.println("请输入id");
					}else if(step>1){
						Integer id=null;
						Goddess go;
						
						try {
							id=Integer.valueOf(in);
							try {
								go=action.get(id);
								if(go==null) System.out.println("查询失败");
								else System.out.println(go.toString());
							} catch (Exception e) {
								System.out.println("查询失败");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("请正确输入id");
						}
					}
					step++;
				}else if(OPERATION_ADD.equals(in.toUpperCase())
						||OPERATION_ADD.substring(0, 1).equals(in.toUpperCase())
						||OPERATION_ADD.equals(previous)){
					previous=OPERATION_ADD;
					if(step==1){
						System.out.println("请输入姓名");
					}else if(step==2){
						goes=new Goddess();
						goes.setUser_name(in);
						System.out.println("请输入年龄");
					}else if(step==3){
						try {
							Integer age=null;
							age=Integer.valueOf(in);
							goes.setAge(age);
							System.out.println("请输入生日信息:格式yyyy-MM-dd");
						} catch (NumberFormatException e) {
							step=2;//保证下一次不会跳过年龄信息的录入
							System.out.println("请正确输入年龄信息");
						}
					}else if(step==4){
						SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
						Date birthday=null;
						try {
							birthday=sf.parse(in);
							goes.setBirthday(birthday);
							System.out.println("请输入邮箱信息");
						} catch (Exception e) {
							step=3;
							System.out.println("请正确输入生日信息");
						    }
						}else if(5==step){
							goes.setEmail(in);
							System.out.println("请输入女神的信息[手机号]：");
						}else if(6==step){
							goes.setMobile(in);
							try {
								action.add(goes);
							} catch (Exception e) {
								System.out.println("新增女神信息失败");
							}
							System.out.println("新增女神信息成功");
							step=1;
							previous=null;
						}
						if(OPERATION_ADD.equals(previous)){
							step++;
						}
					}else if(OPERATION_UPDATE.equals(in.toUpperCase())
								||OPERATION_UPDATE.substring(0, 1).equals(in.toUpperCase())
								||OPERATION_UPDATE.equals(previous)){
							previous=OPERATION_UPDATE;
							if(1==step){
								System.out.println("请输入要修改的女神ID：");
							}else if(2==step){
								Integer id=null;
								try {
									id = Integer.valueOf(in);
									try {
										goes = action.get(id);
										if(goes==null){
											System.out.println("查询女神信息失败");
											step=1;
										}
									} catch (Exception e) {
										System.out.println("查询女神信息失败");
										step=1;
									}
								} catch (Exception e) {
									System.out.println("请输入正确的女神ID：");
									step=1;
								}
								System.out.println("请输入新的女神信息[姓名]，如果不修改该值，请输入-1：");
							}else if(3==step){
								if(-1!=Integer.valueOf(in)){
									goes.setUser_name(in);
								}
								System.out.println("请输入新的女神信息[年龄]，如果不修改该值，请输入-1：");
							}else if(4==step){
								Integer age=null;
								try {
									age = Integer.valueOf(in);
									if(-1!=age){
										goes.setAge(age);
									}
									System.out.println("请输入新的女神信息[生日，格式:2014-12-12]，如果不修改该值，请输入-1：");
								} catch (Exception e) {
									step=3;
									System.out.println("请输入正确女神的信息[年龄]：");
								}
							}else if(5==step){
								SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
								Date birthday=null;
								try {
									if(-1!=Integer.valueOf(in)){
										birthday = sf.parse(in);
										goes.setBirthday(birthday);
									}
									System.out.println("请输入新的女神信息[邮箱]，如果不修改该值，请输入-1：");
								} catch (Exception e) {
									step=4;
									System.out.println("请输入正确女神的信息[生日]：");
								}
							}else if(6==step){
								if(-1!=Integer.valueOf(in)){
									goes.setEmail(in);
								}
								System.out.println("请输入新的女神信息[手机号]，如果不修改该值，请输入-1：");
							}else if(7==step){
								if(-1!=Integer.valueOf(in)){
									goes.setMobile(in);
								}
								try {
									action.edit(goes);
								} catch (Exception e) {
									System.out.println("更新女神信息失败");
								}
								System.out.println("更新女神信息成功");
								step=1;
								previous=null;
							}if(OPERATION_UPDATE.equals(previous)){
								step++;
							}
						}else if(OPERATION_DELETE.equals(in.toUpperCase())
									||OPERATION_DELETE.substring(0, 1).equals(in.toUpperCase())
									||OPERATION_DELETE.equals(previous)){
								previous=OPERATION_DELETE;
								if(1==step){
									System.out.println("请输入要删除的女神ID：");
								}else if(2==step){
									Integer id=null;
									try {
										id = Integer.valueOf(in);
										try {
											action.del(id);
											step=1;
											System.out.println("删除女神信息成功");
										} catch (Exception e) {
											System.out.println("删除女神信息失败");
										}
									} catch (Exception e) {
										System.out.println("请输入正确的女神ID：");
										step=1;
									}
								}
								if(OPERATION_DELETE.equals(previous)){
									step++;
								}
						}else if(OPERATION_SEARCH.equals(in.toUpperCase())
							||OPERATION_SEARCH.substring(0, 1).equals(in.toUpperCase())
							||OPERATION_SEARCH.equals(previous)){
						previous =OPERATION_SEARCH;
						if(step==1){
							System.out.println("请输入要查询的女神信息，支持姓名，手机号查询，如果两个参数都要输入请用逗号隔开[user_name=xx,mobile=xx");
						}else if(step==2){
							if(in!=null&&in!=""){
								List<Map<String,Object>>params=new ArrayList<>();
								Map<String,Object> map=null;
								String[] strs=in.split(",");
								for(int i=0;i<strs.length;i++){
									String [] sub_str=strs[i].split("=");
									map=new HashMap<String,Object>();
									map.put("name", sub_str[0]);
									map.put("rela", "=");
									map.put("value", "'"+sub_str[1]+"'");
									params.add(map);
								}
								List<Goddess> list=action.query(params);
								if(list!=null&&list.size()>0){
									for(Goddess god:list)
										System.out.println(god.toString());
								}else{
									System.out.println("没有查询到信息");
								}
								step=1;
							}
						}
						if(OPERATION_SEARCH.equals(previous)){
							step++;
						}
					}
				}
			}
		}

