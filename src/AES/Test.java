package AES;

public class Test {

	public static void main(String[] args) {
		//String content = "{'CustName': '����','CustAddrTel': '1234567890', 'SellerBankAcct': '','SellerAddrTel': '','KPR': 'HAND-���ù���Ա','FHR': 'HAND-���ù���Ա','SKR': '','InvKind': '2','Memo': '���ΪCON1803300529-1CON1803300529-1��ͬ���µ�05�µ���������,06�µ���������','DetailList': [{'ProdName': '��������','TaxItem': '����','Spec': '','Unit': '','Quantity': 1,'Price': 1319,'Amount': 1319,'TaxAmount': 79.14,'DisAmount': '0','DisTaxAmount': '0','TaxRate': 6,'GoodsNoVer': '12.0', 'GoodsTaxNo': '10301020101','TaxPre': '','TaxPreCon': '','ZeroTax': '','TaxDeduction': ''}, {'ProdName': '��������','TaxItem': '����','Spec': '','Unit': '','Quantity': 1,'Price': 1271,'Amount': 1271,'TaxAmount': 76.26, 'DisAmount': '0', 'DisTaxAmount': '0','TaxRate': 6,'GoodsNoVer': '12.0','GoodsTaxNo': '10301020101','TaxPre': '','TaxPreCon': '','ZeroTax': '','TaxDeduction': ''}]}";
		//String content = "{'resCode':'200','resMsg':'�ɹ�(success)'}";
		String content = "Hls1q2w3e!";
		String KEY = "1vgH5678GH3a1678";

		System.out.println("ԭʼ���ݣ�" + content);
		System.out.println("��Կ��" + KEY);

		String resStr = AES.encrypt(content, KEY);
		System.out.println("���ܺ����ݣ�" + resStr);

		String oldStr = AES.decrypt(resStr, KEY);
		System.out.println("���ܺ����ݣ�" + oldStr);

	}

}
