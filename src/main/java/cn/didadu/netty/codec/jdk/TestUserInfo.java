package cn.didadu.netty.codec.jdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 经过测试，JDK序列化机制编码后的二进制数据大小是二进制编码的5.29倍
 * 所以JDS的序列化性能不好，占存储，占带宽
 * @author zhangjing
 *
 */

public class TestUserInfo {

	public static void main(String[] args) throws IOException {
		
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("Welcome to Netty");
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(info);
		os.flush();
		os.close();
		
		byte[] b = bos.toByteArray();
		System.out.println("The jdk serializable length is : " + b.length);
		bos.close();
		
		System.out.println("-------------------------------------");
		System.out.println("The byte array serializable length is : "
			+ info.codeC().length);
		
	}
}
