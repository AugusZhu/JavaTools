package Others;

/**
 * @author Xianfei Zhu
 *
 */
public class MethodDemo01 {
	
	/*
	 * 方法递归调用
	 */
	public static void main(String[] args) {
		System.out.println(addNum(100));
	}
	
	/*
	 * 从1000 +...+1
	 */
	public static int addNum(int num){
		if(num == 1){
			return 1; //方法出口
		}else{
			return num +addNum(num - 1);
		}
	}

}
