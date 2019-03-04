package AES;

public class Test {

	public static void main(String[] args) {
		//String content = "{'CustName': '张三','CustAddrTel': '1234567890', 'SellerBankAcct': '','SellerAddrTel': '','KPR': 'HAND-汉得管理员','FHR': 'HAND-汉得管理员','SKR': '','InvKind': '2','Memo': '编号为CON1803300529-1CON1803300529-1合同项下的05月的租赁收入,06月的租赁收入','DetailList': [{'ProdName': '租赁收入','TaxItem': '服务','Spec': '','Unit': '','Quantity': 1,'Price': 1319,'Amount': 1319,'TaxAmount': 79.14,'DisAmount': '0','DisTaxAmount': '0','TaxRate': 6,'GoodsNoVer': '12.0', 'GoodsTaxNo': '10301020101','TaxPre': '','TaxPreCon': '','ZeroTax': '','TaxDeduction': ''}, {'ProdName': '租赁收入','TaxItem': '服务','Spec': '','Unit': '','Quantity': 1,'Price': 1271,'Amount': 1271,'TaxAmount': 76.26, 'DisAmount': '0', 'DisTaxAmount': '0','TaxRate': 6,'GoodsNoVer': '12.0','GoodsTaxNo': '10301020101','TaxPre': '','TaxPreCon': '','ZeroTax': '','TaxDeduction': ''}]}";
		//String content = "{'resCode':'200','resMsg':'成功(success)'}";
		String content = "Hls1q2w3e!";
		String KEY = "1vgH5678GH3a1678";

		System.out.println("原始数据：" + content);
		System.out.println("密钥：" + KEY);

		String resStr = AES.encrypt(content, KEY);
		System.out.println("加密后数据：" + resStr);

		String oldStr = AES.decrypt(resStr, KEY);
		System.out.println("解密后数据：" + oldStr);

	}

}
