package com.hand.hls.sso;

import com.idsmanager.dingdang.jwt.DingdangUserRetriever;

public class test {

	public static void main(String[] args) {
		String publicKey = "{\"kty\": \"RSA\",\"kid\": \"2107104987112342040\",\"alg\": \"RS256\",\"n\": \"xAhJTSJPnHhe3JdUck4glnSR2TJZj9aL_Y7kwHkXlA-5-p9us5qBm1u5LD2eeSkpnZdqFQ31qA6TcwhWBXivWgUN6y1VIbORek_2-jTIQMLbcHd_XagTNfSAb9QOMjCtXr36sdvcHG8C5a0n-JBpr_2UqVP3uCIeOPvaYrUuKFuxCD21Rc13F7Rplm0qfNkIDyYmt38mwvev8LTgoyl5QZDLKWb2j3MJjqIry828rzHQsgYKSejNtg4i-hrEuKTJiM1NsOW2mxr1FgS0aMT9OCrhjQcq94nmrTqQLyV_Olyfkik3a-FpQJjILnquOd533VhH8Z2Wuv2EFkDtCrCLmw\",\"e\": \"AQAB\"}";
		String id_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjIxMDcxMDQ5ODcxMTIzNDIwNDAifQ.eyJlbWFpbCI6bnVsbCwibmFtZSI6bnVsbCwibW9iaWxlIjpudWxsLCJvcGVuSWQiOm51bGwsImV4dGVybmFsSWQiOiIzMjIxODIzMjE4NTE1MzY1ODEwIiwiZXhwIjoxNTMzMDk0NjY1LCJqdGkiOiI0djNfdGJyZncwUHl1TWlqWjkwVkZRIiwiaWF0IjoxNTMzMDk0MDY1LCJuYmYiOjE1MzMwOTQwMDUsInN1YiI6ImRldmVsb3BlciJ9.MRRHv5zyF3WUPk6ktha2yGEVY9Z_I8UzatQIJGojlmGoNSiSaIEBr9YpTuKlx1DnyhRFchoayHS3WoAcLPpI_825xepzRA9vYCEIapvdOEFtmPWLwf5O7FjAWtwX2vEsoY4ss_otDOCUVmJ_CEwhmuaxpJ_tzqZrAIn8rFkH6s7v_IdodqWtW14sA6kFmhArzNPcv0HZZbmxhzW-504n4k3VQ64Vuh6EoKiyM_K1136lDJCPD9Xpz9LF9QCoXTxKpKTKfAAPI5_B27AcImX7z6umTvEG1DSeq5IQ5VNZAelGbwlSFv3A13PT4MXJ5orZjKabcxQLUJE5_95ynYe61g";

		// 1.使用公钥，解析id_token
		// 使用publicKey解密上一步获取的id_token令牌
		DingdangUserRetriever retriever = new DingdangUserRetriever(id_token, publicKey);
		DingdangUserRetriever.User user = null;
		try {
			// 2.获取用户信息
			user = retriever.retrieve();
			
			String resString = null;
			System.out.println("start");
			resString = user.toString();
			System.out.println(resString);
		/*	String userId = user.getUserId();
			String userName = user.getUsername();
			System.out.println(userId);
			System.out.println(userName);*/

			// return userName;
		} catch (Exception e) {
			// LOG.warn("Retrieve SSO user failed", e);
			// return "error";
		}

	}

}
