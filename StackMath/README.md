#### 最小栈的实现

#### 学过Java的都知道有一种数据结构“栈”它是按照数据先进后出的原则存放数据；其中Stack就是最典型的一个类；

#### 一，栈的基本概念：


> 栈是一种数据结构，只能在某一端插入和删除的特殊线性表，他按照先入后出的原则存放数据。所以先进入的数据被压放到栈底，后进的数据被存放到栈顶。所以如果要读取数据要从第一个栈顶开始读取，第一条数据被最后一次读取到。

> 栈允许在同一端进行插入和删除操作的特殊线性表，允许进行插入和删除的一端称之为栈顶，另外一端称为栈底；栈底固定，而栈顶是浮动的。


![stack](https://github.com/gifmeryshuai/markDown-ImageCahce/blob/master/stack.png?raw=true)


#### 二，栈的使用方法

>     了解了栈的概念之后我们现在看一下具体栈操作的代码：
```xml
public static void stackTest() {
		
		/**
		 * 创建栈的实例
		 * Stack<Object> 可以使用泛型
		 */
		Stack stack = new Stack();
		/**
		 * 进栈使用push方法
		 * push(Object)参数是Object类型，所以里面可以是任何对象
		 * 
		 */
		stack.push(123);
		printMsg(stack);
		
		stack.push("MSG");
		printMsg(stack);
		
		stack.push(789);
		printMsg(stack);
		
		stack.push("XYZ");
		printMsg(stack);
		
		stack.push(3.14);
		
		stack.push('d');
		printMsg(stack);
		
		/**
		 * 目前为止'd'为栈顶
		 * 所以执行pop方法之后
		 * XYZ就成了栈顶了
		 */
		stack.pop();
		printMsg(stack);
		
		System.out
				.println("peek:" + stack.peek() + ",elementAt:" + stack.elementAt(0) + ",search:" + stack.search("XYZ")
						+ ",firstElement:" + stack.firstElement() + ",lastElement:" + stack.lastElement());
	}
	
	public static void printMsg(Stack stack) {
		
		//empty 判断 此Stack是否为空，也就是栈底是否没有了；
		if(stack != null && !stack.empty()) {
			//通过Enumeration枚举列出所有元素
			Enumeration enumeration = stack.elements();
			
			System.out.println("Stack元素数据");
			
			//循环遍历所有枚举的元素
			while(enumeration.hasMoreElements()) {
				System.out.print(enumeration.nextElement()+"  ");
			}
			System.out.println("");
		}
	}


//打印结果

		/**
		 *
		 * Stack元素数据
         * 123  
         * Stack元素数据
         * 123  MSG  
         * Stack元素数据
         * 123  MSG  789  
         * Stack元素数据
         * 123  MSG  789  XYZ  
         * Stack元素数据
         * 123  MSG  789  XYZ  3.14  d  
         * Stack元素数据
         * 123  MSG  789  XYZ  3.14  
         * 获取栈顶元素peek:3.14,
         * 根据栈的索引获取元素elementAt:123,
         * 根据元素获取栈中的索引search:2,
         * 获取栈底元素firstElement:123,
         * 获取栈顶元素lastElement:3.14
         * 
		 **/
```



#### 三，最小栈的实现
> 上面已经说了，栈的基本使用方法，现在我们有这么一个问题；



```
如果有一个栈，要有出栈（pop）,入栈(push)，还有获取最小值（getMin）方法;

思路：

1. 首先声明一个整型变量int min，初始值为-1；
2. 当第一个元素入栈的时候，将min值赋值为0；
3. 之后每当有元素入栈的时候，都要与min索引所指定的栈中数据进行比较，如果小于新元素那么，就像min赋值为新元素的索引，否则维持min的值不变；
4. 当调用getMin的方法时候，就通过stack.elementAt(min)，来取出来；
```
![image1](https://github.com/gifmeryshuai/markDown-ImageCahce/blob/master/stack-1.png?raw=true)
![image2](https://github.com/gifmeryshuai/markDown-ImageCahce/blob/master/stack-2.png?raw=true)
![image3](https://github.com/gifmeryshuai/markDown-ImageCahce/blob/master/stack-3.png?raw=true)

> 知道思路之后我们看代码：（主要看获取栈中最小值的方法）
```
```xml
/**
	 * 方法一
	 */
	public static void method1() {

		int min = -1;

//		// 创建栈
		Stack<Integer> stack = new Stack<Integer>();
//
//		// 当第一个元素就如栈中就默认记录该值是栈中最小的一个元素
//		stack.push(new Integer(10));
//		// 将min置为0，保存最小元素的索引
//		min = 0;
//
//		// 第二个元素与的最小元素的比较，如果大于就改变min数据，否之记录新进入元素的索引位置；
//		stack.push(new Integer(12));
//		if (stack.get(min) > (new Integer(12))) {
//
//			min = 1;
//		}
//
//		stack.push(new Integer(9));
//		if (stack.get(min) > (new Integer(9))) {
//
//			min = 2;
//		}
//		stack.push(new Integer(4));
//		if (stack.get(min) > (new Integer(4))) {
//
//			min = 3;
//		}
//		stack.push(new Integer(7));
//		if (stack.get(min) > (new Integer(7))) {
//
//			min = 4;
//		}
//
//		printStack(stack);
//		
//		
//		System.out.println("最小值："+stack.get(min)+", 索引："+min);
		
		
		Integer[] integers = new Integer[]{9, 1, 4, 8, 3};
		
		for(int i = 0 ; i < integers.length ; i++) {
			
			stack.push(integers[i]);
			if(i == 0) {

				min = 0;
			}else {
				
				if(stack.get(min) >= integers[i]) {
					
					min = i;
				}
			}
			
			
		}
		printStack(stack);
		
		
		System.out.println("最小值："+stack.get(min)+", 索引："+min);
		
		//假如Stack最小值在栈顶，我们执行pop出去栈顶，而我们就找不到最小值了
		//所以我们要保存从栈底到栈定中所有的最小数据的索引
	}

	public static void printStack(Stack<Integer> stack) {

		if (stack != null) {

			if (stack.empty()) {
				System.out.println("栈为空");
			} else {

				Enumeration item = stack.elements();// 获取stack的枚举对象

				System.out.print("堆栈中的元素：");
				while (item.hasMoreElements()) {
					System.out.print(item.nextElement() + " ");// 遍历枚举中所有的元素
				}
				System.out.println();
			}
		}
	}
	/**
	 * 堆栈中的元素：9 10 4 8 3
     * 最小值：3, 索引：4
	 */
```
> 目前为止，我们已经获取到了栈中的的最小的元素，以及索引的位置。但是，如果栈中如果pop移除出栈一个元素，而这个栈顶的元素正好是最小的元素该怎么办？打个比方：如果栈中的“3”正好是栈顶的元素，而正好也是栈中最小的元素，那么pop()之后我们就找不到了栈中最小的元素了？min这个记录的值也就超出栈中长度索引了，也就是说没有备胎了，没有其他的数据来顶替了，所以这个方法是有问题的；
---

```
思路：
1.  首先创建一个存储数据元素的stack-A栈，之后在创建一个保存A栈最小元素的索引
2.  当第一个元素进入A-栈的时候，B-栈就默认保存A-栈中第一个栈的元素索引也就是A-栈的栈底；
3.  每一个有新的元素进入A-栈都要从B-栈的栈顶获取索引值，给A-栈通过索引得到元素值与新元素进行比较，如果新元素小于A-栈中最小元素的话，那么就将这个新元素的索引放入B-栈；
4.  如果A-栈中移除栈顶元素，首先要将B-栈中栈顶元素获取到，让A-栈得到对象索引元素，判断此元素是否是栈顶元素，如果是，那么就将B-栈中的栈顶元素也相应的移除掉，这样，如果还要获取A-栈中最小元素，直接就可以从B-栈中栈顶在得到元素值，给A-栈即可；
5. 同理，获取getMin最小元素值，也是一样，从B-栈中得到栈顶元素，交给A-栈，得到最小元素；
```

```xml
public static void method2() {
		
		/**
		 * 存储元素stack
		 */
		Stack<Integer> stack_A = new Stack<Integer>();
		/**
		 * 存储stack_A中最小元素的索引
		 */
		Stack<Integer> stack_B = new Stack<Integer>();
		
		Integer[] integers = new Integer[]{9, 18, 2, 7, 29, 3, 6, 2, 8, 13, 4};
		
		for(int i = 0 ; i < integers.length ; i++) {
			
			int value = integers[i];
			pushStack_A(stack_A, stack_B, value);
			
			//入栈
//			stack_A.push(integers[i]);
//			if(i == 0) {
//
//				//第一次默认保存入栈的元素索引
//				stack_B.push(i);
//			}else {
//				//获取stack_B栈顶元素，交给stack_A，stack_A获取最小值与新元素进行比较
//				//如果大于新元素说明，新元素是最小值，
//				if(stack_A.get(stack_B.lastElement()) >= stack_A.lastElement()) {
//					//之后保存新元素的索引值到stack_B中；
//					stack_B.push(i);
//				}
//			}
		}
		System.out.println("stack_A数据元素：");
		printStack(stack_A);
		System.out.println("stack_B数据元素：");
		printStack(stack_B);
		
		for(int i = 0 ; i <6;i++) {
			popStack_A(stack_A, stack_B);
			
		}
		
//		System.out.println(""+stack_A.size());
//		System.out.println("除去Stack_A栈顶:"+stack_A.pop());
//		System.out.println(""+stack_A.size());
//		System.out.println("除去Stack_B的对应的索引:"+stack_B.pop());
		
		
		System.out.println("stack_A数据元素：");
		printStack(stack_A);
		System.out.println("stack_B数据元素：");
		
		printStack(stack_B);
	}
	
	
	
```
#### Stack_A移除元素
```xml
	public static void popStack_A(Stack<Integer> stack_A, Stack<Integer> stack_B) {
		
		System.out.println("移除Stack_A元素:"+stack_A.pop());
		if(stack_A.size() == stack_B.lastElement()) {
			
			System.out.println("移除Stack_B栈顶:"+stack_B.pop());
		}
		
	}
```
#### Stack_A的push方法
```xml
	public static void pushStack_A(Stack<Integer> stack_A, Stack<Integer> stack_B, int value) {
		//第一次默认保存入栈的元素索引
		if(stack_A.size() == 0) {
			
			stack_A.push(value);
			stack_B.clear();
			stack_B.push(0);
		}else {
			//获取stack_B栈顶元素，交给stack_A，stack_A获取最小值与新元素进行比较
//			//如果大于新元素说明，新元素是最小值，
			stack_A.push(value);
			if(stack_A.elementAt(stack_B.lastElement()) >= stack_A.lastElement()) {
				//之后保存新元素的索引值到stack_B中；
				stack_B.push(stack_A.size()-1);
			}
		}
		
	}
```


---

```
具体的代码已经在上面；
又有一个问题，如果我们想要通过Java对象以一种链式的结构（相当于LinkList）来实现该怎么实现：
```

```xml

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


```
#### 源码的链接地址
[源码](https://github.com/gifmeryshuai/androidProject/tree/master/StackMath)

