<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh">
<head>
  <title>V2 Account</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
  <h1>V2Ray使用说明</h1>
</div>
<div class="container">
	<h2 style="color: blue">你的专属ID</h2>
	<h3 style="color: red">${accountBean.id}</h3>
	<h4 style="color: blue">***注: 该id包含了查询端口号${accountBean.port}</h4>
	<h3>点<a href="../resource/software/config.json" download="config.json"> 这里 </a>下载配置文件</h3>
	<h4>用记事本打开config.json文件，把其中的 xx-x-x-x-xx 替换成你的专属ID(开头红色部分)并保存，参考下图。</h4>
    <div>  <img src="../resource/image/v2/v2-config.png" class="img-responsive center-block">  </div>
	<h4 style="color: red">***注意: 这个修改后的 config.json 文件后面需要使用。</h4>
	<br />
	<h3 style="color: red">使用本方法之前，先退出国产杀毒软件。比如360，金山毒霸之类的！！！</h3>
	<h3 style="color: red">***请确认使用本方法的电脑、平板(Pad)或手机的时间是 正确的 日期和时间。</h3>
	<br />
	
	<h3>一、Windows客户端  </h3>
	<h4 style="color: red">请按照如下步骤逐条操作！！！</h4>
	<h4>1.点击下面的连接下载客户端</h4>
  <h4>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="https://www.ifreesky.cn/resource/software/v2ray-windows-64.zip" download="v2ray-windows-64.zip">64位下载 </a>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="https://www.ifreesky.cn/resource/software/v2ray-windows-32.zip" download="v2ray-windows-32.zip"> 32位下载</a>
  </h4>
  <h4>2.下载后解压 (如果没有压缩软件，请点<a href="https://www.7-zip.org/"> 这里 </a>下载)</h4>
  <h4>3.打开解压后的文件夹，用上面修改后的 config.json 文件替换同名文件。</h4>
  <h4>4.双击 v2ray.exe，看到黑窗口说明启动成功。<span style="color: red">***注意：黑窗口要一直开着才能翻墙。</span> </h4>
  <h4>5.然后打开火狐(FireFox)浏览器(建议更新到最新版)， 点右上角 打开菜单  --> 选项，最下面  网络代理, 点 设置...</h4>
  <div><img src="../resource/image/firefox_proxy_setting4.png" class="img-responsive center-block"></div>
  <br />
  <h4>6.然后就可以访问google, youtube之类的网站了。</h4>
   <br />
  <h3>上面的第五步可以使用谷歌浏览器，方法如下。</h3>
  <h4>谷歌浏览器(Chrome)需要下载插件 Falcon Proxy。注意安装插件需要能翻墙，如果不知道怎样安装插件请使用火狐(Firefox)浏览器。</h4>
  <p>插件安装成功后，点击右上角插件图标</p>
  <div><img src="../resource/image/Falcon-Proxy-0.png" class="img-responsive center-block"></div>
  <p>点 【添加... 】 增加如下配置并保存</p>
  <div><img src="../resource/image/Falcon-Proxy-1.png" class="img-responsive center-block"></div>
  <p>点如下开关，启动代理</p>
  <div ><img src="../resource/image/Falcon-Proxy-2.png" class="img-responsive center-block"></div>
  <br />
  
  <h3>二、安卓手机/平板 客户端   </h3>
  <h4>下载 V2RayNG 安装完成后，修改服务器配置信息如下，点右上角 对勾 保存。</h4>
  <h4>如果安卓市场找不到V2RayNG，可以点 <a href="../resource/software/v2rayNG_v0.3.12_apkpure.com.apk" download="v2rayNG.apk">这里</a> 下载。
  </h4>
   <div><img src="../resource/image/v2/v2-android-1.png" class="img-responsive center-block"></div>
  <br />
  <h4>如上图配置好之后，点击左下角画斜杠的纸飞机，变成没有斜杠的样子</h4>

  <h4>然后打开手机浏览器就可以访问google, youtube之类的网站了。</h4>
  <br />
  <h4>有些安卓手机不能使用V2RayNG，可以下载 <a target="_blank" href="https://apkpure.com/bifrostv/com.github.dawndiy.bifrostv"> BifrostV </a> 或 
  <a href="../resource/software/BifrostV_v0.6.8_apkpure.com.apk" download="BifrostV.apk"> BifrostV APK </a>
  配置如下 </h4>
  <div><img src="../resource/image/v2/bifrostv.jpg" class="img-responsive center-block"></div>
  
  <h3>三、苹果手机iPhone/平板iPad </h3>
  <h4> App Store 下载 Kitsunebi </h4>
  <h4> 参数设置 如下图 (<span style="color: red">***注意：每一个参数都要配置的一模一样</span>)</h4>
  <div><img src="../resource/image/v2/v2-apple-1.jpg" class="img-responsive center-block"></div>
  <br />
  
  <h3>四、苹果电脑Mac系统 </h3>  
  <h4>1.点 <a href="https://www.ifreesky.cn/resource/software/v2ray-macos.zip" download="v2ray-macos.zip"> 这里 </a>下载客户端</h4>
  <h4>2.下载后解压，打开解压后的文件夹，用上面修改后的 config.json 替换同名文件。</h4>
  <h4>3.然后打开v2ray，设置socks代理，即可通过浏览器访问google。</h4>
  <h4>Mac系统设置全局socks代理方法如下: </h4>
  <h4>点击右上角无线网络图标，选择 "打开网络偏好设置..."，点 "高级..."，选 "代理"，设置如下</h4>
  <div><img src="../resource/image/mac-socks-proxy.jpg" class="img-responsive center-block" /></div>
  <h4>或者</h4>
  <h4>下载<a href="https://github.com/Cenmrev/V2RayX">V2RayX</a> 使用方法请自行上网查找，参数设置参考安卓。</h4>
  <h4><a href="https://233blog.com/post/25/"> V2RayX参考配置  </a></h4>
  <h4><a href="https://www.youtube.com/watch?v=nAq-ElPAw_A"> V2RayX参考视频 </a></h4>
  <br />
  
  <h3>五、Linux系统 </h3>
  <h4>以Ubuntu系统为例</h4>
  <h4>1.打开终端，执行安装指令</h4>
  <p>$ bash <(curl -L -s https://install.direct/go.sh)</p>
  <h4>2.安装后的配置文件config.json在 /etc/v2ray 目录下</h4>
  <h4>3.先停止v2ray</h4>
  <p>$ service v2ray stop</p>
  <h4>4.把配置好的config.json文件(可以拷贝windows下的该文件)覆盖原来的文件</h4>
  <p>比如修改后的 config.json 文件在 /home/user/Downloads/v2ray/ 目录下</p>
  <p>$ cd /et/v2ray</p>
  <p>$ sudo cp /home/user/Downloads/v2ray/config.json config.json</p>
  <h4>5.启动v2ray</h4>
  <p>$ service v2ray start</p>
  <h4>6.打开火狐Firefox，设置代理如下</h4>
  <p>菜单[Edit-Preference]进入Advanced页面，选择[Network]Tab页，</p>
  <p>在 Connection Setting... 里面配置手动代理Socks V5.</p>
  <img src="../resource/image/firefox_proxy_setting4.png" class="img-responsive center-block" />
  <h4>7.在地址栏输入google.com，就可以访问了。</h4>
  <br />
  <h1>使用中有任何问题，请扫码入群讨论或加群主微信 Freeskyv </h1>
  <p><img src="../resource/image/wechat-group8.jpg" class="img-responsive center-block" /></p>
  <br />
</div>


</body>
</html>

