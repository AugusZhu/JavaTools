package Others;

/**
 * @author Xianfei Zhu
 *
 */
public class MethodDemo01 {
	
	/*
	 * �����ݹ����
	 */
	public static void main(String[] args) {
		System.out.println(addNum(100));
	}
	
	/*
	 * ��1000 +...+1
	 */
	public static int addNum(int num){
		if(num == 1){
			return 1; //��������
		}else{
			return num +addNum(num - 1);
		}
	}

}
