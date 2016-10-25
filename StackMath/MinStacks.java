package com.test.node;

public class MinStacks {

	public static void main(String [] args) {
		
		MinStacks stacks = new MinStacks();
		stacks.pushStack(10);
		stacks.pushStack(12);
		stacks.pushStack(5);
		stacks.pushStack(13);
		stacks.pushStack(0);
		stacks.pushStack(2);
		stacks.pushStack(34);
		
		System.out.println("原始元素:");
		stacks.printStack();
		
		System.out.println("移除元素:"+stacks.popStack());
		System.out.println("移除元素:"+stacks.popStack());
		System.out.println("移除元素:"+stacks.popStack());
		
		System.out.println("最小值:"+stacks.getMin());
		System.out.println("数据元素:");
		System.out.print(stacks.top.value+", ");
		Nodes nodes = stacks.top.node;
		while(nodes!= null) {
			
			System.out.print(nodes.value+", ");
			nodes = nodes.node;
		}
	}
	private Nodes top;
	public int popStack() {
		int result = 0;
		//如果表头不为空说明至少还有一个Nodes节点
		if(top != null) {
			//得到Nodes的value值，也就是栈顶元素值
			result = top.value;
			//获取表头的Nodes的下一个Nodes
			Nodes nodes = top.node;
			//将下一个Nodes节点置为空
			top.node = null;
			//让下一个Nodes的节点被top节点引用当前表头节点
			top = nodes; 
			
			return result;
		}
		
		return result;
	}
	
	
	public void pushStack(int value) {
		
		if(top == null) {
			//首次执行，当Nodes节点就一个的时候，min和value默认是最小值
			top = new Nodes(value, value);
		}else {
			
			//如果新元素添加进来，将上一个元素和新元素value比较，得到最小值，放入新元素节点中
			Nodes nodes = new Nodes(value, Math.min(value, top.min));

			/**
			 * 通过变量代换
			 * 将上一个节点让新元素内部的Nodes引用
			 * 而新元素让top引用
			 * 其实也就是将新元素放在链表的表头部
			 */
			nodes.node = top;
			top = nodes;
		}
	}
	
	public int getMin() {
		
		int min = top.min;
		
		int index  = 0;
		
		Nodes nodes = top;
		
		while(nodes !=  null) {
			
			if(nodes.value == min) 
				break;
			
			nodes = nodes.node;
			index++;
		}
		System.out.println("位置:"+index);
		
		return min;
	}
	
	/*
	 * 打印所有节点value的值
	 */
	private void printStack() {
		
		Nodes nodes = top;

		/**
		 * 通过循环得到
		 */
		while(nodes != null) {
			
			System.out.print(nodes.value+", ");
			nodes = nodes.node;
		}
		System.out.println();
	}
	

}
