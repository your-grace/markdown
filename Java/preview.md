#### java流

```java
//获取配置文件
private PropertiesUtil mProperties = new PropertiesUtil("sysConfig.properties");
@RequestMapping(params = "doCheckHTPdf")
public void doCheckHTPdf(HttpServletResponse response,HttpServletRequest req){
    // 获取路径
    String filePath = req.getParameter("url");
    String prefixFilePath = mProperties.readProperty("routeUpload");
    String completePath = prefixFilePath + filePath;
    File file = new File(completePath);
    byte[] data = null;
    try {
        // 编辑请求头部信息
        // 解决请求头跨域问题（IE兼容性 也可使用该方法）
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/pdf");
        FileInputStream input = new FileInputStream(file);
        data = new byte[input.available()];
        input.read(data);
        response.getOutputStream().write(data);
        input.close();
    } catch (Exception e) {
        System.out.println("e.getMessage() = " + e.getMessage());
    }
}
```

#### 前端

```js
uni.request({
    url: this.file_url,
    method: 'POST',
    data: params,
    responseType: 'arraybuffer', //接口responseType设置为arraybuffer,这里记得设置响应数据格式，不然预览的pdf是空白
    success: (response) => {
        if (!response) {
            uni.showToast({
            title: "预览失败",
            duration: 2000
        	});
    	}
        let pdfData = response.data; //pdfData是后端返回的文件流
        let blob = new Blob([pdfData], {
        	type: 'application/pdf'
        })
    	pdfData = window.URL.createObjectURL(blob) //创建预览路径
        //从接口中拿到pdf文件流，并进行encodeURIComponent编码  (要拼接pdf对应的地址)
    	this.allUrl = this.viewerUrl+encodeURIComponent(pdfData)
    },
    fail: err => {
    	console.log(err)
    }
});
```

#### 配置文件工具类

```java
package org.jeecgframework.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 
 * @author  张代浩
 *
 */
public class PropertiesUtil {
	private String properiesName = "";

	public PropertiesUtil() {

	}
	public PropertiesUtil(String fileName) {
		this.properiesName = fileName;
	}
	public String readProperty(String key) {
		String value = "";
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					properiesName);
			Properties p = new Properties();
			p.load(is);
			value = p.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}

	public Properties getProperties() {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					properiesName);
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return p;
	}

	public void writeProperty(String key, String value) {
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			is = new FileInputStream(properiesName);
			p.load(is);
			os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properiesName).getFile());

			p.setProperty(key, value);
			p.store(os, key);
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != is)
					is.close();
				if (null != os)
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public static void main(String[] args) {
		PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
		p.writeProperty("namess", "wang");
		org.jeecgframework.core.util.LogUtil.info(p.readProperty("namess"));
	}
}
```

