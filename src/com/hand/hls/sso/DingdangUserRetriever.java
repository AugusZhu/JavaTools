package com.hand.hls.sso;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

public class DingdangUserRetriever {

	private String jwtToken;
	private String publicKey;
	private JsonWebSignature jws;

	public DingdangUserRetriever(String jwtToken, String publicKey) {
		System.out.println("test-start");
		this.jwtToken = jwtToken;
		this.publicKey = publicKey;
		System.out.println("test-end");
	}

	public User retrieve() throws JoseException, IOException {
		initJWTSingnature();

		this.jws.setKey(JsonWebKey.Factory.newJwk(this.publicKey).getKey());

		boolean verifySignature = this.jws.verifySignature();
		if (verifySignature) {
			String payload = this.jws.getPayload();
			ObjectMapper objectMapper = new ObjectMapper();
			return (User) objectMapper.readValue(payload, User.class);
		}
		return null;
	}

	private void initJWTSingnature() throws JoseException {
		this.jws = new JsonWebSignature();
		this.jws.setCompactSerialization(this.jwtToken);
	}

	public static class User implements Serializable {
		private static final long serialVersionUID = 1455128854908876661L;
		private String aud;
		private String userId;
		private String name;
		private String email;
		private String openId;
		private String mobile;
		private String exp;
		private String jti;
		private String iat;
		private String nbf;
		private String sub;

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public void setAud(String aud) {
			this.aud = aud;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setOpenId(String openId) {
			this.openId = openId;
		}

		public void setExp(String exp) {
			this.exp = exp;
		}

		public void setJti(String jti) {
			this.jti = jti;
		}

		public void setNbf(String nbf) {
			this.nbf = nbf;
		}

		public void setSub(String sub) {
			this.sub = sub;
		}

		public String getName() {
			return this.name;
		}

		public String getUserId() {
			return this.userId;
		}

		public String getEmail() {
			return this.email;
		}

		public String getNbf() {
			return this.nbf;
		}

		public String getSub() {
			return this.sub;
		}

		public String getUsername() {
			return this.sub;
		}

		public String getOpenId() {
			return this.openId;
		}

		public String getMobile() {
			return this.mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public void setIat(String iat) {
			this.iat = iat;
		}

		public String getAud() {
			return this.aud;
		}

		public String getExp() {
			return this.exp;
		}

		public String getJti() {
			return this.jti;
		}

		public String getIat() {
			return this.iat;
		}

		public String toString() {
			return "Username{aud='" + this.aud + '\'' + ", userId='" + this.userId + '\'' + ", name='" + this.name
					+ '\'' + ", email='" + this.email + '\'' + ", openId='" + this.openId + '\'' + ", mobile='"
					+ this.mobile + '\'' + ", exp='" + this.exp + '\'' + ", jti='" + this.jti + '\'' + ", nbf='"
					+ this.nbf + '\'' + ", sub='" + this.sub + '\'' + '}';
		}
	}
}
