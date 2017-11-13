package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Goddess;

public class TestAction {
	public static void main(String[] args) throws Exception{
		GoddessAction action=new GoddessAction();
		Goddess g=new Goddess();
		g.setUser_name("xiaowang");
		g.setSex(1);
		g.setAge(25);
		g.setBirthday(new Date());
		g.setEmail("xiaowang@qq.com");
		g.setMobile("13557646755");
		g.setId(3);
		
		//action.add(g);
		//action.del(4);
		//action.edit(g);
		//action.get(1);
		List<Map<String, Object>> params=new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("name", "user_name");
		map.put("rela", "=");
		map.put("value", "'–°√¿'");
		
		params.add(map);
		
		List<Goddess> result=action.query(params);
		
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).getId()+
					":"+result.get(i).getUser_name());
		}
	}
}
