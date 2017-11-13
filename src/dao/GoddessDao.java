package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBUtil;
import model.Goddess;

public class GoddessDao {
	public void addGoddess(Goddess g) throws SQLException{//增加
		Connection conn=DBUtil.getConnection();
		String sql=""+"insert into imooc_goddess"+
				"(user_name,sex,age,birthday,email,mobile,"+
				"create_user,create_date,update_user,update_date,isdel)"+
				"value("+
				"?,?,?,?,?,?,?,current_date(),?,current_date(),?)";//current-date()获取当前日期
				
		PreparedStatement ptmt=conn.prepareStatement(sql);
		
		ptmt.setString(1, g.getUser_name());
		ptmt.setInt(2, g.getSex());
		ptmt.setInt(3, g.getAge());
		ptmt.setDate(4, new Date(g.getBirthday().getTime()));
		ptmt.setString(5, g.getEmail());
		ptmt.setString(6,g.getMobile());
		ptmt.setString(7, g.getCreate_user());
		ptmt.setString(8, g.getUpdate_user());
		ptmt.setInt(9, g.getIsdel());
		
		ptmt.execute();
	}
	
	public void updateGoddess(Goddess g) throws SQLException{//update
		Connection conn=DBUtil.getConnection();
		String sql="" +
				"update imooc_goddess "+
				" set user_name=?,sex=?,age=?,birthday=?,email=?,mobile=?, "+
				" update_user=?,update_date=current_date(),isdel=? "+
				" where id=?";	
		PreparedStatement ptmt=conn.prepareStatement(sql);
		
		ptmt.setString(1, g.getUser_name());
		ptmt.setInt(2, g.getSex());
		ptmt.setInt(3, g.getAge());
		ptmt.setDate(4, new Date(g.getBirthday().getTime()));
		ptmt.setString(5, g.getEmail());
		ptmt.setString(6,g.getMobile());
		ptmt.setString(7, g.getUpdate_user());
		ptmt.setInt(8, g.getIsdel());
		ptmt.setInt(9, g.getId());
		
		ptmt.execute();
	}
	
	public void delGoddess(Integer id) throws SQLException{//delete
		Connection conn=DBUtil.getConnection();
		String  sql=""+
				"delete from imooc_goddess "+
				" where id=? ";
		PreparedStatement ptmt=conn.prepareStatement(sql);
		
		ptmt.setInt(1, id);
		ptmt.execute();
	}
	
	public List<Goddess> query() throws SQLException{//查询所有goddess的id,user_name,age
		List<Goddess> result=new ArrayList<>();
		Connection conn=DBUtil.getConnection();
		StringBuilder sb=new StringBuilder();
		sb.append("select id,user_name,age from imooc_goddess");
		
		PreparedStatement stmt=conn.prepareStatement(sb.toString());
		ResultSet rs=stmt.executeQuery();
		
		Goddess g=null;
		while(rs.next()){
			g=new Goddess();
			g.setId(rs.getInt("id"));
			g.setUser_name(rs.getString("user_name"));
			g.setAge(rs.getInt("age"));
			result.add(g);
		}
		return result;
	}	
	public List<Goddess> query(String name,String mobile,String email) throws SQLException{
		//根据姓名，手机号，邮箱查询
		List<Goddess> result=new ArrayList<>();
		Connection conn=DBUtil.getConnection();
		StringBuilder sb=new StringBuilder();
		sb.append(" select * from imooc_goddess ");
		sb.append(" where user_name like ? and mobile like ? and email like ?");
		
		PreparedStatement stmt=conn.prepareStatement(sb.toString());
		stmt.setString(1, "%"+name+"%");
		stmt.setString(2, "%"+mobile+"%");
		stmt.setString(3, "%"+email+"%");
		ResultSet rs=stmt.executeQuery();
		
		Goddess g=null;
		while(rs.next()){
			g=new Goddess();
			g.setId(rs.getInt("id"));
			g.setUser_name(rs.getString("user_name"));
			g.setAge(rs.getInt("age"));
			g.setSex(rs.getInt("sex"));
			g.setBirthday(rs.getDate("birthday"));
			g.setEmail(rs.getString("email"));
			g.setMobile(rs.getString("mobile"));
			g.setCreate_date(rs.getDate("create_date"));
			g.setCreate_user(rs.getString("create_user"));
			g.setUpdate_date(rs.getDate("update_date"));
			g.setUpdate_user(rs.getString("update_user"));
			g.setIsdel(rs.getInt("isdel"));
			
			result.add(g);
		}
		return result;
	}
	
	public List<Goddess> query(List<Map<String,Object>>params)throws SQLException{
		List<Goddess> result=new ArrayList<>();
		
		Connection conn=DBUtil.getConnection();
		StringBuilder sb=new StringBuilder();
		sb.append("select * from imooc_goddess where 1=1 ");
		
		if(params!=null&&params.size()>0){
			for (int i = 0; i < params.size(); i++) {
				Map<String, Object> map=params.get(i);
				sb.append(" and  "+map.get("name")+" "+map.get("rela")+" "+map.get("value")+" ");
			}
		}
		/*sb.append("select * from imooc_goddess where 1=1 ");//1=1连接后面循环中的语句
		
		if(param!=null&&param.size()>0){
			for(int i=0;i<param.size();i++){
				Map<String,Object> map=param.get(i);
				sb.append(" and "+map.get("name")+" "+map.get("rela")+" "+map.get("value")+" ");
			}//采用map记录数据库语句，如name like(=) 小王
		}
		*/
		PreparedStatement ptmt=conn.prepareStatement(sb.toString());
		System.out.println(sb.toString());
		ResultSet rs=ptmt.executeQuery();
		Goddess g=null;
		while(rs.next()){
			g=new Goddess();
			g.setId(rs.getInt("id"));
			g.setUser_name(rs.getString("user_name"));
			g.setAge(rs.getInt("age"));
			g.setSex(rs.getInt("sex"));
			g.setBirthday(rs.getDate("birthday"));
			g.setEmail(rs.getString("email"));
			g.setMobile(rs.getString("mobile"));
			g.setCreate_date(rs.getDate("create_date"));
			g.setCreate_user(rs.getString("create_user"));
			g.setUpdate_date(rs.getDate("update_date"));
			g.setUpdate_user(rs.getString("update_user"));
			g.setIsdel(rs.getInt("isdel"));
			
			result.add(g);
			
		}
		return result;
	}
	public Goddess get(int id) throws SQLException{
		Goddess g=null;
		Connection conn=DBUtil.getConnection();
		String sql=""+
				"select * from imooc_goddess "+
				"where id=?";
		PreparedStatement ptmt=conn.prepareStatement(sql);
		ptmt.setInt(1, id);
		ResultSet rs=ptmt.executeQuery();
		if(rs.next()){
			g=new Goddess();
			g.setId(rs.getInt("id"));
			g.setUser_name(rs.getString("user_name"));
			g.setAge(rs.getInt("age"));
			g.setSex(rs.getInt("sex"));
			g.setBirthday(rs.getDate("birthday"));
			g.setEmail(rs.getString("email"));
			g.setMobile(rs.getString("mobile"));
			g.setCreate_date(rs.getDate("create_date"));
			g.setCreate_user(rs.getString("create_user"));
			g.setUpdate_date(rs.getDate("update_date"));
			g.setUpdate_user(rs.getString("update_user"));
			g.setIsdel(rs.getInt("isdel"));
		}
		return g;
	}
}
