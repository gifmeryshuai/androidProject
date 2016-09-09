package com.base.subject;

/**
 * 此类是委托类
 * 它是在执行于委托类之前 
 */
public class ProxySubject implements InterfaceSubject {

	
	private RealSubject realSubject;
	public ProxySubject(RealSubject subject) {
		// TODO Auto-generated constructor stub
		this.realSubject = subject;
	}

	@Override
	public void addSub(int id, String name) {
		// TODO Auto-generated method stub

		try {
			System.out.println("ProxySubject-增加:开始"+id+" - "+name);
			/**
			 * 在这执行委托类之前可以做一些操作
			 * 也就是所谓的处理滤过信息
			 */
			realSubject.addSub(id, name);
			
			System.out.println("ProxySubject-增加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("ProxySubject-增加失败");
		}
		
	}

	@Override
	public void deleteSub(int id) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("ProxySubject-删除开始"+id);
			this.realSubject.deleteSub(id);
			System.out.println("ProxySubject-删除成功"+id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("ProxySubject-删除失败"+id);
		}
		
	}

	@Override
	public String findSub(int id) {
		// TODO Auto-generated method stub
		
		String result = null;
		try {
			
			System.out.println("ProxySubject-查询开始"+id);
			result = this.realSubject.findSub(id);
			System.out.println("ProxySubject-查询成功"+id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("ProxySubject-查询失败"+id);
		}
		return result;
	}

	@Override
	public void updateSub(int id, String name) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("ProxySubject-修改开始"+id+" - "+name);
			this.realSubject.updateSub(id, name);
			System.out.println("ProxySubject-修改成功"+id+" - "+name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("ProxySubject-修改失败"+id+" - "+name);
		}
		
	}

}
