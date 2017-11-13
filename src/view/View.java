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
	private static final String CONTEXT="��ӭ����Ů��party: \n"+
					"������Ů��Ĺ����б�:\n"+
					"[Main/M]:���˵�\n"+
					"[Query/Q]:�鿴ȫ��Ů�����Ϣ\n"+
					"[GET/G]:�鿴ĳλŮ�����ϸ��Ϣ\n"+
					"[ADD/A]:�����Ϣ\n"+
					"[UPDATE/U]:������Ϣ\n"+
					"[DELETE/D]:ɾ����Ϣ\n"+
					"[SEARCH/S]:��ѯ��Ϣ(�����������ֻ�������ѯ)\n"+
					"[EXIT/E]:�˳���Ϣϵͳ\n"+
					"[BREAK/B]:�˳���ǰ���ܣ��������˵�";
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
				System.out.println("���Ѿ��ɹ��˳���Ϣ��ѯϵͳ");
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
						System.out.println("������id");
					}else if(step>1){
						Integer id=null;
						Goddess go;
						
						try {
							id=Integer.valueOf(in);
							try {
								go=action.get(id);
								if(go==null) System.out.println("��ѯʧ��");
								else System.out.println(go.toString());
							} catch (Exception e) {
								System.out.println("��ѯʧ��");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("����ȷ����id");
						}
					}
					step++;
				}else if(OPERATION_ADD.equals(in.toUpperCase())
						||OPERATION_ADD.substring(0, 1).equals(in.toUpperCase())
						||OPERATION_ADD.equals(previous)){
					previous=OPERATION_ADD;
					if(step==1){
						System.out.println("����������");
					}else if(step==2){
						goes=new Goddess();
						goes.setUser_name(in);
						System.out.println("����������");
					}else if(step==3){
						try {
							Integer age=null;
							age=Integer.valueOf(in);
							goes.setAge(age);
							System.out.println("������������Ϣ:��ʽyyyy-MM-dd");
						} catch (NumberFormatException e) {
							step=2;//��֤��һ�β�������������Ϣ��¼��
							System.out.println("����ȷ����������Ϣ");
						}
					}else if(step==4){
						SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
						Date birthday=null;
						try {
							birthday=sf.parse(in);
							goes.setBirthday(birthday);
							System.out.println("������������Ϣ");
						} catch (Exception e) {
							step=3;
							System.out.println("����ȷ����������Ϣ");
						    }
						}else if(5==step){
							goes.setEmail(in);
							System.out.println("������Ů�����Ϣ[�ֻ���]��");
						}else if(6==step){
							goes.setMobile(in);
							try {
								action.add(goes);
							} catch (Exception e) {
								System.out.println("����Ů����Ϣʧ��");
							}
							System.out.println("����Ů����Ϣ�ɹ�");
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
								System.out.println("������Ҫ�޸ĵ�Ů��ID��");
							}else if(2==step){
								Integer id=null;
								try {
									id = Integer.valueOf(in);
									try {
										goes = action.get(id);
										if(goes==null){
											System.out.println("��ѯŮ����Ϣʧ��");
											step=1;
										}
									} catch (Exception e) {
										System.out.println("��ѯŮ����Ϣʧ��");
										step=1;
									}
								} catch (Exception e) {
									System.out.println("��������ȷ��Ů��ID��");
									step=1;
								}
								System.out.println("�������µ�Ů����Ϣ[����]��������޸ĸ�ֵ��������-1��");
							}else if(3==step){
								if(-1!=Integer.valueOf(in)){
									goes.setUser_name(in);
								}
								System.out.println("�������µ�Ů����Ϣ[����]��������޸ĸ�ֵ��������-1��");
							}else if(4==step){
								Integer age=null;
								try {
									age = Integer.valueOf(in);
									if(-1!=age){
										goes.setAge(age);
									}
									System.out.println("�������µ�Ů����Ϣ[���գ���ʽ:2014-12-12]��������޸ĸ�ֵ��������-1��");
								} catch (Exception e) {
									step=3;
									System.out.println("��������ȷŮ�����Ϣ[����]��");
								}
							}else if(5==step){
								SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
								Date birthday=null;
								try {
									if(-1!=Integer.valueOf(in)){
										birthday = sf.parse(in);
										goes.setBirthday(birthday);
									}
									System.out.println("�������µ�Ů����Ϣ[����]��������޸ĸ�ֵ��������-1��");
								} catch (Exception e) {
									step=4;
									System.out.println("��������ȷŮ�����Ϣ[����]��");
								}
							}else if(6==step){
								if(-1!=Integer.valueOf(in)){
									goes.setEmail(in);
								}
								System.out.println("�������µ�Ů����Ϣ[�ֻ���]��������޸ĸ�ֵ��������-1��");
							}else if(7==step){
								if(-1!=Integer.valueOf(in)){
									goes.setMobile(in);
								}
								try {
									action.edit(goes);
								} catch (Exception e) {
									System.out.println("����Ů����Ϣʧ��");
								}
								System.out.println("����Ů����Ϣ�ɹ�");
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
									System.out.println("������Ҫɾ����Ů��ID��");
								}else if(2==step){
									Integer id=null;
									try {
										id = Integer.valueOf(in);
										try {
											action.del(id);
											step=1;
											System.out.println("ɾ��Ů����Ϣ�ɹ�");
										} catch (Exception e) {
											System.out.println("ɾ��Ů����Ϣʧ��");
										}
									} catch (Exception e) {
										System.out.println("��������ȷ��Ů��ID��");
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
							System.out.println("������Ҫ��ѯ��Ů����Ϣ��֧���������ֻ��Ų�ѯ���������������Ҫ�������ö��Ÿ���[user_name=xx,mobile=xx");
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
									System.out.println("û�в�ѯ����Ϣ");
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

